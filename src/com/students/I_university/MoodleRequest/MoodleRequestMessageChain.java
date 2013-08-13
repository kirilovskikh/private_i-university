package com.students.I_university.MoodleRequest;

import java.util.ArrayList;
import com.students.I_university.Entity.Message;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Akella on 14.08.13.
 */
public class MoodleRequestMessageChain extends MoodleRequest {

    private String wsfunction = "local_iuniversity_message_chain";

    public MoodleRequestMessageChain(String token, String userid)
    {
        this.addParam("token", token);
        this.addParam("wsfunction", wsfunction);
        this.addParam("userid", userid);
    }

    public ArrayList<Message> getMessageChain()
    {
        ArrayList<Message> messages = new ArrayList<Message>();
        JSONArray jsonArray = new JSONArray(this.getResponse());
        int arrayLength = jsonArray.length();
        for(int i = 0; i < arrayLength; i++)
        {
            Message newMessage = new Message();
            //newMessage = jsonArray.getJSONObject(i).get
        }

    }
}
