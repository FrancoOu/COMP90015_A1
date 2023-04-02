package COMP90015.unimelb.edu.UI;


import COMP90015.unimelb.edu.Request.Request;
import COMP90015.unimelb.edu.Request.RequestType;
import COMP90015.unimelb.edu.Response.Response;
import COMP90015.unimelb.edu.Response.ResponseStatus;
import COMP90015.unimelb.edu.Util;
import COMP90015.unimelb.edu.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Gaoyuan Ou(1301025)
 */
public class ClientUI extends JFrame {
    private JTextField searchField, wordField, meaningField;
    private JTextArea resultArea;
    private JButton searchButton, addButton, updateButton, deleteButton;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public ClientUI(Socket socket) {
        super("Dictionary");

        this.socket = socket;

        try {
            // Get the input/output streams for reading/writing data from/to the socket
            in = new BufferedReader(new
                    InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new
                    OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create search label, text field and button
        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        // Add listener on search button
        searchButton.addActionListener(e -> {
            Request request = new Request(RequestType.SEARCH, new Item(searchField.getText()));
            sendRequest(out, request);

            searchField.setText("");
        });

        // Create word label and text field
        JLabel wordLabel = new JLabel("Word:");
        wordField = new JTextField(20);

        // Create meaning label and text field
        JLabel meaningLabel = new JLabel("Meaning:");
        meaningField = new JTextField(20);

        // Create text area for result
        resultArea = new JTextArea(20, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Create add button and listener
        addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            Request request = new Request(RequestType.ADD, new Item(wordField.getText(), meaningField.getText()));
            sendRequest(out, request);

            wordField.setText("");
            meaningField.setText("");

        });

        // Create update button and listener
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            Request request = new Request(RequestType.UPDATE, new Item(wordField.getText(), meaningField.getText()));
            sendRequest(out, request);

            wordField.setText("");
            meaningField.setText("");
        });

        // Create update button and listener
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {

            int i  = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this word?", "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (i == 0) {
                Request request = new Request(RequestType.REMOVE, new Item(wordField.getText()));
                sendRequest(out, request);
                wordField.setText("");
                meaningField.setText("");
            }

        });

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


        // Create a window listener
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    System.out.println("closed");
                    socket.close();


                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setVisible(true);

    }

    private void sendRequest(BufferedWriter out, Request request) {
        String requestStr = null;
        try {
            requestStr = Util.mapper.writeValueAsString(request);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        try {
            out.write(requestStr + "\n");
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {

            while (!socket.isClosed()) {
                String serverResponseStr = null;
                if ((serverResponseStr = in.readLine()) != null) {
                    System.out.println(serverResponseStr);
                    Response serverResponse = Util.mapper.readValue(serverResponseStr, Response.class);
                    if (serverResponse.getStatus() == ResponseStatus.NOTFOUND || serverResponse.getStatus() == ResponseStatus.FAIL){
                        JOptionPane.showMessageDialog(this, serverResponse.getMessage());
                    }
                    else {
                        resultArea.setText(serverResponse.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
