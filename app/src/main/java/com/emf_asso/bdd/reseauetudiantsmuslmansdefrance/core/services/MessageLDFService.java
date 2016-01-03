package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionList;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.MessageMail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Omar_Desk on 03/01/2016.
 */
public class MessageLDFService implements Serializable {


    Map<Integer, Boolean> PosSelectedLdf; // Position and isSelected
    private List<DiffusionList> LDFList;
    private boolean startedService = false;
    private MessageMail message;

    public MessageLDFService() {

    }
    public void onStart(SessionWsService AppCtx) {
        if (!startedService) {
            PosSelectedLdf = new HashMap<>();
            LDFList = new ArrayList<>();
            startedService = true;
            message = new MessageMail();
        }
    }

    public List<DiffusionList> getLDFList() {
        return LDFList;
    }

    public void setLDFList(List<DiffusionList> LDFList) {
        this.LDFList = LDFList;
    }


    public boolean isStartedService() {
        return startedService;
    }

    public void setStartedService(boolean startedService) {
        this.startedService = startedService;
    }

    public void setSelectedLDFByPosition(int pos) {

    }

    public Map<Integer, Boolean> getPosSelectedLdf() {
        return PosSelectedLdf;
    }

    public void setPosSelectedLdf(Map<Integer, Boolean> posSelectedLdf) {
        PosSelectedLdf = posSelectedLdf;
    }

    public MessageMail getMessage() {
        return message;
    }

    public void setMessage(MessageMail message) {
        this.message = message;
    }
}
