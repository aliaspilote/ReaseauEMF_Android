package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;

import org.joda.time.DateTime;
import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class SessionWsService implements Serializable {

    private UserMember userMember;
    private DateTime timeOut;
    private String token;

    public SessionWsService() {
    }

    public SessionWsService(JSONObject jsonObject) {
        token = (jsonObject.get("token")).toString();
        userMember = new UserMember();
        userMember.setEmail((jsonObject.get("mail")).toString());
    }


    public UserMember getUserMember() {
        return userMember;
    }

    public void setUserMember(UserMember userMember) {
        this.userMember = userMember;
    }

    public DateTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(DateTime timeOut) {
        this.timeOut = timeOut;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


