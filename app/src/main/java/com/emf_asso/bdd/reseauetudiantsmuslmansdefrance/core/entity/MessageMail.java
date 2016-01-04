package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.io.Serializable;

/**
 * Created by Omar_Desk on 03/01/2016.
 */
public class MessageMail implements Serializable {

    private String object;
    private String m_abstract = "";
    private String corps;
    private String note = "";
    private String sender; //emmeteur

    public String getCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getM_abstract() {
        return m_abstract;
    }

    public void setM_abstract(String m_abstract) {
        this.m_abstract = m_abstract;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
