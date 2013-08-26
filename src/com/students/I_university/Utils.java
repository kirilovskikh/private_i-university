package com.students.I_university;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.IllegalFormatCodePointException;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 09.08.13
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    public static String convertStreamToString (InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String getToken (Context context) {
        if (AuthorizationActivity.preferences != null)
            return AuthorizationActivity.preferences.getString("iutoken", null);

        SharedPreferences preferences = context.getSharedPreferences("Settings", context.MODE_PRIVATE);
        return preferences.getString("iutoken", null);
    }

    public static String getUrlFunction () {
        return "http://university.shiva.vps-private.net/webservice/rest/server.php?";
    }

}
