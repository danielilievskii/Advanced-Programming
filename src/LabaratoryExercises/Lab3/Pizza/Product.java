package LabaratoryExercises.Lab3.Pizza;

public class Product {
    private Item item;
    private int count;
    Product(Item item, int count) {
        this.item=item;
        this.count=count;
    }

    public int getPrice() {
        return item.getPrice()*count;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
