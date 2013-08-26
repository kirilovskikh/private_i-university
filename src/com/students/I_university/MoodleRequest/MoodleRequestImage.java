package com.students.I_university.MoodleRequest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

/**
 * Created by Akella on 21.08.13.
 */
public class MoodleRequestImage extends AsyncTask<String, Void, Void> {
    private MoodleCallback callbackFunction;
    private boolean success;
    private String errorMessage;
    public Bitmap ownImage;
    public Bitmap userImage;

    public boolean isSuccess()
    {
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
                ownImage = BitmapFactory.decodeStream(in);
                in.close();
            }
            catch (Exception e)
            {
                errorMessage = e.getMessage();
                e.printStackTrace();
            }

        if (strings[1] != null)
            try
            {
                InputStream in = new java.net.URL(strings[1]).openStream();
                userImage = BitmapFactory.decodeStream(in);
                in.close();
            }
            catch (Exception e)
            {
                errorMessage = e.getMessage();
                e.printStackTrace();
            }
        if(ownImage == null && userImage == null)
        {
            success = false;
        }
        else success = true;
        return null;
    }

    public void setMoodleCallback(MoodleCallback callBackFunc)
    {
        this.callbackFunction = callBackFunc;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);    //To change body of overridden methods use File | Settings | File Templates.
        callbackFunction.callBackRun();
    }
}
