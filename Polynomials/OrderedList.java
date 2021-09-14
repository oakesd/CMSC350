package project2;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * OrderedList.java
 * Utility class that determines if a List object is sorted in strictly ascending order
 *
 * @author  Dustin Oakes
 * @version 1.0
 * CMSC350 Project 2: Polynomials
 * 12 September 2021
 */
public class OrderedList {

    /**
     * Checks to see if a List of objects is sorted in ascending order
     *
     * @param list  a List of objects that implement the Comparable interface
     * @param <T>   an object that implements the Comparable interface
     *
     * @return      <code>true</code>   if the List is sorted
     *              <code>false</code>  if the list is not sorted
     */
    public static <T extends Comparable<? super T>> boolean checkSorted(List<T> list){

        // create a Comparator using the compareTo() method of the provided objects
        Comparator<T> c = new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        };

        // call the overloaded checkSorted() method to get the return value
        return checkSorted(list, c);

    }

    /**
     * Checks to see if a List of objects is sorted in ascending order
     *
     * @param list  a List of objects that implement the Comparable interface
     * @param <T>   an object that implements the Comparable interface
     * @param c     a Comparator object that will be used to define the sort
     *
     * @return      <code>true</code>   if the List is sorted
     *              <code>false</code>  if the list is not sorted
     */
    public static <T> boolean checkSorted(List<T> list, Comparator<? super T> c) {

        Iterator<T> it = list.iterator(); // instantiate an Iterator for the provided list

        T current;
        T previous = it.next(); // set the previous object to the next object

        while (it.hasNext()) { // while the list has more objects
            current = it.next(); // set the current object to the next object
            if (c.compare(previous, current) > 0) { // if the previous object is greater than the current object
                return false; // the list is not sorted in ascending order
            }
            previous = current; // continue until all objects have been compared
        }
        return true; // if all objects have been compared and are in order, the list is sorted
    }

}
