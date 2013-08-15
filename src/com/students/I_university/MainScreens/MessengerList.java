package com.students.I_university.MainScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.students.I_university.CustomAdapter.CustomAdapterDialogs;
import com.students.I_university.R;
import com.students.I_university.dialogActivity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.students.I_university.Utils.convertStreamToString;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 23.07.13
 * Time: 3:40
 * To change this template use File | Settings | File Templates.
 */
public class MessengerList extends SherlockFragment {

    String funcName;
    String web_way = "http://university.shiva.vps-private.net/webservice/rest/server.php?";

    String str;
    String token = "03f57c6b674bc8280095dc09837cb170";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       try {


           HttpClient httpClient = new DefaultHttpClient();
           HttpPost httpPost = new HttpPost(web_way);

           List<NameValuePair> list = new ArrayList<NameValuePair>();
           list.add(new BasicNameValuePair("wstoken", token));
           list.add(new BasicNameValuePair("moodlewsrestformat", "json"));

           list.add(new BasicNameValuePair("wsfunction", "local_wstemplate_recent_messages"));
           list.add(new BasicNameValuePair("limitfrom", "0"));
           list.add(new BasicNameValuePair("limitto", "0"));

           httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
           HttpResponse httpResponse = httpClient.execute(httpPost);

           String[] kontakt = new String[0];
           String[] sms = new String[0];
           if (httpResponse != null) {
               InputStream in = httpResponse.getEntity().getContent(); // Get the
               str = convertStreamToString(in);
               JSONArray jsonArray = new JSONArray(str);

               sms = new String[jsonArray.length()];
               kontakt = new String[jsonArray.length()];

               for (int i = 0; i < jsonArray.length(); i++) {
                   JSONObject jsonObject = jsonArray.getJSONObject(i);
                   kontakt[i] = jsonObject.getString("firstname") + jsonObject.getString("lastname");
                   sms[i] = jsonObject.getString("smallmessage");
                   //         String s3 = jsonObject.getString("timecreated");
               }
           }
           //  return null;  //To change body of implemented methods use File | Settings | File Templates.
           //  final String[] kontakt = new String[] {"Старикова Анастасия Константиновна", "Сидоров Петр Сергеевич", "Петров Алексей Федорович", "Сидорова Дарья Ивановна"};
           // final String[] sms = new String[] {"Hello","Hi","Привет","Ура"};

           View view = inflater.inflate(R.layout.listview_layout, null);
           ListView kontaktList = (ListView) view.findViewById(R.id.listView);
           CustomAdapterDialogs adapter = new CustomAdapterDialogs(getSherlockActivity(), R.layout.sms, R.id.textView, R.id.textView1, kontakt, sms);


           // устанавливаем для списка адаптер
           kontaktList.setAdapter(adapter);

           kontaktList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Intent intent = new Intent(getSherlockActivity(), dialogActivity.class);
                   //  intent.putExtra("name", strings[position]);
                   startActivity(intent);
               }
           });

           return view;
       }
       catch (Exception e) {
           str = "exp";
       }
      return null;
    }
}
