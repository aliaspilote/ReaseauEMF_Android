package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.io.Serializable;

/**
 * Created by Omar_Desk on 11/11/2015.
 */
public class DegreeStudy implements Serializable {

    private String label;
    private int order;
    private String degree_id;

    public DegreeStudy() {
        label = "";
        order = 0;
        degree_id = "0";
    }

    public DegreeStudy(String label, int order, String degree_id) {
        setLabel(label);
        setDegree_id(degree_id);
        setOrder(order);
    }

    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDegree_id() {
        return degree_id;
    }

    public void setDegree_id(String degree_id) {
        this.degree_id = degree_id;
    }
}
