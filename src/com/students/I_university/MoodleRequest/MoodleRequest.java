package com.students.I_university.MoodleRequest;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

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

/**
 * Created by Akella on 14.08.13.
 */
public class MoodleRequest extends AsyncTask<Void, Void, Void> {

    private String server = "http://university.vps-private.net";
    private String pathToScript = "/webservice/rest/server.php";
    private List<NameValuePair> params;
    private String response;
    private boolean success = false;


    public MoodleRequest() {};

    //Для подключения к тестовому серверу указываем пустую строку в первом параметре
    public MoodleRequest(String server, String pathToScript){

        if (!server.isEmpty())
            this.server = server;

        if (!pathToScript.isEmpty())
            this.pathToScript = pathToScript;
    }

    public MoodleRequest(String pathToScript){

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
            }
        }
        catch (Exception ex){
            success = false;
            response = ex.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        success = true;
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
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
