package com.students.I_university.Contacts;

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
 * User: kirilovskikh
 * Date: 09.08.13
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
public class DownloadContactHelper  {

    public static HashMap<Integer, ContactInfo> DownloadContactsInfo (List<String> listId) {
        HashMap<Integer, ContactInfo> hashMap = new HashMap<Integer, ContactInfo>();

        try {
            String token = "ac072b83ec6761808d3994dc446557f9";
            String url = "http://university.shiva.vps-private.net/webservice/rest/server.php?";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("wstoken", token));
            list.add(new BasicNameValuePair("wsfunction", "moodle_user_get_users_by_id"));

            for (int i = 0; i < listId.size(); ++i)
                list.add(new BasicNameValuePair("userids[" + Integer.toString(i) + "]", listId.get(i).toString()));


            list.add(new BasicNameValuePair("moodlewsrestformat", "json"));

            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null) {
                InputStream in = httpResponse.getEntity().getContent();
                String str = Utils.convertStreamToString(in);
                JSONArray jsonArray = new JSONArray(str);

                for (int i = 0; i <= jsonArray.length(); ++i) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String fullName = jsonObject.getString("fullname");
                    String phone;
                    try {
                        phone = jsonObject.getString("phone1");
                    }
                    catch (JSONException e){
                        phone = null;
                    }
                    ContactInfo contactInfo = new ContactInfo(id, fullName, phone);
                    hashMap.put(i, contactInfo);
                }
                return hashMap;
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return hashMap;
    }

}
