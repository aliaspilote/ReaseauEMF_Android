package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import org.joda.time.DateTime;
import org.json.simple.JSONObject;

/**
 * Created by Omar_Desk on 02/11/2015.
 */
public class HttpReponse {
    public String Action;
    JSONObject Resultat;
    Boolean Succes = false;
    DateTime DataReponse;
    String ExceptionText = "null";

    public HttpReponse() {

    }

    public HttpReponse(Boolean isSucces, String Error) {
        Succes = isSucces;
        ExceptionText = Error;
    }

    public HttpReponse(JSONObject JSONobj, Boolean isSucces, String actionName, DateTime theDate, String Exp) {
        Resultat = JSONobj;
        Succes = isSucces;
        Action = actionName;
        DataReponse = theDate;
        ExceptionText = Exp;
    }

    public void setHttpReponse(JSONObject JSONobj, Boolean isSucces, String actionName, DateTime theDate, String Exp) {
        Resultat = JSONobj;
        Succes = isSucces;
        Action = actionName;
        DataReponse = theDate;
        ExceptionText = Exp;
    }

    public JSONObject getResultat() {
        return Resultat;
    }

    public void setResultat(JSONObject resultat) {
        Resultat = resultat;
    }

    public Boolean getSucces() {
        if (Succes == null)
            return false;
        else
            return Succes;
    }

    public void setSucces(Boolean succes) {
        Succes = succes;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public DateTime getDataReponse() {
        return DataReponse;
    }

    public void setDataReponse(DateTime dataReponse) {
        DataReponse = dataReponse;
    }

    public String getExceptionText() {
        return ExceptionText;
    }

    public void setExceptionText(String exceptionText) {
        ExceptionText = exceptionText;
    }
}
