package com.students.I_university.Marks;

import android.content.Context;

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
 * User: kotvaska
 * Date: 11.08.13
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class DownloadMarksHelper {

    public static String DownloadHelperMarks (Context mContext, String id, Boolean modules) {
        String mark = "---";

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
                //если несколько модулей в курсе,
                //за которые выставляются оценки
                if (jsonArray.length()==0)
                    mark = "---";
                else {
                    while (i<jsonArray.length()) {
                        int j=0;
                        JSONArray array = jsonArray.getJSONObject(i).getJSONArray("grades");
                        //ищем оценку пользователя в модуле
                        while (j<array.length()){
                            try {
                                if (Integer.toString(array.getJSONObject(j).getInt("userid")).equals(Utils.getUserID(mContext))){
                                    mark = array.getJSONObject(j).getString("grade");
                                }
                            }
                            catch (JSONException e){
                                mark = "N";
                            }
                            ++j;
                        } //while grades...
                        ++i;
                    } //while assignments...
                    mark = mark.substring(0,5);
                } //ifelse...
                return mark;
            } //if response...

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
        float res = 0;

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
                //проходим по всем курсам пользователя
                while (i<jsonArray.length()){
                    int j=0;
                    int id = jsonArray.getJSONObject(i).getInt("id");
                    if (!modules)
                        courseName = jsonArray.getJSONObject(i).getString("fullname");
                    JSONArray array = jsonArray.getJSONObject(i).getJSONArray("assignments");
                    //проходим по всем заданиям в курсе,
                    //за которые можно поставить оценку
                    if (array.length()==0){
                        if (modules)
                           courseName = "Нет оценок";
                        MarkDetails contactInfo = new MarkDetails(id, courseName, "---", "-1");
                        hashMap.put(i, contactInfo);
                    }
                    while (j<array.length()) {
                        if (modules)
                            courseName = array.getJSONObject(j).getString("name");
                        String mark;
                        String assign = array.getJSONObject(j).getString("id");
                        mark = DownloadHelperMarks(mContext, assign, modules);
                        if (!mark.equals("---"))
                            res += Float.parseFloat(mark);
                        if (!modules && (j+1)==array.length())
                            mark = Float.toString(res / (j+1)).substring(0,5);
                        MarkDetails contactInfo = new MarkDetails(id, courseName, mark, assign);
                        if (modules)
                            hashMap.put(j, contactInfo);
                        else
                            hashMap.put(i, contactInfo);
                        ++j;
                    } //while assignment...
                    ++i;
                } //while course...
                return hashMap;
            } //if response...

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
