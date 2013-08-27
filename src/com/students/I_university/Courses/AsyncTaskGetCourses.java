package com.students.I_university.Courses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AsyncTaskGetCourses extends AsyncTask<Void, Void, Void>
{
    public IReturnResult status = null;

    private ProgressDialog progressDialog;
    private Context mContext;

    List<CourseClass> COURSES = new ArrayList<CourseClass>();

    public AsyncTaskGetCourses(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Запускаем прогресс бар
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Загрузка ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String url = "http://university.shiva.vps-private.net/webservice/rest/server.php?";
        String token = "41cbec83166cba95867cb195335c6e5c";
        int userid = 5;
        try {
            //Организуем соединение
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            //Формируем список передаваемых параметров
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("wstoken", token));
            list.add(new BasicNameValuePair("wsfunction", "moodle_enrol_get_users_courses"));
            list.add(new BasicNameValuePair("userid", Integer.toString(userid)));
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
                JSONArray jsonCOURSES = new JSONArray(temp);

                for (int i = 0; i < jsonCOURSES.length(); i++) {
                    //Для каждого курса считываем его ID и Название, создаем переменную с информацией и сохраняем в массиве
                    int ID = jsonCOURSES.getJSONObject(i).getInt("id");
                    String Name = jsonCOURSES.getJSONObject(i).getString("fullname");
                    CourseClass courseInfo = new CourseClass(ID, Name);
                    COURSES.add(courseInfo);
                }
            }
        } catch (Exception e) {
            //Опустошаем массив с информацией и выводим сообщение об ошибке
            COURSES = null;
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();//Верно ли?
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
        //Загрываем прогресс бар и от имени CoursesActivity запускаем выгрузку данных на экран
        progressDialog.dismiss();
        status.returnResult(COURSES);
    }
}
