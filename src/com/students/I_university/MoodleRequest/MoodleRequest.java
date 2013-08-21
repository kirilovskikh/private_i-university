package com.students.I_university.MoodleRequest;

import android.app.Application;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by Akella on 14.08.13.
 */
public class MoodleRequest extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private String server = "http://university.shiva.vps-private.net";
    private String pathToScript = "/webservice/rest/server.php";
    private ArrayList<NameValuePair> params;
    private String response;
    private ProgressDialog progressDialog;
    private MoodleCallback callback;
    protected String errorMessage;
    protected boolean success = false;

    public MoodleRequest(Context context) {
        this.mContext = context;
        this.params = new ArrayList<NameValuePair>();
        this.params.add(new BasicNameValuePair("moodlewsrestformat", "json"));
    };

    public MoodleRequest(Context context, String server, String pathToScript){
        this.mContext = context;
        if (!server.isEmpty())
            this.server = server;

        if (!pathToScript.isEmpty())
            this.pathToScript = pathToScript;
    }

    public MoodleRequest(Context context, String pathToScript){
        this.mContext = context;
        if (!pathToScript.isEmpty())
            this.pathToScript = pathToScript;
    }

    public void addParam(String name, String value){
        params.add(new BasicNameValuePair(name, value));
    }

    public String getResponse()
    {
        return response;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setMoodleCallback(MoodleCallback callBackFunc)
    {
        this.callback = callBackFunc;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.

        progressDialog = new ProgressDialog(this.mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Загрузка ...");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost( server + pathToScript);

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null){
                InputStream in = httpResponse.getEntity().getContent();
                response = convertStreamToString(in);
                if(response.isEmpty())
                        throw new Exception("Cannot create the response string!");
            }
        }
        catch (Exception ex){
            success = false;
            errorMessage = ex.getMessage();
            return null;
        }
        success = true;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);    //To change body of overridden methods use File | Settings | File Templates.
        progressDialog.dismiss();
        if(callback != null) callback.callBackRun();
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
            errorMessage = e.getMessage();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                success = false;
                errorMessage = e.getMessage();
                return null;
            }
        }
        return sb.toString();
    }
}
