package com.students.I_university.Contacts;

import com.students.I_university.LogD;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 09.08.13
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class ContactInfo {

    private int id;
    private String fullName;
    private String phoneNumber;
    private HashMap<String, String> moreInfMap;
    private String smallImgUrl;
    private String normalImgUrl;

    public ContactInfo (int id, String fullName, String phoneNumber, String smallImgUrl) {
        setId(id);
        setFullName(fullName);
        setPhoneNumber(phoneNumber);
        setSmallImgUrl(smallImgUrl);

        moreInfMap = new HashMap<String, String>();
    }

    public ContactInfo() {
        moreInfMap = new HashMap<String, String>();
    }

    public void setSmallImgUrl (String smallImgUrl) {
        this.smallImgUrl = smallImgUrl;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setFullName (String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId () {
        return id;
    }

    public String getFullName () {
        return fullName;
    }

    public String getPhoneNumber () {
        return phoneNumber;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }

    public void createMoreInfMap (JSONObject jsonObject) {
        String[] needRow = new String[] {"email", "phone1", "phone2", "icq", "skype"};
        JSONArray namesAllRow = jsonObject.names();

        try {
            for (int i = 0; i < namesAllRow.length(); ++i) {
                String nameRow = namesAllRow.getString(i);
                if ((nameRow.equals(needRow[0])) || nameRow.equals(needRow[1]) || nameRow.equals(needRow[2]) ||
                        nameRow.equals(needRow[3]) || nameRow.equals(needRow[4])) {
                    String s = jsonObject.getString(nameRow);
                    moreInfMap.put(nameRow, s);
                }
            }
        }
        catch (JSONException e) {
        }

    }

    public void setNormalImgUrl (String url) {
        this.normalImgUrl = url;
    }

    public HashMap<String, String> getMoreInfMap () {
        return moreInfMap;
    }

}
