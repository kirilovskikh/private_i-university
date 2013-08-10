package com.students.I_university.Contacts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.students.I_university.R;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 19.07.13
 * Time: 3:08
 * To change this template use File | Settings | File Templates.
 */
public class ContactActivity extends SherlockActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.contact);

        getSupportActionBar().setTitle("Иванов Иван Иванович");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button openDialog = (Button) findViewById(R.id.openDialog);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Открывается диалог", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        com.actionbarsherlock.view.SubMenu subMenu = menu.addSubMenu("Настройки");
        subMenu.add(0, 1, 0, "Поделиться");

        com.actionbarsherlock.view.MenuItem menuItem = subMenu.getItem();
        menuItem.setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_dark).setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "Открывается окно выбора через что отправить контакт", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
