package LabaratoryExercises.Lab5.ResizableArray;

import java.util.Arrays;
import java.util.*;

public class ResizableArray<T> {
    private T[] elements;

    @SuppressWarnings("unchecked")
    ResizableArray() {
        this.elements = (T[])new Object[0];
    }

    public void addElement(T element) {
        T [] newElements = Arrays.copyOf(elements, elements.length + 1);
        newElements[newElements.length - 1] = element;
        elements=newElements;
    }

    public boolean removeElement(T element) {
        int idx = findElement(element);

        if(idx == -1) {
            return false;
        }

        elements[idx] = elements[count() - 1];

        T [] newElements = Arrays.copyOf(elements, elements.length - 1);

//        T [] newElements = (T []) new Object[elements.length - 1];
//        newElements = Arrays.copyOf(elements, elements.length - 1);


        elements = newElements;
        return true;
    }

    public int findElement(T element) {
        for(int i = 0; i < elements.length; i++) {
            if(element.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }



    public boolean contains(T element){
        //return Arrays.stream(elements).anyMatch(i -> i.equals(element));
        for(T e : elements) {
            if(element.equals(e)) {
                return true;
            }
        }
        return false;
    }

    public Object [] toArray() {
        return Arrays.stream(elements).toArray();
    }

    public boolean isEmpty() {
        return elements.length == 0;
    }

    public int count() {
        return elements.length;
    }

    public T elementAt(int idx) {
        if(idx < 0 || idx >= elements.length) {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
        return elements[idx];
    }

    @SuppressWarnings("unchecked")
    public static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src) {
        T[] tmpSrc =(T[]) Arrays.copyOf(src.toArray(), src.count());
        for(T element : tmpSrc) {
            dest.addElement(element);
        }
    }
}
