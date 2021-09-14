# CMSC350

Polynomials:  A Java application that accepts as input a text file containing polynomial expressions as a string of coefficient and exponent values;
              Converts the strings into a custom, singly-linked list of polynomial element objects;
              Stores the polynomials in an ArrayList of polynomial element object lists (Polynomials);
              Checks to see if the ArrayList of Polynomials are sorted according to strong order (considers both coefficient and exponent values) as well as 
              weak order (considers only exponent values).
              
              Sample polynomial input format:  4.5 4 5.7 2 8.6 0
                                  represents:  4.5x^4 + 5.7x^2 + 8.6
              
              
              
ExpressionConverter: 
              A Java application that creates a JFrame GUI;
              Allows the user to convert mathematical prefix expressions to mathematical postfix expressions and vice versa.
              
              Sample prefix expression:  * 2 + 2 - + 12 9 2
                           converts to:  2 2 12 9 + 2 - + *
