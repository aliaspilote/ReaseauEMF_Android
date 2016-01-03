package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionList;

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
    private DiffusionList current_ldf;
    private boolean startedService = false;

    public void onStart(SessionWsService AppCtx) {
        if (!startedService) {
            current_ldf = null;
            PosSelectedLdf = new HashMap<>();
            LDFList = new ArrayList<>();
            startedService = true;
        }
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

    public void setSelectedLDFByPosition(int pos) {

    }
}
