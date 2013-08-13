package com.students.I_university.MoodleRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import com.students.I_university.Entity.Message;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Akella on 14.08.13.
 */
public class MoodleRequestMessageChain extends MoodleRequest {

    private String wsfunction = "local_iuniversity_message_chain";
    private ArrayList<Message> messages;

    public MoodleRequestMessageChain(String token, String userid)
    {
        this.addParam("token", token);
        this.addParam("wsfunction", wsfunction);
        this.addParam("userid", userid);
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);

        ArrayList<Message> messages = new ArrayList<Message>();
        try
        {
            Message newMessage;
            String parameter;
            JSONArray jsonArray = new JSONArray(this.getResponse());
            int arrayLength = jsonArray.length();
            for(int i = 0; i < arrayLength; i++)
            {
                newMessage = new Message();
                parameter = jsonArray.getJSONObject(i).getString("author");
                if(!parameter.isEmpty()) newMessage.username = parameter;
                else throw new Exception("Incomplete data is received from the server");

                parameter = jsonArray.getJSONObject(i).getString("imageURL");
                if(!parameter.isEmpty()) newMessage.imageURL = parameter;
                else throw new Exception("Incomplete data is received from the server");

                parameter = jsonArray.getJSONObject(i).getString("messageText");
                if(!parameter.isEmpty()) newMessage.messageText = parameter;
                else throw new Exception("Incomplete data is received from the server");

                parameter = jsonArray.getJSONObject(i).getString("createTime");
                if(!parameter.isEmpty()) {
                    newMessage.createTime = new Timestamp(Long.parseLong(parameter));
                }
                else throw new Exception("Incomplete data is received from the server");

                parameter = jsonArray.getJSONObject(i).getString("own");
                if(!parameter.isEmpty()) {
                    newMessage.setOwn(Integer.parseInt(parameter));
                }
                else throw new Exception("Incomplete data is received from the server");

                messages.add(newMessage);
            }
            success = true;
        }
        catch(Exception e)
        {
            success = false;
            errorMessage = e.getMessage();
        }
    }
    public ArrayList<Message> getMessageChain()
    {
        return messages;
    }
}
