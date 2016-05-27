package com.alexlowe.choices;

import java.util.ArrayList;

/**
 * Created by Keyes on 5/25/2016.
 */
public class ChoiceList {
    private String name = "";
    private ArrayList<Choice> choices;

    public ChoiceList(){
        choices = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<Choice> choices) {
        this.choices = choices;
    }
}
