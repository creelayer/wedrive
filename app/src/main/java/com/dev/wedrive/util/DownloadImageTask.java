package com.dev.wedrive.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

import lombok.Setter;
import lombok.experimental.Accessors;

public  class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private String url;

    private DownloadComplete listener;

    @Setter
    @Accessors(chain = true)
    private boolean circular = false;


    public DownloadImageTask(String url) {
        this.url = url;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }


    public void execute(DownloadComplete listener){
        this.listener = listener;
        super.execute(url);
    }



    protected void onPostExecute(Bitmap bitmap) {
        listener.ready(bitmap);
    }


    public interface DownloadComplete{
        public void ready(Bitmap bitmap);
    }


}
