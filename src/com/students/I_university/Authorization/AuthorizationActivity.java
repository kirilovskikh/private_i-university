package com.students.I_university.Authorization;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.students.I_university.R;
import com.students.I_university.MainScreen.SlidingMenu.MainActivity;
import com.students.I_university.Tools.Utils;

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
        preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String s1 = preferences.getString("iutoken","");        //проверяем токен, использование Utils.getToken не работает
        if ( s1.length() > 0){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        setContentView(R.layout.auth);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText email = (EditText) findViewById(R.id.editText);
                EditText password = (EditText) findViewById(R.id.editText1);

                Authorize authorize = new Authorize(
                        "",         //server
                        String.valueOf(email.getText()),
                        String.valueOf(password.getText()),
                        "iuniversity",          //service
                        getApplicationContext()
                );
                authorize.execute();

                try {
                    authorize.get();
                }
                catch (Exception ex){
                    Log.e("EXCEPTION", ex.toString());
                }

                if (authorize.authorized){

                    try{
                        preferences = getSharedPreferences("Settings", MODE_PRIVATE);
                        SharedPreferences.Editor editor = AuthorizationActivity.preferences.edit();
                        editor.putString("iutoken",authorize.getToken());
                        editor.commit();
                    } catch (Exception ex){
                        Log.e("EXCEPTION", ex.toString());
                    }
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Имя пользователя или пароль не верны", Toast.LENGTH_LONG).show();
                }
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
