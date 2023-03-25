package COMP90015.unimelb.edu;

import java.util.ArrayList;

/**
 *
 * @author Gaoyuan Ou(1301025)
 */
public final class Dictionary {

    static ArrayList<Item> items;

//    TODO: operations on word
    public static void addWord(String word, String meaning){

        items.add(new Item(word, meaning));
    }

    public static String searchForMeaning(String word){
        return items.stream()
                .filter(w -> w.getWord().equalsIgnoreCase(word)).
                findFirst().get().getMeaning();
    }

    public static void removeWord(){

    }

    public static Boolean updateWord(String word, String meaning){

        return true;
    }




}
