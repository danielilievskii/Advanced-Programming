package LabaratoryExercises.Lab2.Contact;

public abstract class Contact {
    public String date;
    public Contact(String date) {
        this.date = date;
    }

    public boolean isNewerThan(Contact c) {
        return getDays() > c.getDays();
    }

    public long getDays() {
        String[] dateParts = date.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        return year*365L + month*30L + day;

        //return Long.parseLong(date);
    }

    public abstract String getType();

}


