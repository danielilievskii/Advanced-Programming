package SecondMidtermExercises.Ex35;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

class Payment {
    String studentIndex;
    String name;
    double price;

    public Payment(String studentIndex, String name, double price) {
        this.studentIndex = studentIndex;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s %.0f", name, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(name, payment.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class PaymentFactory {
    public static Payment createPayment(String line) {
        String[] parts = line.split(";");
        String index= parts[0];
        String name = parts[1];
        double price = Double.parseDouble(parts[2]);
        return new Payment(index, name, price);
    }
}

class OnlinePayments {

    Map<String, Set<Payment>> studentPaymentsMap;

    public OnlinePayments() {
        this.studentPaymentsMap = new HashMap<>();
    }

    public void readItems(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        br.lines().forEach(line -> {
            Payment newPayment = PaymentFactory.createPayment(line);

            String index = line.split(";")[0];
            studentPaymentsMap.putIfAbsent(index, new TreeSet<>(Comparator.comparing(Payment::getPrice).thenComparing(Payment::getName).reversed()));
            studentPaymentsMap.get(index).add(newPayment);
        });
    }

    public void printStudentReport (String index, OutputStream os)  {
        if(!studentPaymentsMap.containsKey(index)) {
            System.out.println(String.format("Student %s not found!", index));
            return;
        }
        PrintWriter pw = new PrintWriter(os);
        Set<Payment> studentPayments = studentPaymentsMap.get(index);
        double net = studentPayments.stream().mapToDouble(Payment::getPrice).sum();
        double fee = net /100.0 * 1.14;
        if(fee < 3) fee = 3;
        else if (fee>300) fee = 300;

        pw.println(String.format("Student: %s Net: %.0f Fee: %.0f Total: %.0f", index, net, fee, net+fee));
        pw.println("Items:");
        int id=1;
        for (Payment payment : studentPayments) {
            pw.println(String.format("%d. %s", id, payment));
            id++;
        }
        pw.flush();
    }
}

public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}
