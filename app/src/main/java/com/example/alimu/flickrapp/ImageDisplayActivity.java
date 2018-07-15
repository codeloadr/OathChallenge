package com.example.alimu.flickrapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.alimu.flickrapp.util.UtilityClass;

import static com.example.alimu.flickrapp.util.UtilityClass.clearAlertDialog;
import static com.example.alimu.flickrapp.util.UtilityClass.createAlertDialog;

public class ImageDisplayActivity extends AppCompatActivity {
    private String LOG_TAG = ImageDisplayActivity.class.getSimpleName();
    public static String EXTRA_FULL_IMAGE_URL = "EXTRA_FULL_IMAGE_URL";

    ImageView imageView;
    private String fullImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        fullImageUrl = getIntent().getExtras().getString(EXTRA_FULL_IMAGE_URL);

        Log.i(LOG_TAG,"ImageDisplayActivity fullImageUrl "+ fullImageUrl);

        imageView = findViewById(R.id.imgDisplay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume started");

        if(fullImageUrl != null && !fullImageUrl.isEmpty()) {
            getFullSizeImage(fullImageUrl);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Log.i(LOG_TAG,"onBackPressed started");

        clearResources();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"onDestroy started");

        clearResources();
    }

    private void clearResources(){
        clearAlertDialog();
    }

    private void getFullSizeImage(String currImageUrl){
        if(checkForConnection()) {
            loadImage(currImageUrl);
        }
    }

    public boolean checkForConnection(){
        if(UtilityClass.checkNetworkConnectivity(this)) {
            Log.i(LOG_TAG,"Internet Connection Available");
            return true;
        }
        else{
            Log.i(LOG_TAG,"ImageDisplayActivity offline");
            createAlertDialog(this);
        }

        return false;
    }

    private void loadImage(String currImageUrl){
        Glide.with(ImageDisplayActivity.this).load(currImageUrl).into(imageView);
    }
}
