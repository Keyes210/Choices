package com.alexlowe.choices;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Keyes on 5/25/2016.
 */
public class ChoiceList {
    private String mName = "";
    private ArrayList<Choice> mChoices;
    private Date mDate;

    public static ArrayList<ChoiceList> masterList;

    public ChoiceList(ArrayList<Choice> choices){
        mChoices = new ArrayList<>();
        this.mDate = new Date();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public ArrayList<Choice> getChoices() {
        return mChoices;
    }

    public void setChoices(ArrayList<Choice> choices) {
        this.mChoices = choices;
    }
}
