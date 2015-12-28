package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Omar_Desk on 20/12/2015.
 */
public class DiffusionList implements Serializable {

    public ArrayList<DiffusionCriteria> DiffusionCriteriaListViewValuesArr = new ArrayList<DiffusionCriteria>();
    String label;
    String id;
    String count;

    public DiffusionList() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int size() {
        return DiffusionCriteriaListViewValuesArr.size();
    }

    public DiffusionCriteria get_criteria_byInt(int a) {
        return DiffusionCriteriaListViewValuesArr.get(a);
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}
