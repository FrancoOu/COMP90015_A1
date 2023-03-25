package COMP90015.unimelb.edu;

import COMP90015.unimelb.edu.Request.Request;
import COMP90015.unimelb.edu.Request.RequestType;
import COMP90015.unimelb.edu.Response.Response;
import COMP90015.unimelb.edu.Response.ResponseStatus;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Gaoyuan Ou(1301025)
 */
public class TCPInteractiveServer {
    public static void main(String[] args) {

        try{
            Item[] readValue = Util.mapper.readValue(new File("dictionary.json"), Item[].class);
            Dictionary.items = new ArrayList<>(Arrays.asList(readValue));
            System.out.println("dictionary loaded!");
            System.out.println(Dictionary.items);


        }catch (IOException e){
            e.printStackTrace();
        }

        ServerSocket listeningSocket = null;
        Socket clientSocket = null;
        try {
            //Create a server socket listening on port 4444
            listeningSocket = new ServerSocket(4444);
            int i = 0; //counter to keep track of the number of clients
            //Listen for incoming connections for server
            while (true)
            {
//                System.out.println("Server listening on port 4444 for a connection");
//                //Accept an incoming client connection request
                clientSocket = listeningSocket.accept(); //This method will block until a connection request is received
//                i++;
//                System.out.println("Client connection number " + i + " accepted:");
//                //System.out.println("Remote Port: " +clientSocket.getPort());
//                System.out.println("Remote Hostname: " +
//                        clientSocket.getInetAddress().getHostName());
//                System.out.println("Local Port: " +
//                        clientSocket.getLocalPort());
                //Get the input/output streams for reading/writing data from/to the socket



                Dictionary dictionary = new Dictionary(clientSocket);
                dictionary.start();
                //Read the message from the client and reply
                //Notice that no other connection can be accepted and processed until the last line of
                //code of this loop is executed, incoming connections have to wait until the current
                //one is processed unless...we use threads!
                try
                {


                    FileWriter writer = new FileWriter("dictionary.json");
                    String dictionaryJSON = Util.mapper.writeValueAsString(Dictionary.items);
                    writer.write(dictionaryJSON);
                    writer.close();
                    System.out.println("new JSON written");




                }
                catch(SocketException e)
                {
                    System.out.println("closed...");
                }

            }
        }
        catch (SocketException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("here");
            e.printStackTrace();
        }
        finally
        {
            if(listeningSocket != null)
            {
                try
                {
// close the server socket
                    listeningSocket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}