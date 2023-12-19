package LabaratoryExercises.Lab3.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhoneBook  {
    private List<Contact> contacts;

    public PhoneBook() {
        this.contacts = new ArrayList<>();
    }

    public boolean hasContact(Contact c) {
        return this.contacts.stream().anyMatch(contact -> contact.getName().equals(c.getName()));
    }
    public void addContact(Contact contact) throws MaximumSizeExceddedException, InvalidNameException {
        if(this.contacts.size() > 250) {
            throw new MaximumSizeExceddedException();
        }
        if(hasContact(contact)) {
            throw new InvalidNameException(contact.getName());
        }

        contacts.add(contact);
    }
    public Contact getContactForName(String name) {
        return this.contacts.stream().filter(contact -> contact.getName().equals(name)).findFirst().orElse(null);
    }

    public int numberOfContacts() {
        return this.contacts.size();
    }



    public Contact[] getContacts() {
        return contacts.stream().sorted().toArray(Contact[]::new);
    }

    public boolean removeContact(String name) {
        for(Contact c: contacts) {
            if(c.getName().equals(name)) {
                contacts.remove(c);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.contacts.stream().sorted().forEach(contact -> sb.append(contact).append("\n"));
        return sb.toString();
    }

    public Contact[] getContactsForNumber(String number_prefix) {
        return this.contacts.stream().filter(c -> c.hasNumber(number_prefix)).toArray(Contact[]::new);
    }

    public static boolean saveAsTextFile(PhoneBook pb, String path) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(pb);
            oos.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static PhoneBook loadFromTextFile(String file) throws InvalidFormatException {
        PhoneBook pb = null;

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            pb = (PhoneBook) ois.readObject();
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new InvalidFormatException();
        }

        return pb;
    }
}
