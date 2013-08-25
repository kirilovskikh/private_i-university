package com.students.I_university.MoodleRequest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Sveta
 * Date: 8/25/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Image  extends AsyncTask<String, Void, Void> {

    private String errorMessage;
    public Bitmap userImage;
    private boolean success;

    public boolean isSuccess()
    {;
        return success;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }


    @Override
    protected Void doInBackground(String... strings) {
        if (strings[0] != null)
            try
            {
                InputStream in = new java.net.URL(strings[0]).openStream();
                userImage = BitmapFactory.decodeStream(in);
                in.close();
            }
            catch (Exception e)
            {
                errorMessage = e.getMessage();
                e.printStackTrace();
            }
        return null;
    }
}
