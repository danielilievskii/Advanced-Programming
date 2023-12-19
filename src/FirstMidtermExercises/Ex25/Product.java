package FirstMidtermExercises.Ex25;

public abstract class Product implements Comparable<Product> {
    int productID;
    String productName;
    double productPrice;

    public Product(int productID, String productName, double productPrice) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public abstract double getItemPrice();

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public int compareTo(Product other) {
        return Double.compare(getItemPrice(), other.getItemPrice());
    }

    @Override
    public String toString() {
       return String.format("%d - %.2f", this.productID, getItemPrice());
    }
}
