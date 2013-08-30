package com.students.I_university.Authorization;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.students.I_university.Messages.MoodleRequest;
import com.students.I_university.R;
import com.students.I_university.MainScreen.SlidingMenu.MainActivity;
import com.students.I_university.Tools.Utils;

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
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        this.mContext = this;

        preferences = getSharedPreferences("Settings", MODE_PRIVATE);

        //проверяем токен, использование Utils.getToken не работает

        if ( preferences.getString("iutoken","").length() > 0){
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
                        mContext
                );
                authorize.execute();
                //присвоение значений выполняется в методе onPostExecute
            }
        });

    }

    //мега костыль с флагом, но работает :)
    public void onBackPressed(){
        super.onBackPressed();
        MainActivity.exitFlag = true;
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();    //To change body of overridden methods use File | Settings | File Templates.
        finish();
    }
}
