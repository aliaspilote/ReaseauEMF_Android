package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class SessionWsService implements Serializable {

    private UserMember userMember;
    private DateTime timeOut;

    public SessionWsService() {
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
}


