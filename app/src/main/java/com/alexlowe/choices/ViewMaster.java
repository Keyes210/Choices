package com.alexlowe.choices;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Keyes on 5/28/2016.
 */
public class ViewMaster {
    Toast mToast = null;
    private Context mContext;
    private Activity mActivity;

    public ViewMaster(Context context, Activity activity) {
        this.mContext = context;
        this.mActivity = activity;
    }


    public void setupToolbar(Toolbar toolbar, final DrawerLayout drawerLayout, final NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        addToggleSupport(toolbar, mActivity, drawerLayout);

    }

    private void addToggleSupport(Toolbar toolbar, final Activity activity, final DrawerLayout drawerLayout) {
        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close) {
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
        switch (menuItem.getItemId()) {
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

    public void setUpSearchView(final MenuItem addItem) {
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(addItem);
        searchView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        searchView.setImeOptions(EditorInfo.IME_ACTION_GO);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String choice) {
                ((MainActivity) mContext).setButtonDecide();
                // send choice to listview
                ((MainActivity) mContext).addChoice(choice);

                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                addItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 24) {
                    displayToast("Choices must be under 25 characters");
                    searchView.setQuery(newText.substring(0, 24), false);
                }
                return false;
            }
        });
    }

    public void toolbarClear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Clear List");
        builder.setMessage("Are you sure you want to clear this list?");
        builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) mContext).clearData();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void saveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Save this list of choices");

        final EditText input = new EditText(mContext);
        input.setPadding(36, 40, 12, 8);
        input.setBackgroundColor(mContext.getResources().getColor(R.color.trans));
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        input.setHint("Enter a name for your list");

        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString().trim();
                if (name.length() < 1) {
                    name = "List Name";
                }
                ((MainActivity) mContext).saveChoices(name);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        validateInput(dialog, input);
    }

    private void validateInput(AlertDialog dialog, final EditText input) {
        final Button button = dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
        button.setEnabled(false);
        input.addTextChangedListener(new TextWatcher() {
                                         @Override
                                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                         }

                                         @Override
                                         public void onTextChanged(CharSequence s, int start, int before, int count) {
                                             // my validation condition
                                             if (input.getText().length() > 0) {
                                                 button.setEnabled(true);
                                                 if (input.getText().length() > 30) {
                                                     displayToast("Names must be less than 25 characters");
                                                     button.setTextColor(Color.RED);
                                                     button.setEnabled(false);
                                                 } else {
                                                     button.setEnabled(true);
                                                 }
                                             } else {
                                                 button.setEnabled(false);
                                             }
                                         }

                                         @Override
                                         public void afterTextChanged(Editable s) {
                                         }
                                     }
        );
    }

    public void displayToast(String message) {
        if (mToast != null) {
            mToast.cancel();
        } else {
            mToast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
            mToast.show();
        }
    }


}
