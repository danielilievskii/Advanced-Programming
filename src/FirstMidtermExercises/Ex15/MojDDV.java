package FirstMidtermExercises.Ex15;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MojDDV {
    List<Receipt> receipts;
    public MojDDV() {
        this.receipts = new ArrayList<>();
    }

    public void readRecords (InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        br.lines().forEach(line -> {
            try {
                Receipt fiskalna = new Receipt(line);
                receipts.add(fiskalna);
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        });


//        receipts = br.lines().map(line -> {
//            try {
//                return new Receipt(line);
//            } catch (AmountNotAllowedException e) {
//                System.out.println(e.getMessage());
//                return null;
//            }
//        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
    public void printTaxReturns(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

        for(Receipt r: receipts) {
            System.out.println(r);
        }

        pw.flush();
    }

//    void printStatistics(OutputStream outputStream) {
//        PrintWriter pw = new PrintWriter(outputStream);
//        DoubleSummaryStatistics summaryStatistics = records.stream().mapToDouble(Record::getTaxReturn).summaryStatistics();
//
//        pw.println(String.format("min:\t%05.03f\nmax:\t%05.03f\nsum:\t%05.03f\ncount:\t%-5d\navg:\t%05.03f",
//                summaryStatistics.getMin(),
//                summaryStatistics.getMax(),
//                summaryStatistics.getSum(),
//                summaryStatistics.getCount(),
//                summaryStatistics.getAverage()));
//
//        pw.flush();
//    }
}
