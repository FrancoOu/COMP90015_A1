package COMP90015.unimelb.edu;


import COMP90015.unimelb.edu.UI.ClientUI;
import java.io.*;
import java.net.Socket;

/**
 * @author Gaoyuan Ou(1301025)
 */
public class TCPInteractiveClient {


    public static void main(String[] args) {
        Socket socket = null;
        try {
// Create a stream socket bounded to any port and connect it to the
// socket bound to localhost on port 4444
            socket = new Socket("localhost", 4444);
            System.out.println("Connection established");
            ClientUI clientUI = new ClientUI(socket);
            clientUI.run();
        } catch (IOException e) {
            e.printStackTrace();
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