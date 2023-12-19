package FirstMidtermExercises.Ex25;

public class ItemPS extends Product{
    double quantity;

    public ItemPS(int productID, String productName, double productPrice, double quantity) {
        super(productID, productName, productPrice);
        this.quantity=quantity;
    }

    @Override
    public double getItemPrice() {
        return productPrice/1000 * quantity;
    }
}
