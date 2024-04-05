package com.example.wordquizclient.model;

import java.util.Map;

public class User {
    private String UserName;

    private String UserPass;

    //private final int UserScore;

    public User(String userArg, String passArg){
        UserName = userArg;
        UserPass = passArg;
        //UserScore = 0;
    }

    public User(Map<String, Object> argMap){
        UserName = (String) argMap.get("UserName");
        UserPass = (String) argMap.get("UserPass");
        //UserScore = Integer.parseInt((String) argMap.get("UserScore"));
    }

    public String getUserName(){
        return this.UserName;
    }

    public String getUserPass(){
        return this.UserPass;
    }

    public void setUserName(String userArg){
        UserName = userArg;
    }

    public void setUserPass(String passArg){
        UserPass = passArg;
    }

}
