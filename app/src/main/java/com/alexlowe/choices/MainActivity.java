package com.alexlowe.choices;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_CL = "keychoicelist";
    String mSelection = "";
    RecyclerView rvChoices;
    ChoiceAdapter mChoiceAdapter;
    ChoiceSaver mChoiceSaver;
    ViewMaster mViewMaster = new ViewMaster(this, this);
    private Button btnDecide;
    private ArrayList<Choice> mChoices = new ArrayList<>();
    private ArrayList<Choice> tempList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        mViewMaster.setupToolbar(toolbar, drawerLayout, navigationView);

        btnDecide = (Button) findViewById(R.id.btnDecide);

        rvChoices = (RecyclerView) findViewById(android.R.id.list);

        mChoiceAdapter = new ChoiceAdapter(mChoices);

        mChoiceAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                if (mChoiceAdapter.getItemCount() == 0) setButtonDecide();
            }
        });
        rvChoices.setAdapter(mChoiceAdapter);
        rvChoices.setLayoutManager(new LinearLayoutManager(this));

        if(getIntent().getSerializableExtra(KEY_CL) != null){
            retrievePastChoiceList();
        }else if(Choice.gChoice != null){
            clearData();
            for (Choice choice : Choice.gChoice) {
                mChoices.add(0, choice);
                mChoiceAdapter.notifyItemInserted(0);
            }
        }
    }

    private void retrievePastChoiceList() {
        ChoiceList cl = (ChoiceList) getIntent().getSerializableExtra(KEY_CL);
        clearData();
        for (Choice choice : cl.getChoices()) {
            mChoices.add(0, choice);
            mChoiceAdapter.notifyItemInserted(0);
        }
        setButtonDecide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem addItem = menu.findItem(R.id.action_add);

        addItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        mViewMaster.setUpSearchView(addItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add:
                return true;
            case R.id.action_clear:
                mViewMaster.toolbarClear();
                return true;
            case R.id.action_save:
                mViewMaster.saveDialog();
                return true;
            case android.R.id.home:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addChoice(String choiceText) {
        Choice choice = new Choice(choiceText);
        mChoices.add(0, choice);
        mChoiceAdapter.notifyItemInserted(0);
    }

    public void pressDecide(View view) {
        if (btnDecide.getText().equals("decide")) {
            if (mChoices.size() > 1) {
                decide();
            } else {
                Toast.makeText(this, "You must choose more than one item to make a decision",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            tryAgain();
        }
    }

    private void tryAgain() {
        clearData();
        for (Choice choice : tempList) {
            mChoices.add(0, choice);
            mChoiceAdapter.notifyItemInserted(0);
        }
        setButtonDecide();
    }

    public void setButtonDecide() {
        btnDecide.setText("decide");
    }


    private void decide() {
        cloneList(mChoices);

        chooseRandomItem();
        clearData();

        btnDecide.setText("Try Again?");
        showChoice();
    }

    private void cloneList(ArrayList<Choice> originalList) {
        tempList = new ArrayList<>(originalList.size());
        for (Choice choice : originalList) {
            tempList.add(new Choice(choice));
        }
    }

    private ArrayList<Choice> copyListForSaving(ArrayList<Choice> originalList) {
        ArrayList<Choice> list = new ArrayList<>(originalList.size());
        for (Choice choice : originalList) {
            list.add(new Choice(choice));
        }
        return list;
    }

    private void chooseRandomItem() {
        int pool = mChoices.size();

        Random r = new Random();
        int pick = r.nextInt(pool);

        mSelection = mChoices.get(pick).getChoiceText();
    }

    public void clearData() {
        int size = mChoices.size();
        mChoices.clear();
        mChoiceAdapter.notifyItemRangeRemoved(0, size);
        setButtonDecide();
    }

    private void showChoice() {
        Choice choice = new Choice(mSelection);
        mChoices.add(choice);
    }

    public void saveChoices(String name) {
        if (mChoices.size() > 1) {
            ChoiceList choiceList = new ChoiceList(name, copyListForSaving(mChoices));
            MasterList.gMasterList.getMasterList().add(0, choiceList);
            mChoiceSaver = new ChoiceSaver(this);
            mChoiceSaver.clearPrefs();
            mChoiceSaver.saveMasterList();
            Toast.makeText(this, "Choice List Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You must choose more than one item to save", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Choice.gChoice = copyListForSaving(mChoices);
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
