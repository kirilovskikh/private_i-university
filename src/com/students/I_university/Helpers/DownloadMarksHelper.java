package com.students.I_university.Helpers;

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

    public static String DownloadHelperMarks (String id) {
        String mark = "---";

        try {
            String token="2f6a54d4d69b8b16f97582dc1b519135";
            String url = "http://university.shiva.vps-private.net/webservice/rest/server.php?";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("wstoken", token));
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
                for (int i = 0; i <= jsonArray.length(); ++i) {
                   JSONObject object = jsonArray.getJSONObject(0).getJSONArray("grades").getJSONObject(i);
                   try {
                        if (object.getInt("userid")==9) mark = object.getString("grade");
                   }
                   catch (JSONException e){
                        mark = "N";
                   }
                }
                return mark;
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return mark;
    }


    public static HashMap<Integer, MarkDetails> DownloadHelperAssign (List<String> ids, Boolean modules) {
        HashMap<Integer, MarkDetails> hashMap = new HashMap<Integer, MarkDetails>();

        try {
            String token="2f6a54d4d69b8b16f97582dc1b519135";
            String url = "http://university.shiva.vps-private.net/webservice/rest/server.php?";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("wstoken", token));
            list.add(new BasicNameValuePair("wsfunction", "mod_assign_get_assignments"));

            for (int i=0;i<ids.size();++i){
                list.add(new BasicNameValuePair("courseids["+Integer.toString(i)+"]", ids.get(i).toString()));
            }

            list.add(new BasicNameValuePair("moodlewsrestformat", "json"));

            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null) {
                String courseName = "none";
                InputStream in = httpResponse.getEntity().getContent();
                String str = Utils.convertStreamToString(in);
                JSONObject jsonObject = new JSONObject(str);
                int id = jsonObject.getJSONArray("courses").getJSONObject(0).getInt("id");
                if (!modules)
                    courseName = jsonObject.getJSONArray("courses").getJSONObject(0).getString("fullname");
                JSONArray jsonArray = jsonObject.getJSONArray("courses").getJSONObject(0).getJSONArray("assignments");
                for (int i = 0; i <= jsonArray.length(); ++i) {
                    if (modules)
                        courseName = jsonArray.getJSONObject(i).getString("name");
                    String mark;
                    try {
                        mark = DownloadHelperMarks(jsonArray.getJSONObject(i).getString("id"));
                    }
                    catch (JSONException e){
                        mark = "---";
                    }
                    MarkDetails contactInfo = new MarkDetails(id, courseName, mark);
                    hashMap.put(i, contactInfo);
                }
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
