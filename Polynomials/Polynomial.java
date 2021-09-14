package project2;


import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Polynomial.java
 * Defines an individual polynomial using a custom singly-linked list;
 * Implements Iterable and Comparable interfaces
 *
 * @author  Dustin Oakes
 * @version 1.0
 * CMSC350 Project 2: Polynomials
 * 12 September 2021
 */
public class Polynomial implements Iterable<Polynomial.PElement>, Comparable<Polynomial> {

    private PolynomialExpressionList polynomialExpression;

    final String DOUBLE_PATTERN = "\\d\\.\\d"; // regex pattern to match double values
    final String INT_PATTERN = "\\d"; // regex pattern to match integer values
    final String TOKEN_PATTERN = DOUBLE_PATTERN + "|" + INT_PATTERN; // regex pattern to match double or integer values

    /**
     * Constructor
     * @param expression    String representing a valid polynomial expression;
     *                      must have one exponent for each coefficient and one coefficient for each exponent.     *
     * @throws InvalidPolynomialSyntaxException
     */
    public Polynomial(String expression) throws InvalidPolynomialSyntaxException {
        this.polynomialExpression = toList(expression);
        if (!polynomialExpression.isSorted()) {
            throw new InvalidPolynomialSyntaxException("The provided polynomial was not sorted in descending order.");
            //polynomialExpression.sort(); ^throw an exception instead of sorting the polynomial?
        }
    }


    /**
     * @return  String representation of this Polynomial object.
     */
    @Override
    public String toString() {

        PElement current = polynomialExpression.first; // get the first element of this polynomial
        StringBuilder returnValue = new StringBuilder(current.toString()); // use a StringBuilder to concatenate the string
        while (current.getNextElement() != null) { // while there are more elements in the list
            current = current.getNextElement();
            returnValue.append(" + ").append(current.toString()); // concatenate them to the string
        }

        return returnValue.toString(); // return the completed string.

    }

    /**
     * Compares two Polynomial objects to determine which has the highest degree.
     *
     * @param p2    a Polynomial object to which this Polynomial object
     *              will be compared
     * @return      <code>1</code>   if this Polynomial is greater than the Polynomial
     *                               to which it is being compared.
     *              <code>0</code>   if this Polynomial is equal to the Polynomial
     *                               to which it is being compared.
     *              <code>-1</code>  if this Polynomial is less than the Polynomial
     *                               to which it is being compared.
     */
    @Override
    public int compareTo(Polynomial p2) { return polynomialExpression.compareTo(p2.getPolynomialExpression()); }

    /**
     * @return  an Iterator for this Polynomial object
     */
    @Override
    public Iterator<PElement> iterator() { return polynomialExpression.iterator(); }

    /**
     * @return  value of polynomialExpression as a PolynomialExpressionList object
     */
    public PolynomialExpressionList getPolynomialExpression() {
        return polynomialExpression;
    }

    /**
     * Converts a String representing a valid polynomial expression into a list of polynomial element objects
     * @param   polynomial  String representing a valid polynomial expression
     * @return  PolynomialExpressionList object; a list of polynomial element objects. Returns an empty list if polynomial
     *          is empty or null.
     */
    private PolynomialExpressionList toList (String polynomial) throws InvalidPolynomialSyntaxException {

        double coefficient = 0.0;
        int exponent = 0;

        if (polynomial.isEmpty()) { // if no string was supplied
            throw new InvalidPolynomialSyntaxException("Empty string passed to toList method."); // throw an exception
        }

        PolynomialExpressionList polynomialExpression = new PolynomialExpressionList();
        ArrayDeque<String> tokenQueue = tokenize(polynomial); // call the tokenize method to convert the provided string into a queue of tokens

        if (tokenQueue.isEmpty() || ((tokenQueue.size() % 2) != 0)) { // if the queue of tokens came back empty or has an odd number of tokens
            throw new InvalidPolynomialSyntaxException("Odd number of polynomial elements provided."); // throw an exception
        }

        while (!tokenQueue.isEmpty()) { // while there are more tokens in the queue

            try {
                coefficient = Double.parseDouble(tokenQueue.removeFirst()); // parse the first token to double, store it as the coefficient, then remove it from the queue
                exponent = Integer.parseInt(tokenQueue.removeFirst()); // parse the next token to int, store it as the exponent, then remove it from the queue
            } catch (Exception x) {
                throw new InvalidPolynomialSyntaxException("Wrong type of element provided."); // if we're not able to parse the provided
                // string into double or int as appropriate, throw an exception
            }

            PElement element = new PElement(coefficient, exponent); // instantiate a new polynomial expression element object
            polynomialExpression.add(element); // add the PElement object to the polynomial expression list

        }

        return polynomialExpression;
    }

    /**
     * Separates a String into an ArrayDeque of smaller tokens as Strings
     * delimited according to the TOKEN_PATTERN regex pattern.
     *
     * @param   input String representing a valid polynomial expression.
     * @return  ArrayDeque of tokens as Strings
     */
    private ArrayDeque<String> tokenize(String input) {

        ArrayDeque<String> tokenQueue = new ArrayDeque<>(); // ArrayDeque to store tokens

        // use regex pattern matcher to tokenize the input string...
        Pattern pattern = Pattern.compile(TOKEN_PATTERN);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) { //while the pattern matcher finds matches,
            String s = matcher.group();
            tokenQueue.add(s); // add each match to the ArrayDeque as a new token
        }

        return tokenQueue;

    }


    // ********************************** //
    // ********************************** //
    // ********** Inner Classes ********* //
    // ********************************** //
    // ********************************** //


    /**
     * PolynomialExpressionList
     * Inner class; defines a custom, singly-linked list that stores an individual polynomial;
     * Implements Iterable and Comparable interfaces
     *
     * @author  Dustin Oakes
     * @version 1.0
     */
    protected static class PolynomialExpressionList implements Iterable<PElement>, Comparable<PolynomialExpressionList> {

        protected PElement first;
        protected PElement last;
        protected int numElements = 0;

        /**
         * Constructor
         * Creates an empty PolynomialExpressionList object
         */
        protected PolynomialExpressionList() {
            this.first = null;
            this.last = null;
        }

        /**
         * Adds a new PElement object to the PolynomialExpressionList
         * @param newElement    a valid PElement object that should be added to the list
         */
        protected void add(PElement newElement) {

            if (last == null) {
                first = newElement;
            } else {
                last.setNextElement(newElement);
            }
            last = newElement;
            numElements++;
        }

        /**
         * Checks to see if the PolynomialExpressionList contains objects.
         * @return  <code>true</code>   if there are no objects in the list
         *          <code>false</code>  if there are objects in the list
         */
        protected boolean isEmpty() {
            return first == null; // if the first variable is set to null, the list is empty
        }

        /**
         * Checks to see if the PolynomialExpressionList is full.
         * @return  <code>false</code>  always returns false because the PolynomialExpressionList
         *                              is unbound.
         */
        protected boolean isFull() {
            return false; // the list will never be full unless system runs out of storage
        }

        /**
         * Returns the total number of objects in the PolynomialExpressionList as an integer value
         * @return  integer; number of objects currently in the list
         */
        protected int size() {
            return numElements;
        }

        /**
         * Sorts the elements of the PolynomialExpressionList in order of largest to smallest degree.
         */
        private void sort() {

            if (!isEmpty()) { // if the list is empty, don't try to sort it

                PElement current = first; // the element to be compared
                PElement next; // the next element in line; will be compared to the current element
                boolean sorted; // are the elements of the polynomial expression in the correct order

                do {
                    sorted = true; // start with the assumption that the list is sorted
                    while (current.getNextElement() != null) { // while there are more elements in the expression

                        next = current.getNextElement(); // get the next element for comparison

                        if (current.compareTo(next) < 0) { // if the current element is less than the next element
                            if (current == first) {
                                first = current.getNextElement(); // if we replace the first element in the list, update first
                            }
                            current.setNextElement(next.getNextElement()); // set the current element's link to the next element's link
                            next.setNextElement(current); // set the next element's link to the current element (swap the positions of the current and next elements)
                            sorted = false; // the list wasn't sorted
                        } else {
                            current = next; // if the elements were in the correct order, move on to the next element
                            if (current.getNextElement() == null) {
                                last = current; // make sure the last element in the list is marked correctly.
                            }
                        }
                    }

                } while (!sorted); // continue until no elements needed to be moved.
            }
        }


        /**
         * Checks to see if the elements of this PolynomialExpressionList are sorted from
         * highest to lowest degree, according to their exponents and coefficients
         * @return  <code>true<code/>   if the PolynomialExpressionList is sorted
         *          <code>false</code>  if the PolynomialExpressionList is not sorted
         */
        private boolean isSorted() {

            //return OrderedList.checkSorted(this);

            Iterator<PElement> it = this.iterator(); // instantiate an iterator for the list

            PElement current;
            PElement previous = it.next();

            while (it.hasNext()) { // while there are more items in the list,
                current = it.next();
                if (previous.compareTo(current) < 0) { // if the previous element is less than the current element
                    return false; // the list is not sorted in descending order
                }
                previous = current; // continue comparing elements until all elements have been compared.
            }
            return true; // all elements have been compared and are in descending order.
        }

        /**
         * @return  an Iterator for this PolynomialExpressionList object
         */
        @Override
        public Iterator<PElement> iterator() {
            return new Iterator<PElement>() {

                PElement current = first; // set the first element in the list as the current element
                PElement returnValue; // to hold the element to be returned by the Iterator.

                @Override
                public boolean hasNext() {
                    return current != null; // if the current element is not null, there are more elements
                }

                @Override
                public PElement next() {

                    if (hasNext()) {
                        returnValue = current; // set the current node as the element to be returned
                        current = current.getNextElement(); // make the next element the current element
                        return returnValue; // return the selected element
                    }
                    return null; // if there are no more elements to return, return null
                }
            };
        }


        /**
         * Compares two PolynomialExpressionList objects to determine which has the highest degree.
         *
         * @param p2    a PolynomialExpressionList object to which this PolynomialExpressionList
         *              will be compared
         * @return      <code>1</code>   if this PolynomialExpressionList is greater than the PolynomialExpressionList
         *                               to which it is being compared.
         *              <code>0</code>   if this PolynomialExpressionList is equal to the PolynomialExpressionList
         *                               to which it is being compared.
         *              <code>-1</code>  if this PolynomialExpressionList is less than the PolynomialExpressionList
         *                               to which it is being compared.
         */
        @Override
        public int compareTo(PolynomialExpressionList p2) {
            // since we know the polynomial expressions are sorted,
            // we can compare them one element at a time.

            PElement pe1 = first; // the first element of this polynomial expression
            PElement pe2 = p2.first; // the first element of the second polynomial expression

            do {
                if (pe1.compareTo(pe2) < 0) { // if the first element of the first polynomial is less than the first element of the second polynomial,
                    return -1; // the first polynomial is smaller than the second polynomial
                } else if (pe1.compareTo(pe2) > 0) { // if the first element of the first polynomial is larger than the first element of the second polynomial,
                    return 1; // the first polynomial is larger than the second polynomial
                } else { // if the two elements are equal:

                    if (pe1.getNextElement() == null) { // if the first polynomial has no more elements
                        if (pe2.getNextElement() == null) { // and the 2nd polynomial has no more elements
                            return 0; // the polynomials are equal
                        } else { // if the first polynomial has no more elements, but the second one does,
                            return -1; // the first polynomial is less than the second
                        }
                    } else { // if the first polynomial has more elements
                        if (pe2.getNextElement() == null) { // but the second polynomial doesn't,
                            return 1; // the first polynomial is greater than the second
                        } else { // both polynomials have more elements that we need to compare
                            pe1 = pe1.getNextElement();
                            pe2 = pe2.getNextElement();
                        }
                    }
                }
            } while (pe1 != null && pe2 != null); // continue until we get a return value or one of the polynomials runs out of elements to compare.

            return 0; // if we made it through the whole list and all elements are equal, the polynomials are equal
        }
    }

    /**
     * PElement
     * Inner class; defines one element of a polynomial expression consisting of one coefficient and one exponent
     * Implements Comparable interface
     *
     * @author  Dustin Oakes
     * @version 1.0
     */
    protected static class PElement implements Comparable<PElement> {

        private double coefficient;
        private int exponent;
        private PElement nextElement;

        /**
         * Default constructor
         * creates a mathematically benign polynomial expression element
         */
        public PElement() {
            this.coefficient = 1;
            this.exponent = 0;
            this.nextElement = null;
        }

        /**
         * Constructor
         * @param coefficient   double value representing the coefficient of one element in a polynomial expression
         * @param exponent      integer value representing the exponent of one element in a polynomial expression
         */
        public PElement(double coefficient, int exponent) {
            this.coefficient = coefficient;
            this.exponent = exponent;
            nextElement = null;
        }

        /**
         * Compares two PElement objects to determine which has the highest degree
         * according to their exponent and coefficient
         * @param pe2   PElement object to which this PElement object should be compared
         * @return      <code>1</code>  if this PElement is greater than pe2
         *              <code>0</code>  if this PElement is equal to pe2
         *              <code>-1</code>  if this PElement is less than pe2
         */
        @Override
        public int compareTo(PElement pe2) {

            double pe1Coefficient = this.coefficient; // the coefficient of this element
            double pe2Coefficient = pe2.getCoefficient(); // the coefficient of the second element

            int pe1Exponent = this.exponent; // the exponent of this element
            int pe2Exponent = pe2.getExponent(); // the exponent of the second element

            if (pe1Exponent < pe2Exponent) { // if this element has a smaller exponent than the second element
                return -1; // this element is smaller than the second element
            } else if (pe1Exponent > pe2Exponent) { // if this element has a larger exponent than the second element
                return 1; // this element is larger than the second element
            } else { // if the exponents of the two elements are equal, compare their coefficients
                if (pe1Coefficient < pe2Coefficient) { // if this element has a smaller coefficient
                    return -1; // this element is smaller than the second element
                } else if (pe1Coefficient > pe2Coefficient) { // if this element has a larger coefficient
                    return 1; // this element is larger than the second element
                } else { // if the coefficients of both elements are also equal
                    return 0; // the two elements are equal
                }
            }
        }

        /**
         * @return  String representing the PElement object. Consists of the PElement's
         *          coefficient and exponent combined with a variable
         */
        @Override
        public String toString(){

            String c = ""; // String representing the coefficient of the element
            String e = ""; // String representing the exponent of the element
            String returnValue = ""; // for building the String that will be returned by the method

            if (this.coefficient == 1) { // if the coefficient = 1,
                c = ""; // we don't need to include it in the string
            } else if (this.coefficient > 1) { // if it is greater than one,
                c = String.valueOf(this.coefficient); // we will include it
            }

            if (this.exponent <= 0) { // if the exponent is less than or equal to 0,
                e = ""; // we don't need to include the variable or the exponent in the string (because we are not worried about negative values)
            } else if (this.exponent == 1) { // if the exponent = 1,
                e = "x"; // we only need to include the variable
            } else { // if it is greater than 1,
                e = "x^" + this.exponent; // we need to include the variable and the exponent
            }

            if (this.coefficient <= 0) { // if the coefficient is less than or equal to 0,
                returnValue = ""; // we don't need to return any element at all (because we are not worried about negative values)
            } else {
                returnValue = c + e; // combine the coefficient string and the exponent string
            }

            return returnValue; // and return the result
        }


        // ******************** //
        // Getters and Setters  //
        // ******************** //

        public double getCoefficient() {
            return coefficient;
        }

        public void setCoefficient(double coefficient) {
            this.coefficient = coefficient;
        }

        public int getExponent() {
            return exponent;
        }

        public void setExponent(int exponent) {
            this.exponent = exponent;
        }

        public PElement getNextElement() {
            return nextElement;
        }

        public void setNextElement(PElement nextElement) {
            this.nextElement = nextElement;
        }


    }

}


