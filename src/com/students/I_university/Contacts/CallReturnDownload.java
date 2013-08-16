package com.students.I_university.Contacts;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.students.I_university.CustomAdapter.CustomAdapterContact;
import com.students.I_university.Helpers.ContactInfo;
import com.students.I_university.R;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 09.08.13
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public interface CallReturnDownload {

    public void returnResult(HashMap<Integer, ContactInfo> map);

    /**
     * Created with IntelliJ IDEA.
     * User: kirilovskikh
     * Date: 19.07.13
     * Time: 3:08
     * To change this template use File | Settings | File Templates.
     */

    class ContactActivity extends SherlockActivity implements CallReturnDownload {

        private Context mContext;
        private ListView listView;
        private ImageView imageView;

        /**
         * Для работы корректой работы Activity необходимо:
         *
         * 1) Передать id пользотеля в системе Moodle.
         * 2) Передать fullname.
         */

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
            setContentView(R.layout.contact);

            mContext = this;

            int userId = getIntent().getExtras().getInt("userId");
            String fullname = getIntent().getExtras().getString("fullname");

            getSupportActionBar().setTitle(fullname);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            listView = (ListView) findViewById(R.id.listView);
            imageView = (ImageView) findViewById(R.id.imageView);

            AsyncTaskGetContactInfo asyncTaskGetContactInfo = new AsyncTaskGetContactInfo(this, imageView);
            asyncTaskGetContactInfo.returnDownload = this;
            asyncTaskGetContactInfo.execute(userId);



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

        @Override
        public void returnResult(HashMap<Integer, ContactInfo> map) {
            if (map == null)
                return;

            ContactInfo contactInfo = map.get(0);
            HashMap<String, String> infMap = contactInfo.getMoreInfMap();
            String[] s = new String[infMap.size()];

            CustomAdapterContact customAdapterContact = new CustomAdapterContact(mContext, R.layout.contact_info_list_view_item, s, infMap);
            listView.setAdapter(customAdapterContact);
        }

    }
}
