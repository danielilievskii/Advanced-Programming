package FirstMidtermExercises.Ex5;

public class MinMax<T extends Comparable<T>> {
    private T min;
    private T max;
    private int updatesTotal;
    private int updatesMin;
    private int updatesMax;

    public MinMax() {
        this.updatesMax=0;
        this.updatesMin=0;
        this.updatesTotal=0;
    }

    public void update(T element) {
        if(updatesTotal==0) {
            this.max = element;
            this.min = element;
        }

        if (element.compareTo(min) < 0) {
           this.min=element;
            this.updatesMin=1;
        } else if(element.compareTo(min)==0) {
            this.updatesMin++;
        }

        if (element.compareTo(max) > 0) {
            this.max=element;
            this.updatesMax=1;
        } else if(element.compareTo(max)==0) {
            this.updatesMax++;
        }

        updatesTotal++;
    }

    @Override
    public String toString() {
        return min + " " + max + " " + (updatesTotal - updatesMin - updatesMax) + "\n";
    }

    public T min() {
        return min;
    }
    public T max() {
        return max;
    }
}
