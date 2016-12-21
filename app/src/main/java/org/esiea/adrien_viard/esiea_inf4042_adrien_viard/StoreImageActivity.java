package org.esiea.adrien_viard.esiea_inf4042_adrien_viard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Clems on 18/12/2016.
 */
public class StoreImageActivity extends Activity {

    private TextView tv_title, tv_type, tv_directors, tv_nationality, tv_actors, tv_characters, tv_producer;
    private ImageView iv_image;
    private Button btn_save_image;

    private DownloadManager myDownloadManager;
    private long myDownloadReference;
    private BroadcastReceiver receiverDownloadComplete;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_image);

        final String title = getIntent().getStringExtra("title");
        String type = getIntent().getStringExtra("type");
        String directors = getIntent().getStringExtra("directors");
        String nationality = getIntent().getStringExtra("nationality");
        String actors = getIntent().getStringExtra("actors");
        String characters = getIntent().getStringExtra("characters");
        String producer = getIntent().getStringExtra("producer");
        final String imageURL = getIntent().getStringExtra("imageURL");

        tv_title =(TextView) findViewById(R.id.list_item_title);
        tv_type =(TextView) findViewById(R.id.list_item_type);
        tv_directors =(TextView) findViewById(R.id.list_item_directors);
        tv_nationality =(TextView) findViewById(R.id.list_item_nationality);
        tv_actors =(TextView) findViewById(R.id.list_item_actors);
        tv_characters =(TextView) findViewById(R.id.list_item_characters);
        tv_producer =(TextView) findViewById(R.id.list_item_producer);
        iv_image = (ImageView) findViewById(R.id.list_item_image);

        Picasso.with(this).load(imageURL).into(iv_image);
        tv_title.setText(title);
        tv_type.setText(type);
        tv_directors.setText(directors);
        tv_nationality.setText(nationality);
        tv_actors.setText(actors);
        tv_characters.setText(characters);
        tv_producer.setText(producer);

        //Action button "save image"
        btn_save_image = (Button) findViewById(R.id.btnStoreImage);
        btn_save_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentFilter myFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                registerReceiver(receiverDownloadComplete, myFilter);

                receiverDownloadComplete = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                        if(reference == myDownloadReference) {
                            DownloadManager.Query myQuery = new DownloadManager.Query();
                            myQuery.setFilterById(reference);
                            Cursor myCursor = myDownloadManager.query(myQuery);

                            if (myCursor.moveToFirst()) {
                                checkStatus(myCursor);
                            }

                            Toast.makeText(StoreImageActivity.this, "Image stored in Downloads directory", Toast.LENGTH_LONG).show();
                            open();
                        }
                    }
                };

                myDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri myDownloadSource = Uri.parse(imageURL);
                final DownloadManager.Request myRequest = new DownloadManager.Request(myDownloadSource);

                myRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                myRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                myRequest.setDescription("@string/app_name");
                myRequest.setTitle(title + ".jpg");
                myRequest.setVisibleInDownloadsUi(true);
                myRequest.setDestinationInExternalFilesDir(StoreImageActivity.this, Environment.DIRECTORY_DOWNLOADS, title + ".jpg");

                myDownloadReference = myDownloadManager.enqueue(myRequest);
            }
        });
    }

    private void checkStatus(Cursor myCursor) {
        int columnIndex = myCursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = myCursor.getInt(columnIndex);

        int fileNameIndex = myCursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
        String savedFilePath = myCursor.getString(fileNameIndex);

        int columnReason = myCursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = myCursor.getInt(columnReason);

        String statusText = "";
        String reasonText = "";

        switch (status)
        {
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "STATUS_SUCCESSFUL";
                reasonText = "Filename:\n" + savedFilePath;
                Toast.makeText(StoreImageActivity.this, statusText, Toast.LENGTH_LONG).show();
                break;
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                switch(reason){
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                }
                Toast.makeText(StoreImageActivity.this, statusText + "\n" + reasonText, Toast.LENGTH_LONG).show();
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                switch(reason){
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                }
                Toast.makeText(StoreImageActivity.this, statusText + "\n" + reasonText, Toast.LENGTH_LONG).show();
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                Toast.makeText(StoreImageActivity.this, statusText, Toast.LENGTH_LONG).show();
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                Toast.makeText(StoreImageActivity.this, statusText, Toast.LENGTH_LONG).show();
                break;
        }
    }

    void launchStartActivity() {
        Intent intent = new Intent(StoreImageActivity.this, StartActivity.class);
        startActivity(intent);
    }

    void launchAllImagesActivity() {
        Intent intent = new Intent(StoreImageActivity.this, AllImagesActivity.class);
        startActivity(intent);
    }

    public void launchSettingsActivity() {
        Intent intent = new Intent(StoreImageActivity.this, SettingsActivity.class);
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
                launchSettingsActivity();
            default:
                break;
        }
        return true;
    }

    public void open(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("See image in a specific app");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(StoreImageActivity.this,"Loading ...",Toast.LENGTH_LONG).show();

                        Intent galleryIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("imageURL")));
                        startActivity(galleryIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertErrorDialog = alertDialogBuilder.create();
        alertErrorDialog.show();
    }
}
