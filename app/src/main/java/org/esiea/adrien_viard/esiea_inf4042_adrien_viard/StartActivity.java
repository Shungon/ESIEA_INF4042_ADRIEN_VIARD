package org.esiea.adrien_viard.esiea_inf4042_adrien_viard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    Button btn_my_images;
    Button btn_all_images;
    Button btn_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //btn_my_images = (Button) findViewById(R.id.btnMyImages);
        btn_all_images = (Button) findViewById(R.id.btnAllImages);
        btn_settings = (Button) findViewById(R.id.btnSettings);


        btn_all_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAllImagesActivity();
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSettingsActivity();
            }
        });
    }

    void launchAllImagesActivity() {
        Intent intent = new Intent(StartActivity.this, AllImagesActivity.class);
        startActivity(intent);
    }

    public void launchSettingsActivity() {
        Intent intent = new Intent(StartActivity.this, SettingsActivity.class);
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
                break;
            case R.id.m_all_images:
                Toast.makeText(this, "@string/m_all_images", Toast.LENGTH_SHORT).show();
                launchAllImagesActivity();
                break;
            case R.id.m_settings:
                Toast.makeText(this, "@string/m_settings", Toast.LENGTH_SHORT).show();
                launchSettingsActivity();
            default:
                break;
        }
        return true;
    }
}
