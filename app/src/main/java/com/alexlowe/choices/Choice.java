package com.alexlowe.choices;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dell on 1/24/2016.
 */
public class Choice implements Serializable{
    private String choiceText;
    private boolean isSelected;

    public static ArrayList<Choice> gChoice = null;

    public Choice(String choiceText) {
        this.choiceText = choiceText;
    }

    public Choice(Choice choice) {
        this.choiceText = choice.getChoiceText();
        this.isSelected = choice.getSelected();
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
