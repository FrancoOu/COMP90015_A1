package COMP90015.unimelb.edu;

import COMP90015.unimelb.edu.model.Item;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import java.lang.System;

/**
 * @author Gaoyuan Ou(1301025)
 */
public class TCPInteractiveServer extends TCPInteractiveClient {
    public static void main(String[] args) {

        int port = 0;
        String fileName = null;

        if (args.length == 2){
            try {
                port = Integer.parseInt(args[0]);
                fileName= args[1];
            }catch (NumberFormatException e){
                System.out.println("please enter a valid port number");
                System.exit(1);
            }
        }
        else {
            System.out.println("please give the port number and file name as arguments.\n" +
                    "for example:\n"+
                    " java –jar DictionaryServer.jar <port> <dictionary-file> ");
            System.exit(1);
        }

        try {
            Item[] readValue = Util.mapper.readValue(new File(fileName), Item[].class);
            Dictionary.items = new ArrayList<>(Arrays.asList(readValue));
            System.out.println("dictionary loaded!");
            System.out.println(Dictionary.items);
        } catch (IOException e) {
            System.out.println("cannot find the dictionary file");
            System.exit(1);
        }
        ServerSocket listeningSocket = null;
        Socket clientSocket = null;
        try {
            //Create a server socket listening on port 4444
            listeningSocket = new ServerSocket(port);
            while (true) {
                    clientSocket = listeningSocket.accept();//This method will block until a connection request is received
                    System.out.println("new client connected");

                    // Do something while the client socket is open
                    Dictionary dictionary = new Dictionary(clientSocket);
                    dictionary.start();
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