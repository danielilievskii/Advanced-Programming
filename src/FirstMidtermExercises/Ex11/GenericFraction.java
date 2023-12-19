package FirstMidtermExercises.Ex11;

public class GenericFraction<T extends Number, U extends Number> {
    private T numerator;
    private U denominator;

    public GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {

        if(denominator.doubleValue() == 0) {
            throw new ZeroDenominatorException("Denominator cannot be zero");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    private double gcd(double a, double b) {
        if(b==0) {
            return a;
        }
        return gcd(b, a%b);
    }

    public GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
        double commonDenominator = this.denominator.doubleValue() * gf.denominator.doubleValue();

        double addedNumerator1 = this.numerator.doubleValue() * (commonDenominator / this.denominator.doubleValue());
        double addedNumerator2 = gf.numerator.doubleValue() * (commonDenominator / gf.denominator.doubleValue());
        double resultNumerator = addedNumerator1 + addedNumerator2;

        return simplifyFraction(resultNumerator, commonDenominator);
    }

    private GenericFraction<Double, Double> simplifyFraction(double numerator, double denominator) throws ZeroDenominatorException {
        double gcdValue = gcd(numerator, denominator);
        double simplifiedNumerator = numerator / gcdValue;
        double simplifiedDenominator = denominator / gcdValue;

        return new GenericFraction<Double, Double>(simplifiedNumerator, simplifiedDenominator);
    }

    double toDouble() {
        return this.numerator.doubleValue()/this.denominator.doubleValue();
    }

    @Override
    public String toString() {
        return String.format("%.2f / %.2f", numerator.doubleValue(), denominator.doubleValue());
    }
}
