package FirstMidtermExercises.Ex25;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {
    List<Product> items;

    public ShoppingCart() {
       this.items=new ArrayList<>();
    }

    public void addItem(String itemData) throws InvalidOperationException {
        String[] parts = itemData.split(";");

        String type = parts[0];
        int productID = Integer.parseInt(parts[1]);
        String name = parts[2];
        double productPrice = Double.parseDouble(parts[3]);
        double quanity = Double.parseDouble(parts[4]);
        if(quanity == 0) {
            throw new InvalidOperationException(String.format("The quantity of the product with id %d can not be 0.", productID));
        }

        if(type.equals("WS")) {
            items.add(new ItemWS(productID, name, productPrice, quanity));
        } else {
            items.add(new ItemPS(productID, name, productPrice, quanity));
        }
    }

    public void printShoppingCart(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        items.stream().sorted(Collections.reverseOrder()).forEach(pw::println);

        pw.flush();
    }

    public void blackFridayOffer(List<Integer> discountItems, OutputStream os) throws InvalidOperationException{
        if(discountItems.isEmpty()) {
            throw new InvalidOperationException("There are no products with discount.");
        }

        PrintWriter pw = new PrintWriter(os);

        List<Product> discountedItems = new ArrayList<>();
        discountedItems = items.stream().filter(item -> discountItems.contains(item.getProductID())).collect(Collectors.toList());

        discountedItems.forEach(item ->  {
           double oldItemPrice = item.getItemPrice();
           item.setProductPrice(item.getProductPrice()*0.9);
           double newItemPrice = item.getItemPrice();
           pw.println(String.format("%s - %.2f", item.productID, oldItemPrice-newItemPrice));
        });

        pw.flush();
    }
}
