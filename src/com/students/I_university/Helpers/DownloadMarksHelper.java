package com.students.I_university.Helpers;

import android.content.Context;
import com.students.I_university.AuthorizationActivity;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kotvaska
 * Date: 11.08.13
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class DownloadMarksHelper {

    public static String DownloadHelperMarks (Context mContext, String id, Boolean modules) {
        String mark = "---";
        float res = 0;

        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Utils.getUrlFunction());

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("wstoken", Utils.getToken(mContext)));
            list.add(new BasicNameValuePair("wsfunction", "mod_assign_get_grades"));

            list.add(new BasicNameValuePair("assignmentids[0]", id));


            list.add(new BasicNameValuePair("moodlewsrestformat", "json"));

            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null) {
                InputStream in = httpResponse.getEntity().getContent();
                String str = Utils.convertStreamToString(in);
                JSONObject jsonObject = new JSONObject(str);

                JSONArray jsonArray = jsonObject.getJSONArray("assignments");
                int i=0;
                int j=0;
                //если несколько модулей в курсе,
                //за которые выставляются оценки
                while (i<jsonArray.length()) {
                    JSONArray array = jsonArray.getJSONObject(i).getJSONArray("grades");
                    //ищем оценку пользователя в модуле
                    while (j<array.length()){
                        try {
                            //сюда вместо 9 надо вставить ИД пользователя
                            if (Integer.toString(array.getJSONObject(j).getInt("userid")).equals("9")){
                                mark = array.getJSONObject(j).getString("grade");
                            }
                        }
                        catch (JSONException e){
                            mark = "N";
                        }
                        ++j;
                    } //while grades...
                    res += Float.parseFloat(mark.substring(0,5));
                    ++i;
                } //while assignments...
                if (modules)
                    mark = mark.substring(0,5);
                else
                    mark = Float.toString(res / i);
                return mark;
            }

        } catch (IOException e) {
            e.printStackTrace();
            //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();
            //To change body of catch statement use File | Settings | File Templates.
        }

        return mark;
    }


    public static HashMap<Integer, MarkDetails> DownloadHelperAssign (Context mContext, List<String> ids, Boolean modules) {
        HashMap<Integer, MarkDetails> hashMap = new HashMap<Integer, MarkDetails>();

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Utils.getUrlFunction());

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("wstoken", Utils.getToken(mContext)));
            list.add(new BasicNameValuePair("wsfunction", "mod_assign_get_assignments"));

            for (int i=0;i<ids.size();++i){
                list.add(new BasicNameValuePair("courseids["+Integer.toString(i)+"]", ids.get(i)));
            }

            list.add(new BasicNameValuePair("moodlewsrestformat", "json"));

            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null) {
                String courseName = "none";
                InputStream in = httpResponse.getEntity().getContent();
                String str = Utils.convertStreamToString(in);
                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray = jsonObject.getJSONArray("courses");
                int i=0;
                int j=0;
                //проходим по всем курсам пользователя
                while (i<jsonArray.length()){
                    int id = jsonArray.getJSONObject(i).getInt("id");
                    if (!modules)
                        courseName = jsonArray.getJSONObject(i).getString("fullname");
                    JSONArray array = jsonArray.getJSONObject(i).getJSONArray("assignments");
                    //проходим по всем заданиям в курсе,
                    //за которые можно поставить оценку
                    while (j<array.length()) {
                        if (modules)
                            courseName = array.getJSONObject(j).getString("name");
                        String mark;
                        String assign = array.getJSONObject(j).getString("id");
                        mark = DownloadHelperMarks(mContext, assign, modules);
                        MarkDetails contactInfo = new MarkDetails(id, courseName, mark, assign);
                        hashMap.put(i, contactInfo);
                        ++j;
                    } //while assignment...
                    ++i;
                } //while course...
                return hashMap;
            }

        } catch (IOException e) {
            e.printStackTrace();
            //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();
            //To change body of catch statement use File | Settings | File Templates.
        }

        return hashMap;
    }

}
