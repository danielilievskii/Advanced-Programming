package LabaratoryExercises.Lab1.Bank;

import java.util.Random;

public class Account {
    private String name;
    private long ID;
    private String balance;

    public Account(String name, String balance) {
        this.name = name;
        this.balance = balance;
        Random r = new Random();
        this.ID = r.nextLong();
    }

    public String getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }
    public long getId() {
        return ID;
    }
    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nBalance: " + balance + "\n";
    }
}
