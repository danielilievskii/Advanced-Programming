package LabaratoryExercises.Lab2.Contact;


import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Student {

    public ArrayList<Contact> contacts;
    public String firstName;
    public String lastName;
    public String city;
    public int age;
    public long index;

    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;

        this.contacts = new ArrayList<Contact>();


    }

    void addEmailContact(String date, String email) {
        contacts.add(new EmailContact(date, email));
    }

    void addPhoneContact(String date, String phone) {
        contacts.add(new PhoneContact(date, phone));
    }

    Contact[] getEmailContacts() {
        ArrayList<Contact> emailContacts = new ArrayList<>();
        for(Contact c : this.contacts) {
            if(c.getType().equals("Email")) {
                emailContacts.add(c);
            }
        }
        return emailContacts.toArray(new Contact[emailContacts.size()]);
    }

    Contact[] getPhoneContacts() {
        ArrayList<Contact> phoneContacts = new ArrayList<>();
        for(Contact c : this.contacts) {
            if(c.getType().equals("Phone")) {
                phoneContacts.add(c);
            }
        }
        return phoneContacts.toArray(new Contact[phoneContacts.size()]);
    }

    public String getCity() {
        return this.city;
    }

    public long getIndex() {
        return index;
    }
    public String getFullName() {
        return this.firstName + " " + this.lastName;

    }

    public int getNumContacts () {
        return contacts.size();
    }

    public Contact getLatestContact() {
        Contact newest = contacts.get(0);

        for(Contact c : contacts) {
            if(c.isNewerThan(newest))
                newest = c;
        }
        return newest;
    }

    public int getContactLength() {
        return contacts.size();
    }

    @Override
    public String toString() {
        String str =  "{" +
                "\"ime\":\"" + firstName + "\", " +
                "\"prezime\":\"" + lastName + "\", " +
                "\"vozrast\":" + age + ", " +
                "\"grad\":\"" + city + "\", " +
                "\"indeks\":" + index + ", \"telefonskiKontakti\":" +
                Arrays.toString(getPhoneContacts()) +
                ", \"emailKontakti\":" +
                Arrays.toString(getEmailContacts()) +
                "}";
        return str;
    }
}
