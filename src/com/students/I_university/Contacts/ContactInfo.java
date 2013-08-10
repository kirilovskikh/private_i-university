package com.students.I_university.Contacts;

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

    public ContactInfo (int id, String fullName, String phoneNumber) {
        setId(id);
        setFullName(fullName);
        setPhoneNumber(phoneNumber);
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

}
