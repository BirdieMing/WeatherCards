package com.example.ming.newcards;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardViewActivity extends AppCompatActivity {

    //TODO: Add place search text box
    //Debug: Load picture button
    //Manage picture screen?

    private static String LOG_TAG = "CardViewActivity";
    private static String List_TAG = "ListTag";
    private static String Entry_TAG = "EntryTag";
    private static String CardFragmentStackTag = "CardFragmentStackTag";
    CardEntryFragment fragment = new CardEntryFragment();
    CardListFragment listFragment = new CardListFragment();
    private int interval = 7;
    private String mode = Constants.Days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            CardListFragment listFragment = new CardListFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            listFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            //getSupportFragmentManager().beginTransaction()
            //      .add(R.id.fragment_container, listFragment).commit();
            AddListFragment();

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

//        AutoCompleteTextView textView = (AutoCompleteTextView)
//                findViewById(R.id.location_textbox);
//
//        textView.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // If the event is a key-down event on the "enter" button
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    Refresh();
//                }
//                return false;
//            }
//        });

        AutoCompleteTextView location_txt = (AutoCompleteTextView) findViewById(R.id.location_textbox);
        location_txt.setOnEditorActionListener(new TextView.OnEditorActionListener()
                                               {
                                                   @Override
                                                   public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                       String location = v.getText().toString();
                                                       Refresh();
                                                       return true;
                                                   }
                                               }
        );

//        textView.setAdapter(adapter);
//        textView.clearFocus();
        //Clear focus
    }

    private void AddListFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, listFragment, List_TAG);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, 1, 0, "Refresh");
        SubMenu sMenu0 = menu.addSubMenu(0, 2, 0, "Mode");
        sMenu0.add(0, 3, 0, "Hourly");
        sMenu0.add(0, 4, 0, "Day");

        SubMenu sMenu1 = menu.addSubMenu(0, 8, 0, "Customize picture for weather");
        sMenu1.add(0, 9, 0, "clear sky");

        getMenuInflater().inflate(R.menu.menu_card_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId())
        {
            case 1:
            case 3:
                this.mode = Constants.Hours;
                Refresh();
                return true;
            case 4:
                this.mode = Constants.Days;
                Refresh();
                return true;
            case 9: {
                try {
                    this.dispatchTakePictureIntent(item.getTitle().toString());
                } catch (Exception e)
                {
                    Log.d("exception", e.getMessage());
                }

            }
            default:
                Log.d("Menu Selection", "missing case " + item.getItemId());

        }

        return super.onOptionsItemSelected(item);
    }

    public void Refresh() {

        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.location_textbox);

        String location = (String) textView.getText().toString();

        if (location == "")
            return;

        listFragment.Refresh(location, mode);
    }

    private static final String[] COUNTRIES = new String[] {
            "Malden,us"
    };

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String imageFileKey;
    private String imageFilePath;
    //Dispatch intent from child class?

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TakePicture.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if (this.imageFileKey != "" && this.imageFilePath != "")
                new TakePicture(this, this.getApplicationContext()).SavePictureLoc(this.imageFileKey, this.imageFilePath);
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
        }
    }

    private File createImageFile(String fileName) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = fileName + "_JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent(String picName) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(picName);
            } catch (Exception ex) {
                // Error occurred while creating the File
                Log.d("picture", ex.getMessage());
            }

            if (photoFile != null) {
                this.imageFileKey = picName;
                this.imageFilePath = photoFile.getAbsolutePath();
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                this.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}
