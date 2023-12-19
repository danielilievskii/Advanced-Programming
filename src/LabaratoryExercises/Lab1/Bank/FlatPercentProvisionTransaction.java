package LabaratoryExercises.Lab1.Bank;

import java.util.Objects;

public class FlatPercentProvisionTransaction extends Transaction {
    private final int flatPercent;

    public FlatPercentProvisionTransaction(long fromId, long toId,String amount, int flatPercent) {
        super(fromId, toId, "FlatPercent", amount);
        this.flatPercent = flatPercent;
    }

//    public int getPercent() {
//        return flatPercent;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatPercentProvisionTransaction that = (FlatPercentProvisionTransaction) o;
        return flatPercent == that.flatPercent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(flatPercent);
    }

    @Override
    public double getProvision() {
        return (int) ToDouble.toDouble(getAmount()) * (flatPercent/100.0);
    }

    public int getPercentProvision() {
        return flatPercent;
    }
}
