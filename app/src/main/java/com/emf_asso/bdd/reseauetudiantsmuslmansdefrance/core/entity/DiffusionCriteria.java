package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Omar_Desk on 20/12/2015.
 */
public class DiffusionCriteria implements Serializable {
    String criteria_Name;
    String criteria_id;
    Object value;
    List<Object> values_List;
    ArrayAdapter<Object> adapter_values_List;
    boolean spinner_type = false;

    public DiffusionCriteria(DiffusionCriteria another) {
        this.criteria_Name = another.criteria_Name;
        this.value = another.value;
        this.values_List = another.values_List;
        this.adapter_values_List = another.adapter_values_List;
        this.criteria_id = another.criteria_id;
        this.spinner_type = another.spinner_type;
    }

    public DiffusionCriteria() {
    }

    public DiffusionCriteria(String name, String id, Boolean isSpin, List<Object> valuesList) {
        criteria_Name = name;
        criteria_id = id;
        if (isSpin) {
            spinner_type = isSpin;
            values_List = valuesList;
        }
    }

    public String getCriteria_id() {
        return criteria_id;
    }

    public void setCriteria_id(String criteria_id) {
        this.criteria_id = criteria_id;
    }

    public String getCriteria_Name() {
        return criteria_Name;
    }

    public void setCriteria_Name(String criteria_Name) {
        this.criteria_Name = criteria_Name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isSpinner_type() {
        return spinner_type;
    }

    public void setSpinner_type(boolean spinner_type) {
        this.spinner_type = spinner_type;
    }

    public List<Object> getValues_List() {
        return values_List;
    }

    public void setValues_List(List<Object> values_List) {
        this.values_List = values_List;
    }

    public ArrayAdapter<Object> getAdapter_values_List() {
        return adapter_values_List;
    }

    public void setAdapter_values_List(ArrayAdapter<Object> adapter_values_List) {
        this.adapter_values_List = adapter_values_List;
    }

    @Override
    public String toString() {
        String display = "";
        if (criteria_Name != null)
                display += criteria_Name;
        if (value != null)
                display += " : " + value.toString();
        return display;
    }
}
