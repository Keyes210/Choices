package com.alexlowe.choices;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.transitionseverywhere.Transition;
import com.transitionseverywhere.extra.Scale;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "rimjob";
    private EditText inputET;

    private ArrayList<Choice> choices = new ArrayList<>();
    private ArrayList<Choice> tempList;
    String selection = "";
    ChoiceAdapter adapter;
    private Button btnDecide;
    private DrawerLayout mDrawerLayout;
    RecyclerView rvChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        btnDecide = (Button) findViewById(R.id.btnDecide);



        rvChoices = (RecyclerView) findViewById(android.R.id.list);
        //TextView emptyText = (TextView)findViewById(android.R.id.empty);

        adapter = new ChoiceAdapter(choices);
        rvChoices.setAdapter(adapter);
        rvChoices.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
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
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addChoice(String choiceText){
        Choice choice = new Choice(choiceText);
        choices.add(0, choice);
        adapter.notifyItemInserted(0);
    }

    public void pressDecide(View view) {
        if(btnDecide.getText().equals("decide")){
            decide();
        }else{
            tryAgain();
        }
    }

    private void tryAgain() {
        clearData();
        for (Choice choice : tempList){
            choices.add(0, choice);
            adapter.notifyItemInserted(0);
        }
        btnDecide.setText("decide");
    }


    public void decide() {
        cloneList(choices);

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
        int pool = choices.size();
        Log.i(TAG, "pool: " + pool);

        Random r = new Random();
        int pick = r.nextInt(pool);
        Log.i(TAG, "pick: " + pick);

        selection = choices.get(pick).getChoiceText();

    }

    public void clearData() {
        int size = choices.size();
        choices.clear();
        adapter.notifyItemRangeRemoved(0, size);
    }

    private void showChoice() {
        Choice choice = new Choice(selection);
        choices.add(choice);
    }


    private void showContents(ArrayList<Choice> list, String name){
        String results = "";

        for (Choice choice : list) {
            results += choice.getChoiceText() + "\n";
        }

        Log.d(TAG, "name :" + name);
        Log.d(TAG, results);
    }
}
