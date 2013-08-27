package com.students.I_university.MoodleRequest;

import android.content.Context;
import android.graphics.Bitmap;
import com.students.I_university.Entity.ListMessage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sveta
 * Date: 8/23/13
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MoodleRequestListMessage extends MoodleRequest {

     public MoodleRequestListMessage (Context context)
     {
         this.mContext = context;
         this.params = new ArrayList<NameValuePair>();
         this.params.add(new BasicNameValuePair("moodlewsrestformat", "json"));

     }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        return;
    }

    public ArrayList<ListMessage> getMessage(){
        ArrayList<ListMessage> message;
        ListMessage newMessage;
        JSONArray jsonArray;
        int arrayLength;
        String id;
        String sms;
        Image image = new Image();
        Bitmap bitmap;
        try
        {
            if(!this.isSuccess()) throw new Exception("Unsuccessful request!");

            message = new ArrayList<ListMessage>();
            jsonArray = new JSONArray(this.getResponse());
            arrayLength = jsonArray.length();
            if(arrayLength == 0) throw new Exception("No dialogue");

            for(int i = 0; i < arrayLength; i++)
            {
                newMessage = new ListMessage();
                sms = jsonArray.getJSONObject(i).getString("id");  // посмотреть id
                if(!sms.isEmpty()) newMessage.id =sms;
                else continue;

                sms = jsonArray.getJSONObject(i).getString("firstname");
                if(!sms.isEmpty()) newMessage.username = sms;
                else continue;


                sms = jsonArray.getJSONObject(i).getString("lastname");
                if(!sms.isEmpty()) newMessage.username +=" " + sms;
                else continue;

                sms = jsonArray.getJSONObject(i).getString("imageURL");
                if(!sms.isEmpty()) newMessage.imageURL = sms;
                else continue;

                sms = jsonArray.getJSONObject(i).getString("smallmessage");//.trim();

                if(!sms.isEmpty()) newMessage.sms =  sms;
                else continue;


                sms = jsonArray.getJSONObject(i).getString("timecreated");
                if(!sms.isEmpty()) {
                    newMessage.createTime = new Timestamp(Long.parseLong(sms) * 1000);
                }
                else continue;
                boolean flag= false;
                for(int j=0;j<message.size(); j++)
                {
                    if(newMessage.username.equals(message.get(j).username)){
                       if(newMessage.createTime.getTime() > message.get(j).createTime.getTime())
                        {
                            message.remove(j) ;
                        }
                        else
                           flag=true;
                    }

                }


                if(!flag)
                message.add(newMessage);
                if(true){}
            }
            return message;

        }
        catch(Exception e)

        {
            success = false;
            errorMessage = e.getMessage();
            return null;
        }



    }


}
