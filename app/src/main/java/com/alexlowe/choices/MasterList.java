package com.alexlowe.choices;

import java.util.ArrayList;

/**
 * Created by Keyes on 5/29/2016.
 */
public class MasterList {
    public static final MasterList gMasterList = new MasterList();
    private ArrayList<ChoiceList> masterList;

    public MasterList() {
        this.masterList = new ArrayList<>();
    }

    public ArrayList<ChoiceList> getMasterList() {
        return masterList;
    }

    public void setMasterList(ArrayList<ChoiceList> masterList) {
        this.masterList = masterList;
    }
}
