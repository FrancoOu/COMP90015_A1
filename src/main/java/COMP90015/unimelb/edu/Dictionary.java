package COMP90015.unimelb.edu;

import COMP90015.unimelb.edu.Request.Request;
import COMP90015.unimelb.edu.Response.Response;
import COMP90015.unimelb.edu.Response.ResponseStatus;
import COMP90015.unimelb.edu.model.Item;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @author Gaoyuan Ou(1301025)
 */
public class Dictionary extends Thread {


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
                    Response serverResponse;
                    switch (clientRequest.getRequestType()) {
                        case SEARCH:
                            serverResponse = searchForMeaning(clientRequest.getItem().getWord());
                            responseStr = Util.mapper.writeValueAsString(serverResponse);
                            System.out.println("this is search" + this.getId());
                            break;
                        case ADD:
                            Item itemToAdd = clientRequest.getItem();
                            serverResponse = addWord(itemToAdd);
                            responseStr = Util.mapper.writeValueAsString(serverResponse);
                            System.out.println("this is Add" + this.getId());
                            break;
                        case REMOVE:
                            Item itemToDelete = clientRequest.getItem();
                            serverResponse = removeWord(itemToDelete.getWord());
                            responseStr = Util.mapper.writeValueAsString(serverResponse);
                            break;
                        case UPDATE:
                            Item itemToUpdate = clientRequest.getItem();
                            serverResponse = updateWord(itemToUpdate.getWord(), itemToUpdate.getMeaning());
                            responseStr = Util.mapper.writeValueAsString(serverResponse);

                    }


                    System.out.println("Response sent " + responseStr);

                    out.write(responseStr + "\n");
                    out.flush();

                }
            } catch (SocketException e) {
                System.out.println("socket closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
                System.out.println("client " + this.getId()+" disconnected");
        }
    }

    //    TODO: operations on word
    synchronized public Response addWord(Item item) {
        String message;
        ResponseStatus responseStatus;
        if (!wordExist(item)) {
            items.add(item);
            saveDictionary();
            message = "word added!";
            responseStatus = ResponseStatus.SUCCESS;
        } else {
            message = "word already existed";
            responseStatus = ResponseStatus.FAIL;
        }
        return new Response(message, responseStatus);
    }

    public Response searchForMeaning(String word) {
        String message;
        ResponseStatus responseStatus;
        try {
            message = "The meaning of " + word + ": " + items.stream()
                    .filter(w -> w.getWord().equalsIgnoreCase(word)).
                    findFirst().get().getMeaning();
            responseStatus = ResponseStatus.SUCCESS;
        } catch (NoSuchElementException e) {
            System.out.println("no word");
            message = "no such word";
            responseStatus = ResponseStatus.NOTFOUND;

        }
        return new Response(message, responseStatus);

    }

    synchronized public Response removeWord(String word) {
        String message;
        ResponseStatus responseStatus;
        if (!wordExist(new Item(word))) {
            System.out.println("no word to remove");
            message = "no such word";
            responseStatus = ResponseStatus.NOTFOUND;
        } else {
            items.removeIf(item -> item.getWord().equalsIgnoreCase(word));
            message = "word removed!";
            responseStatus = ResponseStatus.SUCCESS;
            saveDictionary();

        }
        return new Response(message, responseStatus);

    }

    synchronized public Response updateWord(String word, String meaning) {
        String message;
        ResponseStatus responseStatus;
        if (wordExist(new Item(word))) {
            Item itemToUpdate = items.stream().filter((item -> item.getWord().equalsIgnoreCase(word))).findFirst().get();
            itemToUpdate.setMeaning(meaning);
            saveDictionary();
            message = "update successful";
            responseStatus = ResponseStatus.SUCCESS;
            saveDictionary();
        } else {
            message = "no such word";
            responseStatus = ResponseStatus.NOTFOUND;
        }
        return new Response(message, responseStatus);
    }

    public Boolean wordExist(Item item) {
        return items.stream().
                anyMatch(item1 -> item1.getWord().equalsIgnoreCase(item.getWord()));
    }


    public void saveDictionary() {
        try {
            FileWriter writer = new FileWriter("dictionary.json");
            String dictionaryJSON = Util.mapper.writeValueAsString(Dictionary.items);
            writer.write(dictionaryJSON);
            writer.close();
            System.out.println("new JSON written");
        } catch (IOException e) {
            System.out.println("cannot save the dictionary");
        }
    }


}
