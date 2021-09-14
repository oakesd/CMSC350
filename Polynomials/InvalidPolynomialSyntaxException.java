package project2;

/**
 * InvalidPolynomialSyntaxException.java
 * User-defined exception class; extends Exception
 *
 * @author  Dustin Oakes
 * @version 1.0
 * CMSC350 Project 2: Polynomials
 * 12 September 2021
 */
public class InvalidPolynomialSyntaxException extends Exception {

    public InvalidPolynomialSyntaxException() { super("Invalid polynomial syntax detected."); }

    public InvalidPolynomialSyntaxException(String message) { super(message); }

}
