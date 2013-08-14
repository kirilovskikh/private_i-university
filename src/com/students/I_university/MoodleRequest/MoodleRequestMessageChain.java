package com.students.I_university.MoodleRequest;

import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import com.students.I_university.Entity.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Akella on 14.08.13.
 */
public class MoodleRequestMessageChain extends MoodleRequest {

    private String wsfunction = "local_iuniversity_message_chain";

    public MoodleRequestMessageChain(String token, String userid)
    {
        this.addParam("wstoken", token);
        this.addParam("wsfunction", wsfunction);
        this.addParam("userid", userid);
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        return;
    }
    public ArrayList<Message> getMessageChain()
    {
        ArrayList<Message> messages;
        Message newMessage;
        String parameter;
        JSONArray jsonArray;
        int arrayLength;

        try
        {
            if(!this.isSuccess()) throw new Exception("Unsuccessful request!");

            messages = new ArrayList<Message>();
            jsonArray = new JSONArray(this.getResponse());
            arrayLength = jsonArray.length();
            if(arrayLength == 0) throw new Exception("No messages to show");

            for(int i = 0; i < arrayLength; i++)
            {
                newMessage = new Message();
                parameter = jsonArray.getJSONObject(i).getString("author");
                if(!parameter.isEmpty()) newMessage.username = parameter;
                else throw new Exception("Incomplete data is received from the server");

                parameter = jsonArray.getJSONObject(i).getString("imageURL");
                if(!parameter.isEmpty()) newMessage.imageURL = parameter;
                else throw new Exception("Incomplete data is received from the server");

                parameter = jsonArray.getJSONObject(i).getString("fullmessage");
                if(!parameter.isEmpty()) newMessage.messageText = parameter;
                else throw new Exception("Incomplete data is received from the server");

                parameter = jsonArray.getJSONObject(i).getString("timecreated");
                if(!parameter.isEmpty()) {
                    newMessage.createTime = new Timestamp(Long.parseLong(parameter) * 1000);
                }
                else throw new Exception("Incomplete data is received from the server");

                parameter = jsonArray.getJSONObject(i).getString("own");
                if(!parameter.isEmpty()) {
                    newMessage.setOwn(Boolean.parseBoolean(parameter));
                }
                else throw new Exception("Incomplete data is received from the server");

                messages.add(newMessage);
            }
            return messages;
        }
        catch(Exception e)
        {
            success = false;
            errorMessage = e.getMessage();
            return null;
        }
    }
}
