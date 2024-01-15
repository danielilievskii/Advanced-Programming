package SecondMidtermExercises.Ex25_OnlineShop;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum COMPARATOR_TYPE {
    NEWEST_FIRST,
    OLDEST_FIRST,
    LOWEST_PRICE_FIRST,
    HIGHEST_PRICE_FIRST,
    MOST_SOLD_FIRST,
    LEAST_SOLD_FIRST
}

class ProductNotFoundException extends Exception {
    ProductNotFoundException(String id) {
        super(String.format("Product with id %s does not exist in the online shop!", id));
    }
}


class Product {
    private String category;
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private double price;

    private int unitsSold;

    public Product(String category, String id, String name, LocalDateTime createdAt, double price) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.price = price;
        this.unitsSold=0;
    }

    public double getPrice() {
        return price;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(int unitsSold) {
        this.unitsSold = unitsSold;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", price=" + price +
                ", quantitySold=" + unitsSold +
                '}';
    }
}


class OnlineShop {

    Map<String, Product> productsById;

    OnlineShop() {
        this.productsById = new HashMap<>();
    }

    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price){
        Product p = new Product(category, id, name, createdAt, price);
        productsById.put(id, p);
    }

    double buyProduct(String id, int quantity) throws ProductNotFoundException{
        if(!productsById.containsKey(id)) {
            throw new ProductNotFoundException(id);
        }

        Product p = productsById.get(id);
        p.setUnitsSold(p.getUnitsSold() + quantity);
        return p.getPrice()*quantity;
    }

    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
        List<List<Product>> result = new ArrayList<>();
       

        List<Product> productList = productsById.values().stream().collect(Collectors.toList());
        if(category!=null) {
            productList = productList.stream().filter(product -> product.getCategory().equals(category)).collect(Collectors.toList());
        }

        Comparator<Product> comparator = ProductComparatorFactory.createComparator(comparatorType);
        productList.sort(comparator);

        if(pageSize > productList.size()) {
            result.add(productList);
        } else {
            int ratio = (int) Math.ceil(productList.size()*1.0 / pageSize);
            List<Integer> starts = IntStream.range(0, ratio).map(i -> i * pageSize).boxed().collect(Collectors.toList());
            List<Product> finalProductList = productList;
            starts.forEach(i -> result.add(finalProductList.subList(i, Math.min((i+pageSize), finalProductList.size()))));
        }
        return result;
    }

}

class ProductComparatorFactory {
    static Comparator<Product> createComparator(COMPARATOR_TYPE comparatorType) {
        switch (comparatorType) {
            case NEWEST_FIRST:
                return Comparator.comparing(Product::getCreatedAt).reversed();
            case OLDEST_FIRST:
                return Comparator.comparing(Product::getCreatedAt);
            case MOST_SOLD_FIRST:
                return Comparator.comparing(Product::getUnitsSold).reversed();
            case LEAST_SOLD_FIRST:
                return Comparator.comparing(Product::getUnitsSold);
            case HIGHEST_PRICE_FIRST:
                return Comparator.comparing(Product::getPrice).reversed();
            case LOWEST_PRICE_FIRST:
                return Comparator.comparing(Product::getPrice);
            default:
                return Comparator.comparing(Product::getPrice);
        }
    }
}

public class OnlineShopTest {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        double totalAmount = 0.0;
        Scanner sc = new Scanner(System.in);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("addproduct")) {
                String category = parts[1];
                String id = parts[2];
                String name = parts[3];
                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
                double price = Double.parseDouble(parts[5]);
                onlineShop.addProduct(category, id, name, createdAt, price);
            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                try {
                    totalAmount += onlineShop.buyProduct(id, quantity);
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                String category = parts[1];
                if (category.equalsIgnoreCase("null"))
                    category=null;
                String comparatorString = parts[2];
                int pageSize = Integer.parseInt(parts[3]);
                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
            }
        }
        System.out.println("Total revenue of the online shop is: " + totalAmount);

    }

    private static void printPages(List<List<Product>> listProducts) {
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println("PAGE " + (i + 1));
            listProducts.get(i).forEach(System.out::println);
        }
    }
}


