package org.esiea.adrien_viard.esiea_inf4042_adrien_viard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllImagesActivity extends AppCompatActivity {

    private ArrayList<ImageItems> myGrid;
    private GridView myGridView;
    private GridViewAdapter myGridViewAdapter;

    private String jsonFile = "http://reopaes.fr/imageDataBase.json";

    ProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_images);

        myGridView = (GridView) findViewById(R.id.gridView_all_images);

        //Initialisation
        myGrid = new ArrayList<>();
        myGridViewAdapter = new GridViewAdapter(this, R.layout.activity_all_images, myGrid);


        //Loading all images from JSON File
        new Loading().execute();

        //Action GridView Image
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageItems item = (ImageItems) myGridViewAdapter.getItem(i);

                Intent intent = new Intent(AllImagesActivity.this, StoreImageActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("type", item.getType());
                intent.putExtra("directors", item.getDirectors());
                intent.putExtra("nationality", item.getNationality());
                intent.putExtra("actors", item.getActors());
                intent.putExtra("characters", item.getCharacters());
                intent.putExtra("producer", item.getProducer());
                intent.putExtra("imageURL", item.getImageURL());

                //Start details activity
                startActivity(intent);
            }
        });
    }

    private class Loading extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //ProgressDialog creation
            myProgressDialog = new ProgressDialog(AllImagesActivity.this);

            //ProgressDialog title
            myProgressDialog.setTitle("@string/loadingDialogTitle");

            //ProgressDialog message
            myProgressDialog.setMessage("@string/loadingDialogMessage");
            myProgressDialog.setIndeterminate(false);

            //ProgressDialog display
            myProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void...voids) {
            HttpHandler myHandler = new HttpHandler();
            String response = myHandler.makeServiceCall(jsonFile);

            try {
                JSONArray myArray = new JSONArray(response);

                for (int i = 0; i < myArray.length(); i++) {
                    ImageItems image = new ImageItems();

                    JSONObject myObject = myArray.getJSONObject(i);

                    image.title = myObject.getString("Name");

                    JSONArray characters = myObject.getJSONArray("Character(s)");
                    image.characters += characters.getJSONObject(0).getString("p_name");
                    for (int j = 1; j < characters.length(); j++)
                    {
                        image.characters += " , ";
                        image.characters += characters.getJSONObject(j).getString("p_name");

                    }
                    //image.characters = "dodoSansPastèque";

                    image.type = myObject.getString("Type");

                    JSONArray directors = myObject.getJSONArray("Director(s)");
                    image.directors += directors.getJSONObject(0).getString("d_name");
                    for (int j = 1; j < directors.length(); j++) {
                        image.directors += " , ";
                        image.directors += directors.getJSONObject(j).getString("d_name");

                    }

                    JSONArray actors = myObject.getJSONArray("Actor(s)");
                    image.actors += actors.getJSONObject(0).getString("a_name");
                    for (int j = 1; j < actors.length(); j++) {
                        image.actors += " , ";
                        image.actors += actors.getJSONObject(j).getString("a_name");

                    }

                    image.producer = myObject.getString("Producer");

                    image.nationality = myObject.getString("Nationality");

                    image.imageURL = myObject.getString("url");

                    image.id = myObject.getInt("id");

                    myGrid.add(image);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (myGrid.isEmpty()) {
                myProgressDialog.dismiss();
                open();

            } else {
                myGridViewAdapter.setMyGridImages(myGrid);
                myGridView.setAdapter(myGridViewAdapter);

                myProgressDialog.dismiss();
                Toast.makeText(AllImagesActivity.this, "Loading completed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void open(){
        AlertDialog.Builder alertErrorDialogBuilder = new AlertDialog.Builder(this);
        alertErrorDialogBuilder.setMessage("Oups, an error occurred during loading");
                alertErrorDialogBuilder.setPositiveButton("Back",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(AllImagesActivity.this,"StartActivity",Toast.LENGTH_LONG).show();

                                Intent startIntent = new Intent(AllImagesActivity.this, StartActivity.class);
                                startActivity(startIntent);
                            }
                        });

        AlertDialog alertErrorDialog = alertErrorDialogBuilder.create();
        alertErrorDialog.show();
    }

    void launchStartActivity() {
        Intent intent = new Intent(AllImagesActivity.this, StartActivity.class);
        startActivity(intent);
    }

    public void launchSettingsActivity() {
        Intent intent = new Intent(AllImagesActivity.this, SettingsActivity.class);
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
