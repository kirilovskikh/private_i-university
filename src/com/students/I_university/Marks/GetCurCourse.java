package com.students.I_university.Marks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.students.I_university.AuthorizationActivity;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kotvaska
 * Date: 23.08.13
 * Time: 1:15
 * To change this template use File | Settings | File Templates.
 */
public class GetCurCourse extends AsyncTask <Void, Void, Void> {

    String url = "http://university.shiva.vps-private.net/webservice/rest/server.php?";
    String str;
    ProgressDialog progressDialog;
    String error;
    String course;
    public ArrayList<String> courses = new ArrayList<String>();
    private HashMap<Integer, MarkDetails> map = new HashMap<Integer, MarkDetails>();

    public CallBackMarks returnMarks;

    Context mContext;

    public GetCurCourse(Context mContext, String course) {
        this.mContext = mContext;
        this.course = course;
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

            list.add(new BasicNameValuePair("wstoken", AuthorizationActivity.preferences.getString("token",null)));


            list.add(new BasicNameValuePair("wsfunction", "moodle_enrol_get_users_courses"));
            list.add(new BasicNameValuePair("userid", "9"));

            list.add(new BasicNameValuePair("moodlewsrestformat", "json"));

            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null) {
                InputStream in = httpResponse.getEntity().getContent(); // Get the
                str = Utils.convertStreamToString(in);
                JSONArray jsonArray = new JSONArray(str);
                if (getDataFromJSON(jsonArray))
                    map = DownloadMarksHelper.DownloadHelperAssign(courses, true);
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
    private Boolean getDataFromJSON(JSONArray jsonArray ){
        try{
            int i=0;
            while (i<jsonArray.length()){
                if (Integer.toString(jsonArray.getJSONObject(i).getInt("id")).equals(course))
                    courses.add(course);
                ++i;
            }   //while...
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
