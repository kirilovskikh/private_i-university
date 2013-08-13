package com.students.I_university;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.students.I_university.SlidingMenu.MainActivity;

import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 22.07.13
 * Time: 0:39
 * To change this template use File | Settings | File Templates.
 */
public class AuthorizationActivity extends SherlockActivity {

    public static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        preferences = getPreferences(MODE_PRIVATE);
        String s = preferences.getString("token","");
        String s1 = preferences.getString("iutoken","");        //временный токен
        if (s.length() > 0 && s1.length() > 0){                 //необходимо будет удалить
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        setContentView(R.layout.auth);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText email = (EditText) findViewById(R.id.editText);
                EditText password = (EditText) findViewById(R.id.editText1);

                /*      +++      +++      +++      +++      +++      +++*/
                /*          Временное решение (второй токен)            */
                /*          после вынесения функций в плагин            */
                /*          этот блок кода не будет нужен               */
                Authorize special = new Authorize(
                        "",
                        String.valueOf(email.getText()),
                        String.valueOf(password.getText()),
                        "iuniversity"  /*эту строку необходимо будет переставить
                                         в следующий вызов конструктора (authorize)*/
                );
                special.execute();
                try {
                    special.get();
                }
                catch (Exception ex){
                    Log.e("EXCEPTION", ex.toString());
                }
                if (special.authorized){
                    try {
                        preferences = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor ed = preferences.edit();
                        ed.putString("iutoken", special.getToken());
                        ed.commit();
                    }
                    catch (Exception ex){
                        Log.e("EXCEPTION", ex.toString());
                    }
                }
                /*      +++      +++      +++      +++      +++      +++*/

                Authorize authorize = new Authorize(
                        "",         //server
                        String.valueOf(email.getText()),
                        String.valueOf(password.getText()),
                        ""          //service
                );
                authorize.execute();

                try {
                    authorize.get();
                }
                catch (Exception ex){
                    Log.e("EXCEPTION", ex.toString());
                }

                if (authorize.authorized){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    try{
                        preferences = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor = AuthorizationActivity.preferences.edit();
                        editor.putString("token",authorize.getToken());
                        editor.commit();
                    } catch (Exception ex){
                        Log.e("EXCEPTION", ex.toString());
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Имя пользователя или пароль не верны", Toast.LENGTH_LONG).show();
                }
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }
}
