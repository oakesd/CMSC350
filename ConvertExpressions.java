package project1;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import project1.SyntaxErrorException;

/**
 * ConvertExpressions.java
 * Converts mathematical prefix expressions to mathematical postfix expressions and vice versa.
 *
 * @author  Dustin Oakes
 * @version 1.0
 * CMSC350 Project 1: Expression Converter
 * 28 August 2021
 */
public class ConvertExpressions {

    // constants for regex patterns
    static final String TOKEN_PATTERN = "\\d+|[a-zA-Z]+|(\\+|-|\\*|=|:|/|\\^)";
    static final String ALPHANUMERIC = "^[a-zA-Z0-9]+$";

    // shared instance variables
    private Stack<String> reversalStack; // stack to store input in reverse order
    private Stack<String> operandStack; // stack to store operands for output strings
    private ArrayList<String> tokenList; // array list to store tokens
    private String builder = ""; // variable used for concatenation of output string

    /**
     * Constructor
     */
    public ConvertExpressions(){
        this.operandStack = new Stack<String>();
        this.reversalStack = new Stack<String>();
        this.tokenList = new ArrayList<String>();
        this.builder = "";
    }

    /**
     * Converts a mathematical prefix expression to a mathematical postfix expression.
     *
     * @param   prefix String representing the prefix expression to be converted
     * @return  String representing the equivalent postfix expression
     * @throws  SyntaxErrorException
     */
    public String prefixToPostfix(String prefix) throws SyntaxErrorException {

        clearVariables(); // make sure we're starting with empty stacks and lists

        tokenList = tokenize(prefix); // tokenize the string containing the prefix expression
        for (String token : tokenList){ reversalStack.push(token); } // push the tokens onto the reversal stack

        while (!reversalStack.isEmpty()){ // while the reversal stack is not empty

                String s = reversalStack.pop(); // pop the next token from the reversal stack

                if (isOperand(s)) { // if it is an operand,
                    operandStack.push(s); // push it onto the operand stack
                } else { // else, it is an operator
                    // pop two operands off the operand stack,
                    // concatenate the two operands and one operator

                    // use a loop to concatenate operands, checking each iteration if the stack is empty
                    for (int i = 0; i < 2; i++) {
                        if (!operandStack.isEmpty()) { // if the operandStack is not empty
                            if (i == 0) { // if it's the first token on the operandStack
                                builder = operandStack.pop(); // pop the operand off the stack as the left operand
                            } else { // if it's not the first operand on the stack
                                builder = builder + " " + operandStack.pop() + " " + s; // concatenate left operand + right operand + operator
                            }
                        } else { // if there aren't enough operands on the operandStack
                            throw new SyntaxErrorException("Tried to pop an empty stack."); // throw an exception
                        }
                    }

                    if (!builder.isEmpty()) { // if the builder string is not blank,
                        operandStack.push(builder); // push it onto the operand stack
                    }
                }

        }

        String postfix = operandStack.isEmpty() ? "" : operandStack.pop(); // if the operand stack is not empty,
        // pop the postfix expression off the stack, otherwise set the result to an empty string

        if (!operandStack.isEmpty()) { // if the operand stack is not empty after the conversion is complete,
            throw new SyntaxErrorException("Stack is not empty after conversion"); // throw an exception
        }

        return postfix;

    }

    /**
     * Converts a mathematical postfix expression to a mathematical prefix expression
     *
     * @param   postfix String representing the postfix expression to be converted
     * @return  String representing the equivalent prefix expression
     * @throws  SyntaxErrorException
     */
    public String postfixToPrefix(String postfix) throws SyntaxErrorException {

        clearVariables();  // make sure we're starting with empty stacks and lists

        tokenList = tokenize(postfix); // tokenize the string containing the postfix expression

        for (String s : tokenList) { // while there are more tokens, get the next token

            if (isOperand(s)) { // if it is an operand,
                operandStack.push(s); // push it onto the operandStack
            } else { // else it is an operator.
                // pop two operands off of the operandStack,
                // and create a string with the operator followed by two operands

                // use a loop to concatenate operands, checking each iteration if the stack is empty
                for (int i = 0; i < 2; i++) {
                    if (!operandStack.isEmpty()) { // if the stack is not empty
                        if (i == 0) { // and it's the first token on the operandStack
                            builder = operandStack.pop(); // pop it off the stack as the right operand
                        } else { // if the stack is still not empty and this isn't the first token we popped off the stack
                            builder = s + " " + operandStack.pop() + " " + builder; // concatenate operator + left operand + right operand
                        }
                    } else { // if the operandStack doesn't have enough operands
                        throw new SyntaxErrorException("Tried to pop an empty stack."); // throw an exception
                    }
                }

                if (!builder.isEmpty()){ // if the builder string is not blank,
                    operandStack.push(builder); // push it back onto the operand stack
                }
            }
        }

        String prefix = operandStack.isEmpty() ? "" : operandStack.pop(); // if the operand stack is not empty,
        // pop the prefix expression off the stack, otherwise set the result to an empty string

        if (!operandStack.isEmpty()) { // if the operand stack is not empty after the conversion is complete,
            throw new SyntaxErrorException("Stack is not empty after conversion"); // throw an exception
        }

        return prefix;

    }

    /*
    ********************
    ** HELPER METHODS **
    ********************
    */

    /**
     * Separates a String into an ArrayList of smaller tokens as Strings
     * delimited according to the TOKEN_PATTERN regex pattern.
     *
     * @param   input String representing the prefix expression to be converted
     * @return  ArrayList of tokens as Strings
     */
    public ArrayList<String> tokenize(String input) {

        ArrayList<String> tokenList = new ArrayList<String>(); // ArrayList to store tokens

        // use regex pattern matcher to tokenize the input string...
        Pattern pattern = Pattern.compile(TOKEN_PATTERN);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) { //while the pattern matcher finds matches,
            String s = matcher.group();
            tokenList.add(s); // add each match to the ArrayList as a new token
        }

        return tokenList;

    }

    /**
     * Checks to see if a String is an operand by determining if it is alphanumeric.
     * If the String is alphanumeric, it is an operand.
     *
     * @param s The String to be tested
     * @return  <code>true</code> if s is an operand
     *          <code>false</code> if s is not an operand
     */
    public boolean isOperand(String s) {
        // use regex pattern matcher to tokenize the input string...
        Pattern pattern = Pattern.compile(ALPHANUMERIC);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches(); // if the input string matches the specified pattern, return true
                                    // otherwise, return false.
    }

    /**
     *  Clears values of all instance variables
     */
    private void clearVariables() {
        operandStack.clear();
        reversalStack.clear();
        tokenList.clear();
        builder = "";
    }

}
