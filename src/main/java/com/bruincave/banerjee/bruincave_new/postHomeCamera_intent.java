package com.bruincave.banerjee.bruincave_new;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class postHomeCamera_intent extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    ImageView loadedImage;
    GalleryPhoto galleryPhoto;

    final int GALLERY_REQUEST = 13323;
    private String username;

    String selectedPhoto = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_home_camera_intent);
        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);
        username = prefs.getString("username", null);

        loadedImage = (ImageView) findViewById(R.id.loadedImage);
        final EditText editText = (EditText)findViewById(R.id.captionText);

        ImageButton postImage = (ImageButton) findViewById(R.id.btnPostHome);

        ImageButton backImage = (ImageButton) findViewById(R.id.backProfile);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null;
                try {
                    bitmap = ImageLoader.init().from(selectedPhoto).requestSize(512, 512).getBitmap();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //String encodedImage = ImageBase64.encode(bitmap);
                String encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);

                String caption = editText.getText().toString().trim();

                Log.d("encode", encodedImage);

                sendPhotoOnline(caption, encodedImage);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Explain to the user why we need to read the contacts
                    }
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 112934);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique


                return;
            } else{
                galleryPhoto = new GalleryPhoto(getApplicationContext());
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        }else {
            galleryPhoto = new GalleryPhoto(getApplicationContext());
            startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    String encodedImg = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG,100);
                    Bitmap bitmap2 = decodeBase64(encodedImg);
                    loadedImage.setImageBitmap(bitmap2);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while choosing photos", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void sendPhotoOnline(String caption, String bitmap){
        Response.Listener<String> photoListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RSP:", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");


                    if (success) {

                        Intent homeIntent = new Intent(postHomeCamera_intent.this, home_layout.class);
                        startActivity(homeIntent);

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(postHomeCamera_intent.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SendPhotoData sendPhotoData = new SendPhotoData(caption, bitmap, username, photoListener);
        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
        queue1.add(sendPhotoData);
    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
