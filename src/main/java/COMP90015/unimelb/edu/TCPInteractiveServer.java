package COMP90015.unimelb.edu;

import COMP90015.unimelb.edu.model.Item;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Gaoyuan Ou(1301025)
 */
public class TCPInteractiveServer extends TCPInteractiveClient {
    public static void main(String[] args) {

        try {
            Item[] readValue = Util.mapper.readValue(new File("dictionary.json"), Item[].class);
            Dictionary.items = new ArrayList<>(Arrays.asList(readValue));
            System.out.println("dictionary loaded!");
            System.out.println(Dictionary.items);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerSocket listeningSocket = null;
        Socket clientSocket = null;
        try {
            //Create a server socket listening on port 4444
            listeningSocket = new ServerSocket(4444);
            while (true) {
                clientSocket = listeningSocket.accept(); //This method will block until a connection request is received
                Dictionary dictionary = new Dictionary(clientSocket);
                dictionary.start();
                try {
                    FileWriter writer = new FileWriter("dictionary.json");
                    String dictionaryJSON = Util.mapper.writeValueAsString(Dictionary.items);
                    writer.write(dictionaryJSON);
                    writer.close();
                    System.out.println("new JSON written");

                } catch (SocketException e) {
                    System.out.println("closed...");
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            System.out.println("here");
            e.printStackTrace();
        } finally {
            if (listeningSocket != null) {
                try {
// close the server socket
                    listeningSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}