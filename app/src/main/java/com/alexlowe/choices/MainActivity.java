package com.alexlowe.choices;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import java.util.ArrayList;
import java.util.Random;
/*1. set up search view - code in main activity adds input to listview
* 2. setup listview, viewholder pattern with text and delete button
* 3. setup decider button - chooses one item from the list
* 4. set up nav drawer- arrow, drawerfragment*/

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "rimjob";
    private EditText inputET;

    private ArrayList<Choice> choices = new ArrayList<>();
    ChoiceAdapter adapter;
    private CircularProgressButton btnDecide;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        btnDecide = (CircularProgressButton) findViewById(R.id.btnDecide);


        ListView lvChoices = (ListView) findViewById(android.R.id.list);
        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        lvChoices.setEmptyView(emptyText);

        adapter = new ChoiceAdapter(this, choices);
        lvChoices.setAdapter(adapter);
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
        choices.add(choice);
        adapter.notifyDataSetChanged();
    }

    /*public void decide(View view) {
        int pool = choices.size();
        Log.i(TAG, "pool: " + pool);

        Random r = new Random();
        int pick = r.nextInt(pool);
        Log.i(TAG, "pick: " + pick);

        String choice = choices.get(pick);
        Log.i(TAG, "choice: " + choice);

        for(int i = 0; i < pool; i++){
            if(!choices.get(i).equals(choice)){
                choices.remove(i);
            }
        }

        adapter.notifyDataSetChanged();
    }*/

    public void decide(View view) {
        btnDecide.setProgress(100);

        int pool = choices.size();
        Log.i(TAG, "pool: " + pool);

        Random r = new Random();
        int pick = r.nextInt(pool);
        Log.i(TAG, "pick: " + pick);

        for (int i = 0; i < pool; i++){
            if(i == pick){

            }
        }
    }
}
