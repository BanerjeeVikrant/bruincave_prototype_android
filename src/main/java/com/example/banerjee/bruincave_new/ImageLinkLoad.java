package com.example.banerjee.bruincave_new;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 1/3/2017.
 */
/*
public class ImageLinkLoad extends AsyncTask<Void, Void, Bitmap> {
    private String url;
    private ImageView imageView;

    public ImageLinkLoad(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }
}*/

public class ImageLinkLoad extends AsyncTask<Void, Void, Bitmap> {
    private String url;
    private ImageView imageView;
    private static Map<String,Bitmap> cachedBitmaps;

    public ImageLinkLoad(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
        if  (cachedBitmaps == null) {
            cachedBitmaps = new HashMap<String, Bitmap>();
        }
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            Bitmap myBitmap = cachedBitmaps.get(url);
            if (myBitmap == null && ! url.equals("http://www.bruincave.com/m/")) {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
                cachedBitmaps.put(url,myBitmap);
                Log.d("YYY", "image loaded from url:"+url);
            } else {
                Log.d("YYY", "image reused from Cache:"+url);
            }
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }
}