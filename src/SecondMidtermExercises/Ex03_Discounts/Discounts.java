package SecondMidtermExercises.Ex03_Discounts;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Product {
    int price;
    int discountedPrice;

    public Product(int discountedPrice, int price) {
        this.discountedPrice = discountedPrice;
        this.price = price;
    }

    public int discountPercentage() {
        //return (int) (discountedPrice / (float) price * 100);
        return (price - discountedPrice) * 100 / price;
    }

    public int absoluteDiscount() {
        return price - discountedPrice;
    }

    @Override
    public String toString() {
        return String.format("%2d%% %d/%d", discountPercentage(), discountedPrice, price);
    }
}

class Store {
    String name;
    List<Product> products;

    public Store(String line) {
        String[] parts = line.split("\\s+");
        this.name = parts[0];

        this.products=new ArrayList<>();

        Arrays.stream(parts).skip(1).forEach(part -> {
            String[] priceParts = part.split(":");
            int discountedPrice = Integer.parseInt(priceParts[0]);
            int originalPrice = Integer.parseInt(priceParts[1]);
            products.add(new Product(discountedPrice, originalPrice));
        });
    }



    public double averageDiscount() {
        return products.stream().mapToDouble(Product::discountPercentage).average().orElse(0);
    }

    public double totalDiscount() {
        return products.stream().mapToDouble(Product::absoluteDiscount).sum();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String productsStr = products.stream()
                .sorted(Comparator.comparing(Product::discountPercentage).thenComparing(Product::absoluteDiscount).reversed())
                .map(Product::toString)
                .collect(Collectors.joining("\n"));


        return String.format("%s\nAverage discount: %.1f%%\nTotal discount: %.0f\n%s", name, averageDiscount(), totalDiscount(), productsStr);
    }
}



public class Discounts {
    List<Store> stores;

    public Discounts() {
        this.stores=new ArrayList<>();
    }
    public int readStores(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        stores= bufferedReader.lines().map(Store::new).collect(Collectors.toList());
        return stores.size();

    }

    public List<Store>  byAverageDiscount() {
        Comparator<Store> comparatorAverageDiscountThenName = Comparator.comparing(Store::averageDiscount).thenComparing(Store::getName);
        return this.stores.stream().sorted(comparatorAverageDiscountThenName.reversed()).limit(3).collect(Collectors.toList());
    }

    public List<Store>  byTotalDiscount() {
        Comparator<Store> comparatorAbsoluteDiscountThenName = Comparator.comparing(Store::totalDiscount).thenComparing(Store::getName);
        return this.stores.stream().sorted(comparatorAbsoluteDiscountThenName).limit(3).collect(Collectors.toList());
    }
}
