package project2;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Main.java
 * Runs the Polynomial application; prompts user to select a text file containing valid polynomial expressions;
 * Displays the imported text, converts the text to Polynomial objects, adds them to an ArrayList of Polynomial objects,
 * checks and reports the strong-order and weak-order sorting of the ArrayList of Polynomial objects
 *
 * @author  Dustin Oakes
 * @version 1.0
 * CMSC350 Project 2: Polynomials
 * 12 September 2021
 */
public class Main {

    private static ArrayList<Polynomial> polynomialArrayList = new ArrayList<>(); // ArrayList to hold the imported polynomials

    // custom Comparator defined by Lambda expression, used to check weak sort
    // compares two Polynomial objects according to their exponents
    private static Comparator<Polynomial> customSortingComparator = (Polynomial p1, Polynomial p2) -> {

        Polynomial.PElement pe1 = p1.getPolynomialExpression().first; // get the first element of the first polynomial expression
        Polynomial.PElement pe2 = p2.getPolynomialExpression().first; // get the first element of the second polynomial expression

         do {

            int pe1Exponent = pe1.getExponent(); // get the exponent of the first element
            int pe2Exponent = pe2.getExponent(); // get the exponent of the second element

            if (pe1Exponent > pe2Exponent) { // if the exponent of the first element of the first polynomial is
                                            // greater than the exponent of the first element of the second polynomial,
                return 1; // the first polynomial is greater than the second polynomial
            } else if (pe1Exponent < pe2Exponent) { // if the exponent of the first element of the first polynomial is
                                                    // less than the exponent of the first element of the second polynomial,
                return -1; // the first polynomial is less than the second polynomial
            } else { // if the two exponents are equal:

                if (pe1.getNextElement() == null) { // if the first polynomial has no more elements
                    if (pe2.getNextElement() == null) { // and the 2nd polynomial has no more elements
                        return 0; // the polynomials are equal
                    } else { // if the first polynomial has no more elements, but the second one does,
                        if (pe2.getNextElement().getExponent() > 1) { // and the second polynomial's next coefficient is greater than 1
                            return -1; // the first polynomial is less than the second
                        } else { // if the second polynomial's next exponent is not greater than 1
                            return 0; // the polynomials are equal
                        }
                    }
                } else { // if the first polynomial has more elements
                    if (pe2.getNextElement() == null) { // but the second polynomial doesn't,
                        if (pe1.getNextElement().getExponent() > 1) { // and the first polynomial's next coefficient is greater than 1
                            return 1; // the first polynomial is greater than the second
                        } else {
                            return 0; // the polynomials are equal
                        }
                    } else { // both polynomials have more elements that we need to compare
                        pe1 = pe1.getNextElement();
                        pe2 = pe2.getNextElement();
                    }
                }
            }

         } while ((pe1 != null) && (pe2 != null)); // continue until we get a return value
                                                    // or one of the polynomials runs out of elements to compare.

        return 0; // if we made it through the whole list and all elements are equal, the polynomials are equal
    };

    /**
     * Main Method
     * Executes the Polynomials application
     * @param args  Not used
     */
    public static void main (String[] args) {

        promptUser();
        importPolynomials();
        displayPolynomials();
        checkIfSorted();

        System.exit(0);

    }

    /**
     * Allows the user to select the input file from the default directory by using an object of the JFileChooser class.
     * @return  String representing the file path of the selected file.
     */
    private static String getFilePath(){

        JFileChooser fileChooser = new JFileChooser(); // instantiate a JFileChooser object
        int returnValue = fileChooser.showOpenDialog(null); // display the file chooser

        if (returnValue == JFileChooser.APPROVE_OPTION) { // if the user selects a file
            File file = fileChooser.getSelectedFile(); // get the file
            String filePath = file.getPath(); // get the file's file path
            return filePath; // return the file path
        } else {
            return ""; // if no file is selected, return an empty string.
        }
    }

    /**
     * Imports polynomials from a text file as String objects, converts them into Polynomial objects,
     * then adds them to an ArrayList of Polynomial objects
     */
    private static void importPolynomials(){

        String filePath = getFilePath(); // get the file path to a file selected by the user

        if (!filePath.isEmpty()){ // if a file path is returned
            System.out.println("\nImported Text:");
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath)); // create a file reader
                String s = "";
                while ((s=br.readLine()) != null) {
                    System.out.println(s); // echo the lines of text as they're imported as Strings
                    try {
                        Polynomial polynomial = new Polynomial(s); // try to convert the Strings to Polynomial objects
                        polynomialArrayList.add(polynomial); // and add the Polynomial to an ArrayList
                    } catch (InvalidPolynomialSyntaxException ipse) {
                        displayError(ipse.getMessage()); // if an exception occurs, display an error message
                        System.exit(1); // and exit the program with code 1
                    }
                }
            } catch (Exception ex) { // catch any unexpected exceptions just a precaution
                displayError(ex.getMessage()); // if an exception occurs, display an error message
                System.exit(1); // and exit the program with code 1
            }
        }
    }

    /**
     * Displays formatted Polynomial expressions to the user.
     */
    private static void displayPolynomials(){

        System.out.println("\nPolynomial Expressions:");

        for (Polynomial p : polynomialArrayList) {
            System.out.println(p);
        }
    }

    /**
     * Prompts user to select a file that contains polynomial expressions
     */
    private static void promptUser(){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Select a file that contains polynomial expressions.");
    }

    /**
     * Displays the provided error message in a JOptionPane
     * @param message   String representing the error message to be displayed
     */
    private static void displayError(String message) {
        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f, "ERROR: " + message, "WARNING", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Checks if an ArrayList of Polynomial objects is sorted by strong order
     * using the compareTo() method of the Polynomial class
     * @param list  ArrayList containing Polynomial objects
     * @return      <code>YES</code>    if the list is sorted by strong order
     *              <code>NO</code>     if the list is not sorted by strong order
     */
    private static String isStrongSorted(ArrayList<Polynomial> list) {
        return OrderedList.checkSorted(list) ? "YES" : "NO";
    }

    /**
     * Checks if an ArrayList of Polynomial objects is sorted by weak order
     * using a Comparator
     * @param list  ArrayList containing Polynomial objects
     * @return      <code>YES</code>    if the list is sorted by weak order
     *              <code>NO</code>     if the list is not sorted by weak order
     */
    private static String isWeakSorted(ArrayList<Polynomial> list, Comparator<Polynomial> c) {
        return OrderedList.checkSorted(list, c) ? "YES" : "NO";
    }

    /**
     * Checks if an ArrayLIst of Polynomial objects is sorted by Strong and/or Weak order
     * and displays the results to the user.
     */
    private static void checkIfSorted() {
        System.out.println("\nStrong Sorted? " + isStrongSorted(polynomialArrayList));
        System.out.println("Weak Sorted? " + isWeakSorted(polynomialArrayList, customSortingComparator));
    }

}
