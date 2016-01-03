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


    Map<String, Boolean> numSelectedLdf; // numLDF and isSelected
    private List<DiffusionList> LDFList;
    private boolean startedService = false;
    private MessageMail message;

    public MessageLDFService() {

    }
    public void onStart(SessionWsService AppCtx) {
        if (!startedService) {
            numSelectedLdf = new HashMap<>();
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

    public MessageMail getMessage() {
        return message;
    }

    public void setMessage(MessageMail message) {
        this.message = message;
    }

    public Map<String, Boolean> getNumSelectedLdf() {
        return numSelectedLdf;
    }

    public void setNumSelectedLdf(Map<String, Boolean> numSelectedLdf) {
        this.numSelectedLdf = numSelectedLdf;
    }
}
