package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Omar_Desk on 20/12/2015.
 */
public class DiffusionList implements Serializable {

    public ArrayList<DiffusionCriteria> DiffusionCriteriaListViewValuesArr = new ArrayList<DiffusionCriteria>();
    String label;
    String id = "new";
    String count;

    public DiffusionList() {
    }

    public DiffusionList(String label, String count) {
        this.label = label;
        this.count = count;
    }

    public DiffusionList(String id, String label, String count) {
        this.label = label;
        this.count = count;
        this.id = id;
    }

    public String toString() {
        if (count == null)
            return label + " : -";
        else
            return label + " : " + count;
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

    public void add_criterias(DiffusionCriteria c) {
        DiffusionCriteriaListViewValuesArr.add(c);
    }
}
