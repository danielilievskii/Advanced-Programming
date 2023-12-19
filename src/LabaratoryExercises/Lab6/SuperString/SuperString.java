package LabaratoryExercises.Lab6.SuperString;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;
import java.util.stream.Collectors;

public class SuperString {
    private LinkedList<String> stringArr;
    private Stack<String> stringStack;
    public SuperString() {
        this.stringArr = new LinkedList<String>();
        this.stringStack = new Stack<>();
    }

    public void append(String s) {
        this.stringArr.addLast(s);
        stringStack.push(s);
    }

    public void insert(String s) {
        this.stringArr.addFirst(s);
        stringStack.push(s);

    }

    public boolean contains(String s) {
        //return stringArr.stream().anyMatch(str -> str.contains(s));
        return toString().contains(s);
    }

    public void reverse() {
        Collections.reverse(stringArr);

        stringArr = stringArr.stream().map(str -> new StringBuilder(str).reverse().toString()).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public String toString() {
        return String.join("", stringArr);

        //so StringBuilder vtoro resenie
    }

    public void removeLast(int k) {
        if(stringArr.isEmpty()) return;
       for(int i=0; i<k; i++) {
           StringBuilder sb = new StringBuilder(stringStack.pop());

           stringArr.remove(sb.toString());
           stringArr.remove(sb.reverse().toString());
       }
    }
}
