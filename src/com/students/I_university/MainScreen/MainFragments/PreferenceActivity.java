package com.students.I_university.MainScreen.MainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.students.I_university.Authorization.AuthorizationActivity;
import com.students.I_university.Authorization.Authorize;
import com.students.I_university.R;
import com.students.I_university.Tools.LogD;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 28.08.13
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */
public class PreferenceActivity extends SherlockPreferenceActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        addPreferencesFromResource(R.xml.preference);

        mContext = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PreferenceScreen logOut = (PreferenceScreen) findPreference("logout");
        logOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                logOut();
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

    private void logOut () {
        Authorize.LogOut("iutoken", mContext);
        startActivity(new Intent(mContext, AuthorizationActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

}
