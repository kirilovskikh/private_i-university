package com.students.I_university.Marks;

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

    protected ProgressDialog progressDialog;
    protected String course;
    protected Boolean module;
    public static Boolean error;
    public ArrayList<String> courses = new ArrayList<String>();
    private HashMap<Integer, MarkDetails> map = new HashMap<Integer, MarkDetails>();

    public CallBackMarks returnMarks;

    Context mContext;

    public GetCourses (Context mContext, Boolean module, String course) {
        this.mContext = mContext;
        this.course = course;
        this.module = module;
    }

    @Override
    protected void onPreExecute() {
        error = true;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Загрузка ...");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Utils.getUrlFunction());

            List<NameValuePair> list = new ArrayList<NameValuePair>();

            list.add(new BasicNameValuePair("wstoken", Utils.getToken(mContext)));


            list.add(new BasicNameValuePair("wsfunction", "moodle_enrol_get_users_courses"));
            list.add(new BasicNameValuePair("userid", Utils.getUserID(mContext)));

            list.add(new BasicNameValuePair("moodlewsrestformat", "json"));

            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null) {
                InputStream in = httpResponse.getEntity().getContent();
                String str = Utils.convertStreamToString(in);
                JSONArray jsonArray = new JSONArray(str);
                //если всё хорошо и курсы вернулись,
                //то получаем оценки
                if (getDataFromJSON(jsonArray))
                    map = DownloadMarksHelper.DownloadHelperAssign(mContext, courses, module);
            } //if response...
            else error = false;

        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            error = true;
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);    //To change body of overridden methods use File | Settings | File Templates.
        progressDialog.dismiss();
        returnMarks.returnMarks(map);
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    private Boolean getDataFromJSON(JSONArray jsonArray ){
        try{
            int i=0;
            while (i<jsonArray.length()){
                //если нужны модули курса
                if (module){
                    if(Integer.toString(jsonArray.getJSONObject(i).getInt("id")).equals(course))
                        courses.add(course);
                }
                else
                    //если нужны сами курсы пользователя
                    courses.add(Integer.toString(jsonArray.getJSONObject(i).getInt("id")));
                ++i;
            }   //while...
            return error;
        }
        catch (JSONException jsonE){
            jsonE.printStackTrace(System.out);
            error = false;
            return error;
        }
        catch (Exception e){
            e.printStackTrace(System.out);
            return false;
        }

    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

}
