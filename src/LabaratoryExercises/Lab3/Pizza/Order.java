package LabaratoryExercises.Lab3.Pizza;
import java.util.ArrayList;
import java.util.List;

public class Order {
    List<Product> orderList;
    public boolean locked;

    Order() {
        this.orderList = new ArrayList<>();
        this.locked=false;
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        if(locked) {
            throw new OrderLockedException();
        }

        if(count>10) {
            throw new ItemOutOfStockException(item);
        }

        for(Product p : orderList) {
            if(p.getItem().equals(item)) {
                p.setCount(count);
                return;
            }
        }

        this.orderList.add(new Product(item, count));
    }

    public int getPrice() {
        int sum=0;
        for(Product p : orderList) {
            sum = sum + p.getPrice();
        }
        return sum;

        //return orderList.stream().mapToInt(i -> i.getItem().getPrice() * i.getCount()).sum();
    }

    public void removeItem(int idx) throws OrderLockedException  {
        if(locked) {
            throw new OrderLockedException();
        }

        if(idx>=this.orderList.size() || idx<0) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            this.orderList.remove(idx);
        }

    }

    public void lock() throws EmptyOrder{
        if(!this.orderList.isEmpty()) {
            this.locked=true;
        } else throw new EmptyOrder();
    }

    public void displayOrder() {
        StringBuilder sb = new StringBuilder();

        orderList.stream().forEach(i -> sb.append(String.format("%3d.%-15sx%2d%5d$%n", orderList.indexOf(i) + 1, i.getItem().getType(), i.getCount(), i.getPrice())));
        sb.append(String.format("%-22s%5d$", "Total:", getPrice()));

        System.out.println(sb.toString());
    }
}

