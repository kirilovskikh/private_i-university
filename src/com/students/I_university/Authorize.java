package com.students.I_university;

import android.os.AsyncTask;

/**
 * Created with IntelliJ IDEA.
 * User: Иконников
 * Date: 09.08.13
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class Authorize extends AsyncTask <Void, Void, Void> {

    private String protocol = "http://";
    private String url;
    private String loginPath = "/login/token.php?";
    private String token;
    private String user;
    private String password;

    //Для подключения к тестовому серверу указываем пустую строку в первом параметре
    public Authorize(String server, String user, String password){
        if (!server.isEmpty())
            this.url = server;
        else this.url = "university.shiva.vps-private.net";

        if (!user.isEmpty())
            this.user = user;
        else this.user = "testuser";

        if (!password.isEmpty())
            this.password = password;
        else this.password = "Testuser1.";

    };

    public String getToken(){

        return "";
    };

    //метод, выполняющий обращение к серверу
    protected Void doInBackground(Void... voids){
        return null;
    };
}
