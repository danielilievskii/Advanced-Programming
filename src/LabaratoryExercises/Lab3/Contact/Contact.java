package LabaratoryExercises.Lab3.Contact;

import LabaratoryExercises.Lab2.Contact.Operator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Contact implements Comparable<Contact> {
    private String name;
    private String[] phoneNumbers;

    public Contact(String name, String... phoneNumbers) throws InvalidNameException, InvalidNumberException, MaximumSizeExceddedException{
        if(name.length()<4 || name.length()>10 || !isAlphaNumeric(name))
            throw new InvalidNameException(name);

        if(phoneNumbers.length > 5) {
            throw new MaximumSizeExceddedException();
        }

//        if(!Arrays.stream(phoneNumbers).allMatch(Contact::numberCheck)) {
//            throw new InvalidNumberException();
//        }
        for(String pn: phoneNumbers) {
            if(!numberCheck(pn)) {
                throw new InvalidNumberException();
            }
        }

        this.name = name;
        this.phoneNumbers = new String[phoneNumbers.length];
        IntStream.range(0, phoneNumbers.length).forEach(i -> this.phoneNumbers[i]=phoneNumbers[i]);
//        for(int i=0; i<phoneNumbers.length; i++) {
//            this.phoneNumbers[i]=phoneNumbers[i];
//        }
    }

    public void addNumber(String phonenumber) throws InvalidNumberException {
        if(!numberCheck(phonenumber))
            throw new InvalidNumberException();
        else {
            phoneNumbers = Arrays.copyOf(phoneNumbers, phoneNumbers.length+1);
            phoneNumbers[phoneNumbers.length-1] = phonenumber;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name)
                .append("\n")
                .append(phoneNumbers.length)
                .append("\n");
        Arrays.stream(phoneNumbers).sorted().forEach(i -> sb.append(i).append("\n"));
        return sb.toString();
    }

    public String getName() {
        return name;
    }
    public String[] getNumbers() {
        return Arrays.stream(phoneNumbers).sorted().toArray(String[]::new);
    }

    public boolean hasNumber(String s) {
        return Arrays.stream(phoneNumbers).anyMatch(i -> i.startsWith(s));
    }

    public static Contact valueOf(String s) throws InvalidFormatException{
        try {
            return new Contact(s);
        } catch (Exception e) {
            throw new InvalidFormatException();
        }
    }

    public static boolean numberCheck(String number) {
        return number.matches("07[0125678][0-9]{6}");
//        String operatorParser = number.substring(0, 3);
//        return (operatorParser.equals("070")
//                || operatorParser.equals("071")
//                || operatorParser.equals("072")
//                || operatorParser.equals("075")
//                || operatorParser.equals("076")
//                || operatorParser.equals("077")
//                || operatorParser.equals("078"))
//                && number.length()==9;

//        return (number.startsWith("070")
//                || number.startsWith("071")
//                || number.startsWith("072")
//                || number.startsWith("075")
//                || number.startsWith("076")
//                || number.startsWith("077")
//                || number.startsWith("078"))
//                && number.length()==9;
    }

    public static boolean isAlphaNumeric(String input) {
        for(char c: input.toCharArray()) {
            if(!Character.isAlphabetic(c))
                return false;
        }
        return true;
    }

    @Override
    public int compareTo(Contact other) {
        return this.name.compareTo(other.name);
    }
}
