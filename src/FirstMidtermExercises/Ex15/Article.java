package FirstMidtermExercises.Ex15;

public class Article {
    private int price;
    private DDV_TYPE type;

    public Article(int price, DDV_TYPE type) {
        this.price = price;
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public DDV_TYPE getType() {
        return type;
    }

    public double getTax() {
        switch (type) {
            case A:
                return price * 0.18 * 0.15;
            case B:
                return price * 0.05 * 0.15;
            default:
                return 0;
        }
    }
}
