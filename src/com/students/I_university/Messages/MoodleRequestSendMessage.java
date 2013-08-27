package com.students.I_university.Messages;
import android.content.Context;
import org.json.JSONArray;

/**
 * Created by Akella on 16.08.13.
 */
public class MoodleRequestSendMessage extends MoodleRequest {

    private String function = "core_message_send_instant_messages";

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
                JSONArray jsonObject = new JSONArray(this.getResponse());
                    if(jsonObject.getJSONObject(0).getInt("msgid") < 0)
                    {
                        this.success = false;
                        this.errorMessage = jsonObject.getJSONObject(0).getString("errormessage");
                    }
            }
            catch(Exception e)
            {
                this.success = false;
                this.errorMessage = "С сервера получен неверный ответ!";
            }
        }

        super.onPostExecute(aVoid);
        return;
    }
}
