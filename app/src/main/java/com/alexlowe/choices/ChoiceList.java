package com.alexlowe.choices;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Keyes on 5/25/2016.
 */
public class ChoiceList implements Serializable{
    private String mName;
    private ArrayList<Choice> mChoices;
    private Date mDate;


    public ChoiceList(String name, ArrayList<Choice> choices) {
        this.mName = name;
        mChoices = choices;
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

    public String getDate() {
        SimpleDateFormat s = new SimpleDateFormat("d MMM, yyyy", Locale.getDefault());
        return s.format(mDate);
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
