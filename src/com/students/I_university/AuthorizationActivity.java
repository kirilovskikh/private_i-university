package com.students.I_university;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.actionbarsherlock.app.SherlockActivity;
import com.students.I_university.SlidingMenu.MainActivity;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 22.07.13
 * Time: 0:39
 * To change this template use File | Settings | File Templates.
 */
public class AuthorizationActivity extends SherlockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.auth);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }
}
