package com.students.I_university.MainScreen.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
//import com.example.actionbarsherlock.MyActivity;
import com.students.I_university.MainScreen.SlidingMenu.MainActivity;
import com.students.I_university.R;
import com.students.I_university.Tools.LogD;
import com.students.I_university.Tools.TypeFragment;
import com.students.I_university.Tools.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: Maksim
 * Date: 14.07.13
 * Time: 1:03
 * To change this template use File | Settings | File Templates.
 */

public class ErrorFragment extends SherlockFragment{

    private int typeFragment;
    private MainActivity activity;
    private String errorMessasge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        if (savedInstanceState != null)
            typeFragment = savedInstanceState.getInt("id");
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.error_fragment, null);

        activity = (MainActivity)getActivity();

        TextView textView = (TextView) view.findViewById(R.id.textView);
        Button button = (Button) view.findViewById(R.id.button);

        setTextError(textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reCallFragment();
            }
        });

        return view;
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private void reCallFragment () {
        switch (typeFragment) {
            case 0:
                Utils.changeFragment(activity, this, new CoursesList());
                break;
            case 1:
                Utils.changeFragment(activity, this, new ContactsList());
                break;
            case 2:
                Utils.changeFragment(activity, this, new MessengerList());
                break;
            case 3:
                Utils.changeFragment(activity, this, new AllMarksList());
                break;
        }
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    public void setTypeFragment (int typeFragment) {
        this.typeFragment = typeFragment;
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    public void setTextError (String string) {
        errorMessasge = string;
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);    //To change body of overridden methods use File | Settings | File Templates.
        outState.putInt("id", typeFragment);
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    private void setTextError (TextView textView) {
        if (errorMessasge == null)
            return;

        textView.setText(errorMessasge);
    }

}
