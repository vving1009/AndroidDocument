package com.android.app.sqlitetest;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    protected final static int MENU_ADD = Menu.FIRST;
    protected final static int MENU_DELETE = Menu.FIRST + 1;
    protected final static int MENU_UPDATE = Menu.FIRST + 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_ADD, 0, "ADD");
        menu.add(Menu.NONE, MENU_DELETE, 0, "DELETE");
        menu.add(Menu.NONE, MENU_UPDATE, 0, "UPDATE");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case MENU_ADD:
                break;
            case MENU_DELETE:
                break;
            case MENU_UPDATE:
                break;
        }
        return true;
    }


}
