package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar_Desk on 03/01/2016.
 */
public class MessageLDFService implements Serializable {

    private List<DiffusionList> selectedLDFList;
    private List<DiffusionList> LDFList;
    private DiffusionList current_ldf;
    private boolean startedService = false;

    public void onStart(SessionWsService AppCtx) {
        if (!startedService) {
            current_ldf = null;
            selectedLDFList = new ArrayList<>();
            LDFList = new ArrayList<>();
            startedService = true;
        }
    }

    public List<DiffusionList> getSelectedLDFList() {
        return selectedLDFList;
    }

    public void setSelectedLDFList(List<DiffusionList> selectedLDFList) {
        this.selectedLDFList = selectedLDFList;
    }

    public List<DiffusionList> getLDFList() {
        return LDFList;
    }

    public void setLDFList(List<DiffusionList> LDFList) {
        this.LDFList = LDFList;
    }

    public DiffusionList getCurrent_ldf() {
        return current_ldf;
    }

    public void setCurrent_ldf(DiffusionList current_ldf) {
        this.current_ldf = current_ldf;
    }

    public boolean isStartedService() {
        return startedService;
    }

    public void setStartedService(boolean startedService) {
        this.startedService = startedService;
    }
}
