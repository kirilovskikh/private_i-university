package com.students.I_university.MoodleRequest;
import android.content.Context;

/**
 * Created by Akella on 16.08.13.
 */
public class MoodleRequestSendMessage extends MoodleRequest {

    private String function = "moodle_message_send_instant_messages";
    public String foo;

    public MoodleRequestSendMessage(Context context, String token, String toUserId, String messageText)
    {
        super(context);

        this.addParam("wstoken", token);
        this.addParam("wsfunction", function);
        this.addParam("messages[0][touserid]", toUserId);
        this.addParam("messages[0][text]", messageText);
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        if(this.isSuccess()) foo = "Fuck your brain!";
        return;
    }
}
