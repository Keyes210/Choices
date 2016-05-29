package com.alexlowe.choices;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {
    private EditText etMin, etMax;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        ViewMaster viewMaster = new ViewMaster(this, this);
        viewMaster.setupToolbar(toolbar, drawerLayout, navigationView);

        etMin = (EditText) findViewById(R.id.min_et);
        etMax = (EditText) findViewById(R.id.max_et);
        tvResult = (TextView) findViewById(R.id.result_tv);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    public void pressGenerate(View view) {
        int min = Integer.valueOf(etMin.getText().toString());
        int max = Integer.valueOf(etMax.getText().toString());

        if(min >= max) {
            max = min + 1;
            etMax.setText(String.valueOf(max));
        }

        Random r = new Random();
        int result = r.nextInt(max - min + 1) + min;

        tvResult.setText(String.valueOf(result));
    }
}
