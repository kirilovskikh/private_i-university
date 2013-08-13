package com.students.I_university.Entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Akella on 13.08.13.
 */
public class Message {
    public String username;
    public String surname;
    public String imageURL;
    public String messageText;
    public Timestamp createTime;

    public Message(String username, String surname, String imageURL, String messageText, Timestamp createTime)
    {
        this.username = username;
        this.surname = surname;
        this.imageURL = imageURL;
        this.messageText = messageText;
        this.createTime = createTime;
    }

    public String GetCreateTime()
    {
        Date date = new Date(createTime.getTime());
        SimpleDateFormat output = new SimpleDateFormat("dd.MM.yyyy");
        return output.format(date);
    }
}
