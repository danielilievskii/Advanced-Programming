package LabaratoryExercises.Lab1.Bank;

public abstract class Transaction {
    protected final long fromID;
    protected final long toID;
    protected final String description;
    protected final String amount;

    public Transaction (long fromID, long toID, String description, String amount) {
        this.fromID = fromID;
        this.toID = toID;
        this.description = description;
        this.amount = amount;
    }

    public long getFromID() {
        return fromID;
    }

    public long getToID() {
        return toID;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public abstract double getProvision();
}
