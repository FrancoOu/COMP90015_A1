package COMP90015.unimelb.edu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to store the information of a dictionary item
 *
 * @author Gaoyuan Ou(1301025)
 */

public class Item {

    private String word;
    private String meaning;

    public Item(@JsonProperty("word") String word, @JsonProperty("meaning") String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public Item(String word) {
        this.word = word;

    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "Item{" +
                "word='" + word + '\'' +
                ", meaning='" + meaning + '\'' +
                '}';
    }
}
