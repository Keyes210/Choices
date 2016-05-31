package com.alexlowe.choices;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class SavesActivity extends AppCompatActivity {
    private static final String TAG = "rimjob";
    public ChoiceListAdapter mChoiceListAdapter;
    private ArrayList<ChoiceList> mChoiceLists;
    private RecyclerView rvSaves;
    private ChoiceSaver mChoiceSaver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saves);
        mChoiceSaver = new ChoiceSaver(SavesActivity.this);
        mChoiceSaver.pullData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        ViewMaster viewMaster = new ViewMaster(this, this);
        viewMaster.setupToolbar(toolbar, drawerLayout, navigationView);

        mChoiceLists = new ArrayList<>(MasterList.gMasterList.getMasterList());
        mChoiceListAdapter = new ChoiceListAdapter(this, mChoiceLists);

        rvSaves = (RecyclerView) findViewById(R.id.rvSaves);
        rvSaves.setAdapter(mChoiceListAdapter);
        rvSaves.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_saves, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_clear:
                clearAllChoices();
                return true;
            case android.R.id.home:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearAllChoices() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clear Past Lists");
        builder.setMessage("Are you sure you want to clear all your choice lists?");
        builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearData();
                SavesActivity.this.recreate();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void clearData() {
        int size = MasterList.gMasterList.getMasterList().size();
        MasterList.gMasterList.getMasterList().clear();
        mChoiceListAdapter.notifyItemRangeRemoved(0, size);
        mChoiceSaver.clearPrefs();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mChoiceSaver.clearPrefs();
        mChoiceSaver.saveMasterList();
    }

}
