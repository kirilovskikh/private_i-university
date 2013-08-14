package com.students.I_university.Contacts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 14.08.13
 * Time: 06:10
 * To change this template use File | Settings | File Templates.
 */
public class AsyncTaskDownloadImage extends AsyncTask <String, Void, Bitmap> {

    private ImageView imageView;

    public AsyncTaskDownloadImage (ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap mIcon11 = null;

        if (strings[0] == null)
            return null;

        try {
            InputStream in = new java.net.URL(strings[0]).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mIcon11;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);    //To change body of overridden methods use File | Settings | File Templates.

        if ((imageView == null) || (bitmap == null))
            return;

        imageView.setImageBitmap(bitmap);
    }
}
