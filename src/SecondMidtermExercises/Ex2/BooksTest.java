package SecondMidtermExercises.Ex2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class Book {
    String title;
    String category;
    double price;


    public Book(String title, String category, double price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f", title, category, price);
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
}

class BookCollection {
    List<Book> books;

    public BookCollection() {
        this.books=new ArrayList<>();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void printByCategory(String category) {
        Comparator<Book> compareNameThenPrice = Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice);
        this.books.stream().filter(book -> book.getCategory().equals(category)).sorted(compareNameThenPrice).forEach(System.out::println);
    }

    public List<Book> getCheapestN(int n) {
        if(books.size()<n) {
            return books;
        } else {
            Comparator<Book> priceComparator = Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle);
            return this.books.stream().sorted(priceComparator).limit(n).collect(Collectors.toList());
        }
    }
}

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

// Вашиот код овде
