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

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 23.07.13
 * Time: 3:40
 * To change this template use File | Settings | File Templates.
 */
public class MessengerList extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout, null);

        final String[] kontakt = new String[] {"Старикова Анастасия Константиновна", "Сидоров Петр Сергеевич", "Петров Алексей Федорович", "Сидорова Дарья Ивановна"};
        final String[] sms = new String[] {"Hello","Hi","Привет","Ура"};

        ListView kontaktList = (ListView) view.findViewById(R.id.listView);
        CustomAdapterDialogs adapter = new CustomAdapterDialogs(getSherlockActivity(), R.layout.sms,R.id.textView,R.id.textView1,kontakt,sms);


        // устанавливаем для списка адаптер
        kontaktList.setAdapter(adapter);

        kontaktList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getSherlockActivity(), dialogActivity.class);
                intent.putExtra("userId", 5);
                //  intent.putExtra("name", strings[position]);
                startActivity(intent);
            }
        });
        return view;
    }
}
