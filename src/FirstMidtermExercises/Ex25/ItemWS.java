package FirstMidtermExercises.Ex25;

public class ItemWS extends Product {
    double quantity;

    public ItemWS(int productID, String productName, double productPrice, double quantity) {
        super(productID, productName, productPrice);
        this.quantity=quantity;
    }

    @Override
    public double getItemPrice() {
        return quantity*productPrice;
    }
}
