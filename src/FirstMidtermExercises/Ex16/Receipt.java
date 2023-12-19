package FirstMidtermExercises.Ex16;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    String ID;
    List<Article> articles;

    public Receipt(String line) throws AmountNotAllowedException {
        articles = new ArrayList<>();
        String[] parts = line.split("\\s+");
        this.ID = parts[0];

        for(int i=1; i<= parts.length-1; i+=2) {
            int amount = Integer.parseInt(parts[i]);
            String type = parts[i+1];

            articles.add(new Article(amount, DDV_TYPE.valueOf(type)));
        }
        int totalAmount = articles.stream().mapToInt(article -> article.getPrice()).sum();
        if(totalAmount > 30000) {
            throw new AmountNotAllowedException(totalAmount);
        }
    }

    public String getID() {
        return ID;
    }

    public int totalSum() {
        return this.articles.stream().mapToInt(a -> a.getPrice()).sum();
    }

    public double totalTax() {
        return this.articles.stream().mapToDouble(a -> a.getTax()).sum();
    }

    @Override
    public String toString() {
        return String.format("%10s\t%10d\t%10.5f", getID(), totalSum(), totalTax());
    }
}
