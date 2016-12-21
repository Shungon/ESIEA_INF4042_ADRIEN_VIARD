package org.esiea.adrien_viard.esiea_inf4042_adrien_viard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class SettingsActivity extends Activity {
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        change(checkBox);
    }

    public void change(View view) {
        handleCheckBoxClick(view, Color.GREEN);
    }

    public void handleCheckBoxClick(View view, int color) {
        CheckBox tmpChkBox = (CheckBox) findViewById(view.getId());
        if(tmpChkBox.isChecked())
        {
            setActivityBackgroundColor(color);
        }
        else
        {
            setActivityBackgroundColor(Color.WHITE);
        }
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }


    void launchStartActivity() {
        Intent intent = new Intent(SettingsActivity.this, StartActivity.class);
        startActivity(intent);
    }

    void launchAllImagesActivity() {
        Intent intent = new Intent(SettingsActivity.this, AllImagesActivity.class);
        startActivity(intent);
    }

    //ActionBar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Add input to action_bar_menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.m_start_activity:
                Toast.makeText(this, "@string/m_start_activity", Toast.LENGTH_SHORT).show();
                launchStartActivity();
                break;
            case R.id.m_all_images:
                Toast.makeText(this, "@string/m_all_images", Toast.LENGTH_SHORT).show();
                launchAllImagesActivity();
                break;
            case R.id.m_settings:
                Toast.makeText(this, "@string/m_settings", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        return true;
    }
}
