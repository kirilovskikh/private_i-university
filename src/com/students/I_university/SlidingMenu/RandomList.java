package com.students.I_university.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListFragment;
import com.students.I_university.MainScreens.AllMarksList;
import com.students.I_university.MainScreens.ContactsList;
import com.students.I_university.MainScreens.CoursesList;
import com.students.I_university.MainScreens.MessengerList;
import com.students.I_university.R;
import com.students.I_university.other;

public class RandomList extends SherlockListFragment{
	
	String[] list_contents = {
		"Список курсов",
		"Контакты",
		"Сообщения",
		"Все оценки"
	};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_contents);
        setListAdapter(stringArrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Fragment newFragment;
        FragmentManager fm = getFragmentManager();
        switch (position){
            case 0:
                newFragment = new CoursesList();
                break;
            case 1:
                newFragment = new ContactsList();
                break;
            case 2:
                newFragment = new MessengerList();
                break;
            case 3:
                newFragment = new AllMarksList();
                break;

            default:
                newFragment = new other();
                break;
        }
        if (newFragment != null)
            switchContent(newFragment);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, newFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    private void switchContent(Fragment fragment){
        MainActivity ma = (MainActivity)getActivity();
        ma.switchContent(fragment);
    }

}
