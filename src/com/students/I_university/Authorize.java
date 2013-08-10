package com.students.I_university;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Иконников
 * Date: 09.08.13
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class Authorize extends AsyncTask <Void, Void, Void> {

    private String protocol;
    private String server;
    private String token;
    private String user;
    private String password;

    public boolean authorized;

    //Для подключения к тестовому серверу указываем пустую строку в первом параметре
    public Authorize(String server, String user, String password){
        this.protocol = "http://";
        this.token = "";
        this.authorized = false;

        if (!server.isEmpty())
            this.server = server;
        else this.server = "university.shiva.vps-private.net";

        if (!user.isEmpty())
            this.user = user;
        else this.user = "testuser";

        if (!password.isEmpty())
            this.password = password;
        else this.password = "Testuser1.";

    }

    @Override
    //метод, асинхронно выполняющий запрос к серверу
    protected Void doInBackground(Void... voids){
        String result = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            String loginPath = "/login/token.php?";
            HttpPost httpPost = new HttpPost( this.protocol + this.server + loginPath);

            List<NameValuePair> list = new ArrayList<NameValuePair>();

            list.add(new BasicNameValuePair("username", this.user));
            list.add(new BasicNameValuePair("password", this.password));
            list.add(new BasicNameValuePair("service", "moodle_mobile_app"));

            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null){
                InputStream in = httpResponse.getEntity().getContent();
                result = convertStreamToString(in);
                JSONObject jsonObject = new JSONObject(result);
                this.token = jsonObject.getString("token");
                if(this.token.length() > 0)
                    this.authorized = true;
            }
        }
        catch (Exception ex){
            result = "Exception";
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        //this.notifyAll();
    }

    /*      ---      ---      ---      ---      ---      ---      ---      ---      ---      ---      ---      ---*/
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

    public String getToken(){
        if ( !this.token.equals("") )
            return this.token;
        else return null;
    }

    public void setProtocol(String protocol){
        this.protocol = protocol;
    }







}
