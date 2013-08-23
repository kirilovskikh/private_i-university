package com.students.I_university.MoodleRequest;

import com.students.I_university.Entity.ListMessage;
import com.students.I_university.Entity.Message;
import org.json.JSONArray;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sveta
 * Date: 8/17/13
 * Time: 11:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class MoodleRequestListMessages extends MoodleRequest {

    private String wsfunction = "local_iuniversity_recent_messages";

    public MoodleRequestListMessages(String token){
        this.addParam("wstoken", token);
        this.addParam("wsfunction", wsfunction);
    }

    @Override
        protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        return;
    }

    public ArrayList<ListMessage> getMessage(){
        ArrayList<ListMessage> message;
        Message newMessage;
        JSONArray jsonArray;
        int arrayLength;
        String id;
        String sms;

        try
        {
            if(!this.isSuccess()) throw new Exception("Unsuccessful request!");

            message = new ArrayList<ListMessage>();
            jsonArray = new JSONArray(this.getResponse());
            arrayLength = jsonArray.length();
            if(arrayLength == 0) throw new Exception("No dialogue");

            for(int i = 0; i < arrayLength; i++)
            {
                newMessage = new Message();
                sms = jsonArray.getJSONObject(i).getString("id");  // посмотреть id
                if(!sms.isEmpty()) newMessage.username = sms;
                else throw new Exception("Incomplete data is received from the server");

                newMessage = new Message();
                sms = jsonArray.getJSONObject(i).getString("firstname");
                if(!sms.isEmpty()) newMessage.username = sms;
                else throw new Exception("Incomplete data is received from the server");

                newMessage = new Message();
                sms = jsonArray.getJSONObject(i).getString("lastname");
                if(!sms.isEmpty()) newMessage.username = sms;
                else throw new Exception("Incomplete data is received from the server");

                sms = jsonArray.getJSONObject(i).getString("imageURL");
                if(!sms.isEmpty()) newMessage.imageURL = sms;
                else throw new Exception("Incomplete data is received from the server");

                sms = jsonArray.getJSONObject(i).getString("smallmessage");
                if(!sms.isEmpty()) newMessage.messageText = sms;
                else throw new Exception("Incomplete data is received from the server");

                sms = jsonArray.getJSONObject(i).getString("timecreated");
                if(!sms.isEmpty()) {
                    newMessage.createTime = new Timestamp(Long.parseLong(sms) * 1000);
                }
                else throw new Exception("Incomplete data is received from the server");

                message.add(newMessage);
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
