package com.alexlowe.choices;

/**
 * Created by dell on 1/24/2016.
 */
public class Choice {
    private String choiceText;
    private boolean isSelected;

    public Choice(String choiceText){
        this.choiceText = choiceText;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
