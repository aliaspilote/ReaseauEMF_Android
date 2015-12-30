package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 27/12/2015.
 */
public class LdfService implements Serializable {

    private List<DiffusionList> ldfList;
    private DiffusionList current_ldf;
    private int current_position;
    private boolean startedService = false;

    public void onStart() {
        if (!startedService) {
            current_position = 0;
            current_ldf = null;
            ldfList = new ArrayList<>();
            startedService = true;
        }
    }

    public List<DiffusionList> getLdfList() {
        return ldfList;
    }

    public void setLdfList(List<DiffusionList> ldfList) {
        this.ldfList = ldfList;
    }

    public DiffusionList getCurrent_ldf() {
        return current_ldf;
    }

    public void setCurrent_ldf(DiffusionList current_ldf) {
        this.current_ldf = current_ldf;
    }

    public int getCurrent_position() {
        return current_position;
    }

    public void setCurrent_position(int current_position) {
        this.current_position = current_position;
    }

    public void add_ldf(DiffusionList ldf) {
        ldfList.add(ldf);
    }

    public void remove_ldf(DiffusionList ldf) {
        if (ldfList.contains(ldf))
            ldfList.remove(ldf);
    }

    public void update_ldf(DiffusionList newldf) {
        if (ldfList.contains(newldf))
            ldfList.set(ldfList.indexOf(newldf), newldf);
        else
            ldfList.add(newldf);
    }

    public void update_ldf(int position, DiffusionList newldf) {
        ldfList.set(position, newldf);
    }

    public DiffusionList get_ldf(int position) {
        return ldfList.get(position);
    }

    public DiffusionCriteria get_criteria_currentldf(int pos) {
        return current_ldf.DiffusionCriteriaListViewValuesArr.get(pos);
    }

    public void add_criteria_currentldf(DiffusionCriteria criteria) {
        current_ldf.DiffusionCriteriaListViewValuesArr.add(criteria);
    }

    public void remove_criteria_currentldf(DiffusionCriteria criteria) {
        current_ldf.DiffusionCriteriaListViewValuesArr.remove(criteria);
    }

    public void update_criteria_currentldf(int position, DiffusionCriteria criteria) {
        current_ldf.DiffusionCriteriaListViewValuesArr.set(position, criteria);
    }

    public void updateVal_criteria_currentldf(int position, Object criteriaVal) {
        DiffusionCriteria df = get_criteria_currentldf(position);
        df.setValue(criteriaVal);
        update_criteria_currentldf(position, df);
    }

    public int size_ldf() {
        return ldfList.size();
    }


}
