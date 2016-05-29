package com.alexlowe.choices;

import android.content.DialogInterface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "rimjob";

    private Button btnDecide;
    private RecyclerView rvChoices;

    private ArrayList<Choice> mChoices = new ArrayList<>();
    private ArrayList<Choice> tempList;
    String mSelection = "";
    ChoiceAdapter mChoiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        ViewMaster viewMaster = new ViewMaster(this, this);
        viewMaster.setupToolbar(toolbar, drawerLayout, navigationView);

        btnDecide = (Button) findViewById(R.id.btnDecide);

        rvChoices = (RecyclerView) findViewById(android.R.id.list);

        mChoiceAdapter = new ChoiceAdapter(mChoices);
        rvChoices.setAdapter(mChoiceAdapter);
        rvChoices.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem addItem = menu.findItem(R.id.action_add);

        addItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(addItem);
        searchView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        searchView.setImeOptions(EditorInfo.IME_ACTION_GO);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String choice) {
                btnDecide.setText("decide");
                // send choice to listview
                addChoice(choice);

                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                addItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_add:
                return true;
            case R.id.action_clear:
                toolbarClear();
                return true;
            case android.R.id.home:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toolbarClear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clear List");
        builder.setMessage("Are you sure you want to clear this list?");
        builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearData();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void addChoice(String choiceText){
        Choice choice = new Choice(choiceText);
        mChoices.add(0, choice);
        mChoiceAdapter.notifyItemInserted(0);
    }

    public void pressDecide(View view) {
        if(btnDecide.getText().equals("decide")){
            if(mChoices.size() > 1){
                decide();
            }else {
                Toast.makeText(this, "You must choose more than one item to make a decision",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            tryAgain();
        }
    }

    private void tryAgain() {
        clearData();
        for (Choice choice : tempList){
            mChoices.add(0, choice);
            mChoiceAdapter.notifyItemInserted(0);
        }
        btnDecide.setText("decide");
    }


    public void decide() {
        cloneList(mChoices);

        chooseRandomItem();
        clearData();

        btnDecide.setText("Try Again?");
        showChoice();
    }

    private void cloneList(ArrayList<Choice> originalList){
        tempList = new ArrayList<>(originalList.size());
        for(Choice choice : originalList){
            tempList.add(new Choice(choice));
        }
    }

    private void chooseRandomItem(){
        int pool = mChoices.size();
        Log.i(TAG, "pool: " + pool);

        Random r = new Random();
        int pick = r.nextInt(pool);
        Log.i(TAG, "pick: " + pick);

        mSelection = mChoices.get(pick).getChoiceText();

    }

    public void clearData() {
        int size = mChoices.size();
        mChoices.clear();
        mChoiceAdapter.notifyItemRangeRemoved(0, size);
        btnDecide.setText("decide");
    }

    private void showChoice() {
        Choice choice = new Choice(mSelection);
        mChoices.add(choice);
    }


    private void showContents(ArrayList<Choice> list, String name){
        String results = "";

        for (Choice choice : list) {
            results += choice.getChoiceText() + "\n";
        }

        Log.d(TAG, "name :" + name);
        Log.d(TAG, results);
    }

    public void saveChoices(){
        if(mChoices.size() > 1){
            ChoiceList choiceList = new ChoiceList(mChoices);
            ChoiceList.masterList.add(choiceList);
        }else {
            Toast.makeText(this, "You must choose more than one item to save", Toast.LENGTH_SHORT).show();
        }
    }
}
