package project1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ExpressionConverterGUI.java
 * <p>
 * Creates and displays a graphical user interface that allows users to convert prefix expressions to
 * postfix expressions and vice versa.
 * <p>
 * Relies on the ConvertExpressions class
 *
 * @author Dustin Oakes
 * @version 1.0
 * CMSC350 Project 1: Expression Converter
 * 28 August 2021
*/
public class ExpressionConverterGUI {

    // private instance variables
    private JFrame frame = new JFrame(); //
    private JPanel panel = new JPanel();
    private JTextField txtPrefix = new JTextField(30);
    private JTextField txtPostfix = new JTextField(30);
    private JLabel lblPrefix = new JLabel("Prefix Expression:");
    private JLabel lblPostfix = new JLabel("Postfix Expression:");
    private JButton btnPrefixToPostfix = new JButton("Prefix to Postfix");
    private JButton btnPostfixToPrefix = new JButton(("Postfix to Prefix"));

    /**
     * Constructor - creates and displays the graphical user interface
     */
    public ExpressionConverterGUI()  {

        // set the border and display layout for the panel
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10,30));
        panel.setLayout(new GridLayout(3, 1));

        // add labels for the first row of the panel
        panel.add(lblPrefix);
        panel.add(lblPostfix);

        // add text fields for the second row of the panel
        panel.add(txtPrefix);
        panel.add(txtPostfix);

        // set the text fields as targets for their respective labels
        lblPrefix.setLabelFor(txtPrefix);
        lblPostfix.setLabelFor(txtPostfix);

        // add action buttons to the panel
        panel.add(btnPrefixToPostfix);
        panel.add(btnPostfixToPrefix);

        // create a ConvertExpressions object that will be used to handle the conversions
        ConvertExpressions converter = new ConvertExpressions();

        // add an action listener to the Prefix to Postfix button
        // that calls the prefixToPostfix() method of the ConvertExpressions class
        btnPrefixToPostfix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // call the prefixToPostfix method of the ConvertExpressions class
                    // using the value of the txtPrefixToPostfix field as input
                    String postfix = converter.prefixToPostfix(txtPrefix.getText());
                    // set the value of the txtPostfixToPrefix text field to the value returned
                    // by the prefixToPostfix() method
                    txtPostfix.setText(postfix);
                } catch (SyntaxErrorException see) { // if the prefixToPostfix method throws an exception,
                    txtPostfix.setText("Invalid Prefix Expression"); // set the text of the txtPostfix text field to a useful message
                    displayError(see.getMessage()); // and display a JOption pane with the error message
                }
            }
        });

        // add an action listener to the Postfix to Prefix button
        // that calls the postfixToPrefix() method of the ConvertExpressions class
        btnPostfixToPrefix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // call the postfixToPrefix() method of the ConvertExpressions class
                    // using the value of the txtPostfix field as input
                    String prefix = converter.postfixToPrefix(txtPostfix.getText());
                    // set the value of the txtPrefix text field to the value returned
                    // by the postfixToPrefix() method
                    txtPrefix.setText(prefix);
                } catch (SyntaxErrorException see) { // if the prefixToPostfix method throws an exception,
                    txtPrefix.setText("Invalid Postfix Expression"); // set the text of the txtPrefix text field to a useful message
                    displayError(see.getMessage()); // and display a JOption pane with the error message
                }
            }
        });


        frame.add(panel, BorderLayout.CENTER); // add the JPanel to the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set the default close operation of the JFrame to exit
        frame.setTitle("Expression Converter"); // set the title of the JFrame to Expression Converter
        frame.pack(); // pack the JFrame,
        frame.setLocationRelativeTo(null); // center it on the screen,
        frame.setVisible(true); // and make it visible to the user

    }

    /**
     * Displays error message in a JOptionPane
     *
     * @param message
     */
    private void displayError(String message) {
        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f, "ERROR: " + message, "WARNING", JOptionPane.WARNING_MESSAGE);
    }

}
