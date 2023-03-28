package COMP90015.unimelb.edu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DictionaryGUI extends JFrame implements ActionListener {
    private JTextField searchField, wordField, meaningField;
    private JTextArea resultArea;
    private JButton searchButton, addButton, updateButton, deleteButton;
    private NewDictionary dictionary;

    public DictionaryGUI() {
        super("Dictionary");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        JLabel wordLabel = new JLabel("Word:");
        wordField = new JTextField(20);

        JLabel meaningLabel = new JLabel("Meaning:");
        meaningField = new JTextField(20);

        resultArea = new JTextArea(20, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        addButton = new JButton("Add");
        addButton.addActionListener(this);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);

        // Layout components
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel addPanel = new JPanel(new GridLayout(2, 2));
        addPanel.add(wordLabel);
        addPanel.add(wordField);
        addPanel.add(meaningLabel);
        addPanel.add(meaningField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        resultPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(addPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);
        setVisible(true);

        dictionary = new NewDictionary(); // initialize dictionary
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String word = searchField.getText();
            String meaning = dictionary.search(word);
            if (meaning != null) {
                resultArea.setText(meaning);
            } else {
                resultArea.setText("Word not found.");
            }
        } else if (e.getSource() == addButton) {
            String word = wordField.getText();
            String meaning = meaningField.getText();
            dictionary.add(word, meaning);
            resultArea.setText("Word added.");
            wordField.setText("");
            meaningField.setText("");
        } else if (e.getSource() == updateButton) {
            String word = wordField.getText();
            String meaning = meaningField.getText();
            boolean success = dictionary.update(word, meaning);
            if (success) {
                JOptionPane.showMessageDialog(this, "Word updated.");
            } else {
                JOptionPane.showMessageDialog(this, "Word not found.");
            }
            wordField.setText("");
            meaningField.setText("");
        } else if (e.getSource() == deleteButton) {
            String word = wordField.getText();
            boolean success = dictionary.delete(word);
            if (success) {
                JOptionPane.showMessageDialog(this, "Word deleted.");
            } else {
                JOptionPane.showMessageDialog(this, "Word not found.");
            }
            wordField.setText("");
            meaningField.setText("");
        }
    }

    public static void main(String[] args) {
        DictionaryGUI gui = new DictionaryGUI();
        gui.setVisible(true);
    }
}

class NewDictionary {
    private java.util.Map<String, String> map;

    public NewDictionary() {
        map = new java.util.HashMap<>();
    }

    public String search(String word) {
        return map.get(word);
    }

    public void add(String word, String meaning) {
        map.put(word, meaning);
    }

    public boolean delete(String word) {

        return false;
    }

    public boolean update(String word, String meaning) {
        return false;
    }
}
