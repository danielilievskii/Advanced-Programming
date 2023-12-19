package LabaratoryExercises.Lab2.Contact;
import java.util.Arrays;

public class Faculty {
    public String name;
    Student[] students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = students;
    }

    Student getStudent(long index) {
        for(int i=0; i<students.length; i++) {
            if(students[i].getIndex()==index) {
                return students[i];
            }
        }
        return null;
    }

    double getAverageNumberOfContacts() {
        double sum=0, counter=0;
        for(Student s:students) {
            sum+=s.contacts.size();
        }
        return sum / students.length;
    }

    int countStudentsFromCity(String city) {
        int counter=0;
        for(Student s:students) {
            if(s.city.equals(city)) {
                counter++;
            }
        }
        return counter;
    }

    public Student getStudentWithMostContacts() {
        Student maxStudent = students[0];
        for(Student s : students) {
            if(s.getNumContacts()>maxStudent.getNumContacts()) maxStudent = s;
            else if(s.getNumContacts()==maxStudent.getNumContacts()){
                if(s.getIndex()>maxStudent.getIndex()) maxStudent = s;
            }
        }
        return maxStudent;
    }

    @Override
    public String toString() {
        return "{\"fakultet\":\"" +
                name +
                "\", \"studenti\":" +
                Arrays.toString(students) +
                "}";
    }
}
