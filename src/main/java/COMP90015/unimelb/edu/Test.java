package COMP90015.unimelb.edu;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {

        JFrame f = new JFrame();
        JButton button = new JButton();

        button.setBounds(130,100,100,40);
        f.add(button);
        f.setLayout(null);
        f.setSize(500,500);
        f.setVisible(true);
        ObjectMapper  mapper = new ObjectMapper();
        try{
            Item[] readValue = mapper.readValue(new File("dictionary.json"), Item[].class);
            ArrayList<Item> values = new ArrayList<Item>(Arrays.asList(readValue));
            System.out.println(values.get(1).getMeaning());

        }
        catch (IOException e){
            System.out.println("sorry can't find the file");
            e.printStackTrace();

        }
    }
}
