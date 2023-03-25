package COMP90015.unimelb.edu;

import COMP90015.unimelb.edu.Request.Request;
import COMP90015.unimelb.edu.Request.RequestType;
import COMP90015.unimelb.edu.Response.Response;
import COMP90015.unimelb.edu.UI.ClientUI;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author Gaoyuan Ou(1301025)
 */
public class TCPInteractiveClient {


    public static void main(String[] args) {
        ClientUI clientUI = new ClientUI();


        Socket socket = null;
        try {
// Create a stream socket bounded to any port and connect it to the
// socket bound to localhost on port 4444
            socket = new Socket("localhost", 4444);
            System.out.println("Connection established");
// Get the input/output streams for reading/writing data from/to the socket
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new
                    OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            //add an event listener on search button
            clientUI.getSearchButton().addActionListener((e -> {
                Request request = new Request(new Item(clientUI.getWordInputText()));
                sendRequest(out, request);
            }));

            //add an event listener on add button
            clientUI.getAddButton().addActionListener((e -> {
                Request request = new Request(RequestType.ADD, new Item(clientUI.getWordInputText(), clientUI.getMeaningTextField().getText()));
                sendRequest(out, request);
            }));

            while (!socket.isClosed()) {
//                System.out.println(clientUI.isShowing());
                String serverResponseStr = null;
                if ((serverResponseStr = in.readLine()) != null) {

                    System.out.println(serverResponseStr);
                    Response serverResponse = Util.mapper.readValue(serverResponseStr, Response.class);
                    clientUI.getMeaningTextField().setText(serverResponse.getMessage());
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
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

    private static void sendRequest(BufferedWriter out, Request request) {
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
}