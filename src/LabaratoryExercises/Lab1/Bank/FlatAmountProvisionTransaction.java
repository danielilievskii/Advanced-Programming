package LabaratoryExercises.Lab1.Bank;

import java.util.Objects;

public class FlatAmountProvisionTransaction extends Transaction implements ToDouble{
    private final String flatAmount;

    public FlatAmountProvisionTransaction(long fromId, long toId,String amount, String flatAmount) {
        super(fromId, toId, "FlatAmount", amount);
        this.flatAmount = flatAmount;
    }

    public String getFlatAmount() {
        return flatAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatAmountProvisionTransaction that = (FlatAmountProvisionTransaction) o;
        return Objects.equals(flatAmount, that.flatAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flatAmount);
    }



    @Override
    public double getProvision() {
        return ToDouble.toDouble(flatAmount);
    }
}
