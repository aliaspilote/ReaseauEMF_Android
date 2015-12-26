package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar_Desk on 20/12/2015.
 */
public class DiffusionCriteria {
    String criteria_Name;
    Object value;
    List<Object> values_List = new ArrayList<>();
    ArrayAdapter<Object> adapter_values_List;
    boolean spinner_type = false;

    public DiffusionCriteria() {
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

    public void setValuesTest() {
        values_List.add((Object) new String("val1"));
        values_List.add((Object) new String("val2"));
        values_List.add((Object) new String("val3"));
    }
}
