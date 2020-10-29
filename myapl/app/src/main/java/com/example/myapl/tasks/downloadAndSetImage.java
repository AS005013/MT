package com.example.myapl.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class downloadAndSetImage implements Runnable {

    private ImageView bmImage;
    private String url;

    public downloadAndSetImage(ImageView bmImage, String url) {
        this.bmImage = bmImage;
        this.url = url;
    }
    private Bitmap trySetBitmap() {
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    public void run() {
        final Bitmap result = trySetBitmap();
        bmImage.post(new Runnable() {
            @Override
            public void run() {
                bmImage.setImageBitmap(result);
            }
        });
    }
}
