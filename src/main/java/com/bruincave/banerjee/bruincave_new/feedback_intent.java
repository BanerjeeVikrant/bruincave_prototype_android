package com.bruincave.banerjee.bruincave_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class feedback_intent extends AppCompatActivity {
    GalleryPhoto galleryPhoto;

    final int GALLERY_REQUEST = 13323;
    private String username;
    private ImageView loadedImage;
    private String selectedPhoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_intent);

        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);
        username = prefs.getString("username", null);

        loadedImage = (ImageView) findViewById(R.id.loadedImage);
        final EditText feedbackText = (EditText)findViewById(R.id.feedbackText);

        galleryPhoto = new GalleryPhoto(getApplicationContext());

        Button postImage = (Button) findViewById(R.id.btnPostHome);
        ImageButton backImage = (ImageButton) findViewById(R.id.backProfile);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton galleryBtn = (FloatingActionButton) findViewById(R.id.fabGallery);
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null;
                String encodedImage = "";
                try {
                    bitmap = ImageLoader.init().from(selectedPhoto).requestSize(512, 512).getBitmap();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //String encodedImage = ImageBase64.encode(bitmap);
                if(bitmap != null) {
                    encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
                    Log.d("encode", encodedImage);
                }
                String caption = feedbackText.getText().toString().trim();


                Response.Listener<String> photoListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RSP:", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("success");


                            if (success.equals("true")) {

                                finish();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(feedback_intent.this);
                                builder.setMessage(success)
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SendFeedbackData sendFeedbackData = new SendFeedbackData(caption, encodedImage, username, photoListener);
                RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
                queue1.add(sendFeedbackData);
            }
        });
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
