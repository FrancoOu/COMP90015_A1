package COMP90015.unimelb.edu.UI;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Gaoyuan Ou(1301025)
 */
public class ClientUI extends JFrame {
    private JTextField wordTextField, meaningTextField;
    private JButton searchButton, addButton, button3;

    public ClientUI() {
        // Set up the JFrame
        setTitle("Dictionary");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the input boxes
        wordTextField = new JTextField(10);
        meaningTextField = new JTextField(10);

        // Set up the buttons
        searchButton = new JButton("Search");
        addButton = new JButton("Add");
        button3 = new JButton("Button 3");

        // Add the components to the JFrame
        JPanel inputPanel = new JPanel(new GridLayout(2, 3));
        inputPanel.add(new JLabel("Word:"));
        inputPanel.add(wordTextField);
        inputPanel.add(searchButton);
        inputPanel.add(new JLabel("Meaning:"));
        inputPanel.add(meaningTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(button3);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Display the JFrame
        setVisible(true);

    }

    public String getWordInputText(){
        return wordTextField.getText();
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getMeaningTextField() {
        return meaningTextField;
    }

    public JButton getAddButton() {
        return addButton;
    }


}
