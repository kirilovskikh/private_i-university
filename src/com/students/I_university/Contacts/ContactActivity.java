package com.students.I_university.Contacts;

import a_vcard.android.syncml.pim.vcard.ContactStruct;
import a_vcard.android.syncml.pim.vcard.VCardComposer;
import a_vcard.android.syncml.pim.vcard.VCardException;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.students.I_university.CustomAdapter.CustomAdapterContact;
import com.students.I_university.Helpers.ContactInfo;
import com.students.I_university.LogD;
import com.students.I_university.R;
import com.students.I_university.dialogActivity;

import java.io.*;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 19.07.13
 * Time: 3:08
 * To change this template use File | Settings | File Templates.
 */

public class ContactActivity extends SherlockActivity implements CallReturnDownload {

    private Context mContext;
    private ListView listView;
    private ImageView imageView;
    private HashMap<Integer, ContactInfo> map;

    private String fullname;
    private int userId;


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

        userId = getIntent().getExtras().getInt("userId");
        fullname = getIntent().getExtras().getString("fullname");

        getSupportActionBar().setTitle(fullname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        imageView = (ImageView) findViewById(R.id.imageView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedKey = getSelectedKey(i);

                    if (selectedKey.equals("phone1")) {
                        String number = map.get(0).getMoreInfMap().get("phone1");
                        callDialog(number);
                        return;
                    }

                    if (selectedKey.equals("phone2"))  {
                        String number = map.get(0).getMoreInfMap().get("phone2");
                        callDialog(number);
                        return;
                    }

                    if (selectedKey.equals("email")) {
                        String email = map.get(0).getMoreInfMap().get("email");
                        emailDialog(email);
                        return;
                    }
                }
        });

        AsyncTaskGetContactInfo asyncTaskGetContactInfo = new AsyncTaskGetContactInfo(this, imageView);
        asyncTaskGetContactInfo.returnDownload = this;
        asyncTaskGetContactInfo.execute(userId);

        Button openDialog = (Button) findViewById(R.id.openDialog);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Открывается диалог", Toast.LENGTH_SHORT).show();
                
                Intent intent = new Intent(mContext, dialogActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        com.actionbarsherlock.view.SubMenu subMenu = menu.addSubMenu("Настройки");
        subMenu.add(0, 1, 0, "Поделиться");

        com.actionbarsherlock.view.MenuItem menuItem = subMenu.getItem();
        menuItem.setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_dark).setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case 1:
                shareContact();
                break;
        }
        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    @Override
    public void returnResult(HashMap<Integer, ContactInfo> map) {
        if (map == null)
            return;

        this.map = map;
        ContactInfo contactInfo = map.get(0);
        HashMap<String, String> infMap = contactInfo.getMoreInfMap();
        String[] s = new String[infMap.size()];

        CustomAdapterContact customAdapterContact = new CustomAdapterContact(mContext, R.layout.contact_info_list_view_item, s, infMap);
        listView.setAdapter(customAdapterContact);
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private String getSelectedKey (int id) {
        int mId = 0;

        for (String s : map.get(0).getMoreInfMap().keySet()) {
            if (mId == id)
                return s;
            ++mId;
        }

        return null;
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private void shareContact () {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(writeDateAboutContact()));
            intent.setType("text/x-vcard");
            startActivity(intent);
        }
        catch (Exception e) {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }

    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private void callContact (String number) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + number));
        startActivity(intent);
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private void callEmail (String email) {
        Intent send = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode(email);
        Uri uri = Uri.parse(uriText);
        send.setData(uri);
        startActivity(Intent.createChooser(send, "Send mail..."));
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private File writeDateAboutContact() {
        try {
            VCardComposer composer = new VCardComposer();
            ContactStruct struct = new ContactStruct();

            struct.name = fullname;

            String phone1 = map.get(0).getPhoneNumber1();
            if (phone1 != null)
                struct.addPhone(Contacts.Phones.TYPE_MOBILE, phone1, null, true);

            String phone2 = map.get(0).getPhoneNumber2();
            if (phone2 != null)
                struct.addPhone(Contacts.Phones.TYPE_HOME, phone2, null, false);

            String email = map.get(0).getEmail();
            if (email != null)
                struct.addContactmethod(Contacts.KIND_EMAIL, Contacts.KIND_EMAIL, email, null, true);

            String strVcard = composer.createVCard(struct, VCardComposer.VERSION_VCARD30_INT);

            File vcfFile = new File(this.getExternalFilesDir(null), fullname + ".vcf");
            FileWriter fileWriter = new FileWriter(vcfFile);
            fileWriter.write(strVcard);
            fileWriter.close();

            return vcfFile;
        }
        catch (Exception e) {
            return null;
        }

    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private void callDialog (final String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Внимание");
        builder.setMessage("Сейчас вы совершите звонок по номеру: " + number);
        builder.setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callContact(number);
            }
        });
        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private void emailDialog (final String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Внимание");
        builder.setMessage("Вы действительно хотите написать email на адрес: " + email);
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callEmail(email);
            }
        });
        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

}
