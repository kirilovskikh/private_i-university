package com.students.I_university.Courses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;

import com.students.I_university.Tools.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Echoes
 * Date: 22.08.13
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public class AsyncTaskGetTopics extends AsyncTask<Void, Void, Void> {
    public IReturnResult status = null;

    private ProgressDialog progressDialog;
    private Context mContext;

    List<TopicClass> TOPICS = new ArrayList<TopicClass>();

    public AsyncTaskGetTopics(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Загрузка ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //String url = "http://university.shiva.vps-private.net/webservice/rest/server.php?";
        String url = Utils.getUrlFunction();
        //String token = "41cbec83166cba95867cb195335c6e5c";
        String token = Utils.getToken(mContext);
        int courseID = CourseActivity.courseID;
        if (courseID == -1) {
        }

        try {
            //Организуем соединение
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            //Формируем список передаваемых параметров
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("wstoken", token));
            list.add(new BasicNameValuePair("wsfunction", "core_course_get_contents"));
            list.add(new BasicNameValuePair("courseid", Integer.toString(courseID)));
            list.add(new BasicNameValuePair("moodlewsrestformat", "json"));

            //Отправляем запрос, получаем ответ
            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            //Если не получен ответ, то генерируем исключение и функция возвращает false
            if (httpResponse == null) {
                throw new Exception("Не получен ответ");
            } else {
                //Парсерим ответ, сохраняем в виде JSON массива
                InputStream in = httpResponse.getEntity().getContent();
                String temp = convertStreamToString(in);
                JSONArray jsonTOPICS = new JSONArray(temp);

                String[] EXTS = {".doc", ".docx", ".ppt",".pptx",".pdf",".mp4", ".xls", ".xlsx"};

                for (int i = 0; i < jsonTOPICS.length(); i++) {
                    //Для каждой темы записываем ее Название
                    String Name = jsonTOPICS.getJSONObject(i).getString("name");

                    //Далее для каждой темы парсерим список вложенных элементов
                    List<ElementClass> ELEMENTS = new ArrayList<ElementClass>();
                    JSONArray jsonELEMENTS = new JSONArray(jsonTOPICS.getJSONObject(i).getString("modules"));
                    for (int j = 0; j < jsonELEMENTS.length(); j++) {
                        ElementClass element;
                        String Type = jsonELEMENTS.getJSONObject(j).getString("modname");
                        if (Type.equals("label"))
                        {
                            String Text = Html.fromHtml(jsonELEMENTS.getJSONObject(j).getString("description")).toString();
                            Text = Text.replace("\n\n", "\n");
                            Text = Text.substring(0, Text.length() - 1);
                            //String Text = Html.fromHtml(jsonELEMENTS.getJSONObject(j).getString("description")).toString().replace("\n\n","\n");
                            element = new ElementClass(Type, Text);
                        } else if (Type.equals("resource"))
                        {
                            JSONObject Atrributes = new JSONArray(jsonELEMENTS.getJSONObject(j).getString("contents")).getJSONObject(0);

                            String Text = jsonELEMENTS.getJSONObject(j).getString("name");

                            String FileName = Atrributes.getString("filename");
                            int index = FileName.lastIndexOf('.');
                            String Extension = FileName.substring(index);
                            if (!Arrays.asList(EXTS).contains(Extension))
                                continue;

                            Text = Text + Extension;

                            String URL = jsonELEMENTS.getJSONObject(j).getString("url");

                            double dSizeBytes = Atrributes.getDouble("filesize");
                            BigDecimal bd;
                            String Size;
                            if ((int) dSizeBytes/1048576 > 1)
                            {
                                //Перевести в МБ
                                bd = new BigDecimal(dSizeBytes/1048576);
                                bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                                Size = Double.toString(bd.doubleValue()) + " МБ";
                            }
                            else
                            {
                                //Перевести к КБ
                                bd = new BigDecimal(dSizeBytes/1024);
                                bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                                Size = Double.toString(bd.doubleValue()) + " КБ";
                            }
                            element = new ElementClass(Type, Text, URL, Extension, Size);
                        } else {
                            continue;
                        }
                        ELEMENTS.add(element);
                    }
                    TopicClass topic = new TopicClass(Name, ELEMENTS);
                    TOPICS.add(topic);
                }
            }
        } catch (Exception e) {
            TOPICS = null;
        } finally {
            return null;
        }

    }

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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);    //To change body of overridden methods use File | Settings | File Templates.
        progressDialog.dismiss();

        status.returnResult(TOPICS);
    }
}
