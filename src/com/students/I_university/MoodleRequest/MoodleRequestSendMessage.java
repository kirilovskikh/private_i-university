package com.students.I_university.MoodleRequest;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Akella on 16.08.13.
 */
public class MoodleRequestSendMessage extends MoodleRequest {

    private String function = "core_message_send_instant_messages";
    public String foo;

    public MoodleRequestSendMessage(Context context, String token, String toUserId)
    {
        super(context);

        this.addParam("wstoken", token);
        this.addParam("wsfunction", function);
        this.addParam("messages[0][touserid]", toUserId);
        this.addParam("messages[0][textformat]", "2");
        //this.addParam("messages[0][text]", messageText);
    }

    public void setMessageText(String messageText)
    {
        this.addParam("messages[0][text]", messageText);
    }

    @Override
    protected void onPostExecute(Void aVoid){

        if(this.isSuccess()){
            try
            {
                JSONObject jsonObject = new JSONObject(this.getResponse());
                if(jsonObject.has("exception"))
                {
                    this.success = false;
                    this.errorMessage = jsonObject.getString("message");
                }
                else
                {
                    if(jsonObject.getInt("msgid") < 0)
                    {
                        this.success = false;
                        this.errorMessage = jsonObject.getString("errormessage");
                    }
                }
            }
            catch(Exception e)
            {
                this.success = false;
                this.errorMessage = e.getMessage();
            }
        }

        super.onPostExecute(aVoid);
        return;
    }
}
