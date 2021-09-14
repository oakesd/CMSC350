package project1;

/**
 * SyntaxErrorException.java
 * User defined exception class; extends Exception class
 *
 * @author  Dustin Oakes
 * CMSC350 Project 1: Expression Converter
 * 28 August 2021
 */
public class SyntaxErrorException extends Exception {

    /**
     * Default Constructor passes default syntax error message to the Exception class
     */
    public SyntaxErrorException() {
        super("There was a problem with the syntax of the expression you entered.");
    }

    /**
     * Constructor
     * @param message   Message describing the exception that occurred.
     */
    public SyntaxErrorException(String message) {
        super(message);
    }
}
