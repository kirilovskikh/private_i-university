package com.students.I_university.Messages;

import android.graphics.Bitmap;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Sveta
 * Date: 8/18/13
 * Time: 12:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListMessage {

    public String id ;
    public String username;
    public boolean own = false;
    public String imageURL;
    public String sms;
    public Timestamp createTime;
    public Bitmap bitmap;

    public ListMessage() {}

    public ListMessage(String id, String username, String imageURL, String sms, Timestamp createTime)
    {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.sms = sms;
        this.createTime = createTime;
    }

    public String GetCreateTime()
    {
        Date date = new Date(createTime.getTime());
        SimpleDateFormat output = new SimpleDateFormat("dd.MM.yyyy");
        return output.format(date);
    }

  /*    public String GetCreateImage()
      {

          try {
              ImageView iv;
              iv = new ImageView;
              URL url = new URL(imageURL);
              InputStream content = (InputStream)url.getContent();
              Drawable d = Drawable.createFromStream(content, "src");
              iv.setImageDrawable(d);

          } catch (MalformedURLException e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          } catch (IOException e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          }
          return null;
      }*/
}
