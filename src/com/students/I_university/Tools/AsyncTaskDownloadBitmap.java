package com.students.I_university.Tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

import com.students.I_university.Messages.MoodleCallback;

import java.io.InputStream;

/**
 * Created by Akella on 30.08.13.
 */
public class AsyncTaskDownloadBitmap extends AsyncTask<String, Void, Bitmap> {

    private LruCache<String, Bitmap> bitmap;
    private String imageURL;
    public MoodleCallback callback;

    public AsyncTaskDownloadBitmap (LruCache<String, Bitmap> imageView) {
        this.bitmap = imageView;
    }

    public void setCallBack(MoodleCallback callback)
    {
        this.callback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap mIcon11 = null;

        if (strings[0] == null)
            return null;

        try {
            imageURL = strings[0];
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

        if ((this.bitmap == null) || (bitmap == null))
            return;

        this.bitmap.put(imageURL, bitmap);
        callback.callBackRun();
    }
}

