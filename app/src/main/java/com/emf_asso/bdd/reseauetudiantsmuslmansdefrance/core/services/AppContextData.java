package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

/**
 * Created by Omar_Desk on 18/11/2015.
 */
public class AppContextData {
    private SessionWsService userSession;

    public SessionWsService getUserSession() {
        return userSession;
    }

    public void setUserSession(SessionWsService userSession) {
        this.userSession = userSession;
    }

}
