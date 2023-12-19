package LabaratoryExercises.Lab2.Contact;


public class PhoneContact extends Contact{
    String number;

    public PhoneContact(String date, String phone) {
        super(date);
        this.number=phone;
    }

    public String getPhone() {
        return number;
    }

    public String toString() {
        return "\"" + number + "\"";
    }

    public Operator getOperator() {
        String operatorParser = number.substring(0, 3);
        if(operatorParser.equals("070") || operatorParser.equals("071") || operatorParser.equals("072")) {
            return Operator.TMOBILE;
        }
        else if (operatorParser.equals("075") || operatorParser.equals("076")) {
            return Operator.ONE;
        }
        else return Operator.VIP;
    }

    public String getType() {
        return "Phone";
    }
}
