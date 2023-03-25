package COMP90015.unimelb.edu;

import COMP90015.unimelb.edu.Request.Request;
import COMP90015.unimelb.edu.Request.RequestType;
import COMP90015.unimelb.edu.Response.Response;
import COMP90015.unimelb.edu.Response.ResponseStatus;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 *
 * @author Gaoyuan Ou(1301025)
 */
public final class Dictionary extends Thread {

    static ArrayList<Item> items;
    Socket socket;
    BufferedReader in;
    BufferedWriter out;

    public Dictionary(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new
                OutputStreamWriter(socket.getOutputStream()));

    }

    @Override
    public void run() {
        String clientMsg = null;
        try {

            try {


            while ((clientMsg = in.readLine()) != null) {

                Request clientRequest = Util.mapper.readValue(clientMsg, Request.class);
                String responseStr = null;
                switch (clientRequest.getRequestType()) {
                    case SEARCH:
                                String meaning = Dictionary.searchForMeaning(clientRequest.getItem().getWord());
                                Response serverResponse = new Response(meaning, ResponseStatus.SUCCESS);
                                responseStr = Util.mapper.writeValueAsString(serverResponse);
                        System.out.println("this is search" + this.getId());
                        break;
                    case ADD:
                                Item itemToAdd = clientRequest.getItem();
                                Dictionary.addWord(itemToAdd.getWord(), itemToAdd.getMeaning());
                                serverResponse = new Response("word added", ResponseStatus.SUCCESS);
                                responseStr = Util.mapper.writeValueAsString(serverResponse);
                        System.out.println("this is Add" + this.getId());

                        break;

                }


                System.out.println("Response sent " + responseStr);

                out.write(responseStr + "\n");
                out.flush();

            }
            }
            catch (SocketException e){
                System.out.println("socket closed");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //    TODO: operations on word
    public static void addWord(String word, String meaning){

        items.add(new Item(word, meaning));
    }

    public static String searchForMeaning(String word){

        try {
            return items.stream()
                    .filter(w -> w.getWord().equalsIgnoreCase(word)).
                    findFirst().get().getMeaning();
        }
        catch (NoSuchElementException e){
            System.out.println("no word");
            return "no such word";
        }
    }

    public static void removeWord(){

    }

    public static Boolean updateWord(String word, String meaning){

        return true;
    }




}
