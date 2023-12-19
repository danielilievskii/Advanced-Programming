package LabaratoryExercises.Lab3.Pizza;

import java.util.Objects;

public class ExtraItem implements Item{

    private final String type;
    ExtraItem(String type) throws InvalidExtraTypeException {
        if(!type.equals("Coke") && !type.equals("Ketchup")) {
            throw new InvalidExtraTypeException("");
        } else {
            this.type=type;
        }
    }
    public String getType() {
        return type;
    }
    public int getPrice() {
        switch (type) {
            case "Coke": return 5;
            case "Ketchup": return 3;
            default: return 0;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraItem extraItem = (ExtraItem) o;
        return Objects.equals(type, extraItem.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
