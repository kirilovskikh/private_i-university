package com.students.I_university.Contacts;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.students.I_university.Tools.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 09.08.13
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class AsyncTaskGetContacts extends AsyncTask <Void, Void, HashMap<Integer, ContactInfo>> {

    private Context mContext;
    private ProgressDialog progressDialog;
    private Boolean errorFlag; // true - error, false - Ok.
    private ArrayList<String> listId = new ArrayList<String>();
    private HashMap<Integer, ContactInfo> map = new HashMap<Integer, ContactInfo>();


    public CallReturnDownload returnDownload;

    public AsyncTaskGetContacts (Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Загрузка ...");
        progressDialog.show();
    }

    @Override
    protected HashMap<Integer, ContactInfo> doInBackground(Void... voids) {

        if (!Utils.isOnline(mContext))
            return null;

        String token = Utils.getToken(mContext);
        String url = Utils.getUrlFunction();
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url); // url

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("wstoken", token));
            list.add(new BasicNameValuePair("wsfunction", "core_message_get_contacts"));
            list.add(new BasicNameValuePair("moodlewsrestformat", "json"));

            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null) {
                InputStream in = httpResponse.getEntity().getContent();
                String str = Utils.convertStreamToString(in);
                errorFlag = parseJson(new JSONObject(str));
                return map;
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onPostExecute(HashMap<Integer, ContactInfo> mMap) {
        super.onPostExecute(mMap);    //To change body of overridden methods use File | Settings | File Templates.
        progressDialog.dismiss();

        returnDownload.returnResult(mMap);
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private boolean parseJson (JSONObject jsonObject) {
        try {
            errorFlag = parseTypeObject(jsonObject.getJSONArray("online"));
            if (errorFlag)
                return errorFlag;

            errorFlag = parseTypeObject(jsonObject.getJSONArray("offline"));
            if (!errorFlag)
                map = DownloadContactHelper.DownloadContactsInfo(mContext, listId);

            return errorFlag;

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return true;
        }
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private Boolean parseTypeObject (JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject contactObject = jsonArray.getJSONObject(i);
                listId.add(contactObject.getString("id"));
            }

            return false;

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return true;
        }
    }

}
