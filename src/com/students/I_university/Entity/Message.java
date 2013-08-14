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
    public boolean own = false;
    public String imageURL;
    public String messageText;
    public Timestamp createTime;

    public Message() {};

    public Message(String username, int input, String imageURL, String messageText, Timestamp createTime)
    {
        this.username = username;
        if(input == 1) this.own = true;
        this.imageURL = imageURL;
        this.messageText = messageText;
        this.createTime = createTime;
    }

    public void setOwn(boolean input)
    {
        this.own = input;
    }

    public String GetCreateTime()
    {
        Date date = new Date(createTime.getTime());
        SimpleDateFormat output = new SimpleDateFormat("dd.MM.yyyy");
        return output.format(date);
    }
}
