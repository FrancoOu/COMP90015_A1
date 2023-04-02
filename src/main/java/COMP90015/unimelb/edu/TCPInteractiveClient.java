package COMP90015.unimelb.edu;


import COMP90015.unimelb.edu.UI.ClientUI;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

/**
 * @author Gaoyuan Ou(1301025)
 */
public class TCPInteractiveClient {


    public static void main(String[] args) {
        String host = null;
        int port = 0;
        if (args.length == 2){
            try {
                host = args[0];
                port = Integer.parseInt(args[1]);
            }catch (NumberFormatException e){
                System.out.println("please enter a valid port number");
                System.exit(1);
            }
        }
        else {
            System.out.println("please give the host and port number as arguments.\n" +
                    "for example:\n"+
                    "java –jar DictionaryClient.jar <host-address> <host-port> ");
            System.exit(1);
        }

        Socket socket = null;
        try {
// Create a stream socket bounded to any port and connect it to the
// socket bound to localhost on port 4444
            socket = new Socket(host, port);
            System.out.println("Connection established");
            ClientUI clientUI = new ClientUI(socket);
            clientUI.run();
        } catch (IOException e) {
            System.out.println("connection refused!!");
        } finally {
// Close the socket
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}