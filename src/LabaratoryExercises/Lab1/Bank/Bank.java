package LabaratoryExercises.Lab1.Bank;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class Bank implements ToDouble {
    public Account accounts[];
    String name;
    private double totalTransfer;
    private double totalProvision;
    public Bank(String name, Account accounts[]) {
        this.name=name;
        this.totalTransfer=0;
        this.totalProvision=0;
        this.accounts = new Account[accounts.length];
        System.arraycopy(accounts, 0, this.accounts, 0, accounts.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Double.compare(totalTransfer, bank.totalTransfer) == 0 && Double.compare(totalProvision, bank.totalProvision) == 0 && Arrays.equals(accounts, bank.accounts) && Objects.equals(name, bank.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, totalTransfer, totalProvision);
        result = 31 * result + Arrays.hashCode(accounts);
        return result;
    }

    public String totalTransfer() {
        return String.format("%.2f$", totalTransfer);
    }

    public String totalProvision() {
        return String.format("%.2f$", totalProvision);
    }

    private int findIndex(long ID) {
        for(int i=0; i<accounts.length; i++) {
            if(accounts[i].getId() == ID) {
                return i;
            }
        }
        return -1;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Name: ").append(name).append("\n\n");
        for(Account acc : accounts) {
            str.append(acc.toString());
        }
        return str.toString();
    }

    public boolean makeTransaction(Transaction t) {
        int fromIndex = findIndex(t.fromID);
        int toIndex = findIndex(t.toID);

        if(fromIndex == -1 || toIndex == -1) {
            return false;
        }

        double balanceFrom = ToDouble.toDouble(accounts[fromIndex].getBalance());
        double balanceTo = ToDouble.toDouble(accounts[toIndex].getBalance());
        double amountTransaction = ToDouble.toDouble(t.getAmount());
        double amountProvision = t.getProvision();

        if(balanceFrom < amountTransaction) {
            return false;
        }

        if(fromIndex == toIndex) {
            accounts[fromIndex].setBalance(String.format("%.2f$", balanceFrom - amountProvision));
        } else {
            accounts[fromIndex].setBalance(String.format("%.2f$", balanceFrom - amountProvision - amountTransaction));
            accounts[toIndex].setBalance(String.format("%.2f$", balanceTo + amountTransaction));

        }

        totalTransfer+=amountTransaction;
        totalProvision+=amountProvision;
        return true;
    }
}
