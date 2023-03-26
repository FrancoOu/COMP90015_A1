package COMP90015.unimelb.edu;

import COMP90015.unimelb.edu.Request.Request;
import COMP90015.unimelb.edu.Request.RequestType;
import COMP90015.unimelb.edu.Response.Response;
import COMP90015.unimelb.edu.UI.ClientUI;
import COMP90015.unimelb.edu.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

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