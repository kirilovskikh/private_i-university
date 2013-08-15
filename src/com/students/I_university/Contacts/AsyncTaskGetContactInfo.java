package com.students.I_university.Contacts;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.students.I_university.Helpers.ContactInfo;
import com.students.I_university.Helpers.DownloadContactHelper;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 10.08.13
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class AsyncTaskGetContactInfo extends AsyncTask <Integer, Void, HashMap<Integer, ContactInfo>> {
    public CallReturnDownload returnDownload = null;
    private Context mContext;
    private ProgressDialog progressDialog;
    private ImageView imageView;

    public AsyncTaskGetContactInfo (Context context, ImageView imageView) {
        this.mContext = context;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.
        showProgressDialog();
    }

    @Override
    protected HashMap<Integer, ContactInfo> doInBackground(Integer... integers) {
        return DownloadContactHelper.oneObject(integers[0]);
    }

    @Override
    protected void onPostExecute(HashMap<Integer, ContactInfo> map) {
        super.onPostExecute(map);    //To change body of overridden methods use File | Settings | File Templates.

        if (progressDialog != null)
            progressDialog.dismiss();

        if ((map != null) && (imageView != null)) {
            AsyncTaskDownloadImage downloadImage = new AsyncTaskDownloadImage(imageView);
            downloadImage.execute(map.get(0).getNormalImgUrl());
        }

        returnDownload.returnResult(map);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Загрузка...");
        progressDialog.show();
    }

}
