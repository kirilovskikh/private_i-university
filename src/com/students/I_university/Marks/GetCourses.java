package com.students.I_university.Marks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.students.I_university.Helpers.DownloadMarksHelper;
import com.students.I_university.Helpers.MarkDetails;
import com.students.I_university.Utils;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kotvaska
 * Date: 11.08.13
 * Time: 3:40
 * To change this template use File | Settings | File Templates.
 */
public class GetCourses extends AsyncTask <Void, Void, Void> {

    String token="2f6a54d4d69b8b16f97582dc1b519135";
    String url = "http://university.shiva.vps-private.net/webservice/rest/server.php?";
    String str;
    Boolean modules;
    ProgressDialog progressDialog;
    String error;
    public ArrayList<String> courses = new ArrayList<String>();
    private HashMap<Integer, MarkDetails> map = new HashMap<Integer, MarkDetails>();

    public CallBackMarks returnMarks;

    Context mContext;

    public GetCourses (Context mContext, Boolean modules) {
        this.mContext = mContext;
        this.modules = modules;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Загрузка ...");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> list = new ArrayList<NameValuePair>();

            list.add(new BasicNameValuePair("wstoken", token));


            list.add(new BasicNameValuePair("wsfunction", "moodle_enrol_get_users_courses"));
            list.add(new BasicNameValuePair("userid", "9"));

            list.add(new BasicNameValuePair("moodlewsrestformat", "json"));

            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null) {
                InputStream in = httpResponse.getEntity().getContent(); // Get the
                str = Utils.convertStreamToString(in);
                JSONArray jsonArray = new JSONArray(str);
                if (getDataFromJSON(jsonArray.getJSONObject(0)))
                    map = DownloadMarksHelper.DownloadHelperAssign(courses, modules);
            }

        }
        catch (Exception e) {
            error = "Не удалось получить данные. Проверьте соединение...";
            Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);    //To change body of overridden methods use File | Settings | File Templates.
        if (str == null)
            str = "null";
        progressDialog.dismiss();

        returnMarks.returnMarks(map);
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    private Boolean getDataFromJSON(JSONObject jsonObject ){
        try{
            courses.add(Integer.toString(jsonObject.getInt("id")));
            return true;
        }
        catch (JSONException jsonE){
            error = "Ошибка получения данных";
            Toast.makeText(mContext, jsonE.getMessage()+"\n"+error, Toast.LENGTH_SHORT).show();
            return false;
        }
        catch (Exception e){
            error = "Другая ошибка";
            Toast.makeText(mContext, e.getMessage()+"\n"+error, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

}
