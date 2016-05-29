package com.alexlowe.choices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Keyes on 5/28/2016.
 */
public class ViewMaster {
    private Context mContext;
    private Activity mActivity;

    public ViewMaster(Context context, Activity activity){
        this.mContext = context;
        this.mActivity = activity;
    }


    public void setupToolbar(Toolbar toolbar, final DrawerLayout drawerLayout, NavigationView navigationView){

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                selectDrawerItem(item);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        addToggleSupport(toolbar, mActivity, drawerLayout);


    }

    private void addToggleSupport(Toolbar toolbar, final Activity activity, DrawerLayout drawerLayout) {
        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                activity.invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                activity.invalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(mDrawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public void selectDrawerItem(MenuItem menuItem) {
        Intent intent;
        switch(menuItem.getItemId()) {
            case R.id.navigation_item_chooser:
                intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.navigation_item_randnum:
                intent = new Intent(mContext, RandomActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.navigation_item_saves:
                intent = new Intent(mContext, SavesActivity.class);
                mContext.startActivity(intent);
                break;
            default:
                break;

        }
    }

}
