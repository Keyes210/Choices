package com.alexlowe.choices;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private ArrayList<String> choices = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private CircularProgressButton btnDecide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_test);

        btnDecide = (CircularProgressButton) findViewById(R.id.btnDecide);


        ListView lvChoices = (ListView) findViewById(R.id.lvChoices);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, choices);

        lvChoices.setAdapter(adapter);
    }
/*
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addChoice(String choice){
        choices.add(choice);
        adapter.notifyDataSetChanged();
    }

    public void decide(View view) {
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
    }
}
