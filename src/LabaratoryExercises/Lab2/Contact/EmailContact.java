package LabaratoryExercises.Lab2.Contact;

public class EmailContact extends Contact {
    String email;
    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }
    public String getType() {
        return "Email";
    }

    public String toString() {
        return "\"" + email + "\"";
    }

    public String getEmail() {
        return email;
    }
}
