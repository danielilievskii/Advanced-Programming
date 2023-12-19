package LabaratoryExercises.Lab6.IntegerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class IntegerList {
    private List <Integer> list;

    public IntegerList() {
        this.list=new ArrayList<>();
    }

    public IntegerList(Integer... numbers) {
        this.list=new ArrayList<>();
        IntStream.range(0, numbers.length).forEach(i -> list.add(numbers[i]));
    }

    public void add (int el, int idx) {

        if (idx >= list.size()) {
            IntStream.range(list.size(), idx).forEach(i -> list.add(i, 0));
        }
        list.add(idx, el);
    }
    public int remove(int idx) {
        if(idx<0 || idx>=list.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return list.remove(idx);
    }

    public void set(int el, int idx) {
        if(idx<0 || idx>=list.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        list.set(idx, el);
    }

    public int get(int idx) {
        if(idx<0 || idx>=list.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return list.get(idx);
    }
    public int size() {

        return list.size();
    }

    public int count(int el) {
        return (int) list.stream().filter(i -> i == el).count();
    }

    public void removeDuplicates() {
        Collections.reverse(list);
        list = list.stream().distinct().collect(Collectors.toList());
        Collections.reverse(list);


//        for(int i= list.size() -1; i>=1; i--) {
//            for(int j=i-1; j>=0; j--) {
//                if(list.get(i).equals(list.get(j))) {
//                    list.remove(j);
//                    j++;
//                }
//            }
//        }

    }

    public int sumFirst(int k) {
        return list.stream().limit(k).mapToInt(i -> i).sum();
        //for od 0 do k
    }

    public int sumLast(int k) {
        return list.stream().skip((long) list.size()-k).mapToInt(i -> i).sum();
        //for od list.size-1 do list.size-1-k
    }

    public void shiftRight(int idx, int k) {
        if(idx<0 || idx>=list.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

//        int targetIdx = idx;
//        for(int i=1; i<=k; i++) {
//            targetIdx++;
//            if(targetIdx==list.size()) {
//                targetIdx=0;
//            }
//        }
//        list.add(targetIdx, list.remove(idx));

        int targetIdx = (idx + k) % list.size();
        list.add(targetIdx, list.remove(idx));

    }

    public void shiftLeft(int idx, int k) {
        if(idx<0 || idx>=list.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

//        int targetIdx = idx;
//        for(int i=1; i<=k; i++) {
//            targetIdx--;
//            if(targetIdx==-1) {
//                targetIdx=list.size()-1;
//            }
//        }
//        list.add(targetIdx, list.remove(idx));

        int targetIdx = (idx - k) % list.size();
        if(targetIdx < 0) targetIdx = list.size() + targetIdx;
        list.add(targetIdx, list.remove(idx));

    }

    public IntegerList addValue(int value) {
        IntegerList tmp = new IntegerList();
        tmp.list = list.stream().map(i -> i+value).collect(Collectors.toList());
        return tmp;
    }

}

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test standard methods
            int subtest = jin.nextInt();
            if ( subtest == 0 ) {
                IntegerList list = new IntegerList();
                while ( true ) {
                    int num = jin.nextInt();
                    if ( num == 0 ) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if ( num == 1 ) {
                        list.remove(jin.nextInt());
                    }
                    if ( num == 2 ) {
                        print(list);
                    }
                    if ( num == 3 ) {
                        break;
                    }
                }
            }
            if ( subtest == 1 ) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for ( int i = 0 ; i < n ; ++i ) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if ( k == 1 ) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if ( num == 1 ) {
                    list.removeDuplicates();
                }
                if ( num == 2 ) {
                    print(list.addValue(jin.nextInt()));
                }
                if ( num == 3 ) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if ( num == 1 ) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if ( num == 2 ) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if ( num == 3 ) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if ( il.size() == 0 ) System.out.print("EMPTY");
        for ( int i = 0 ; i < il.size() ; ++i ) {
            if ( i > 0 ) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}
