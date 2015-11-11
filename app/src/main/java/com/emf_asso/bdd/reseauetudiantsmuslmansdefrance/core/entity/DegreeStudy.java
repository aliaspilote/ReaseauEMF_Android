package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

/**
 * Created by Omar_Desk on 11/11/2015.
 */
public class DegreeStudy {

    private String label;
    private int order;
    private int degree_id;

    public DegreeStudy() {
    }

    public DegreeStudy(String label, int order, int degree_id) {
        setLabel(label);
        setDegree_id(degree_id);
        setOrder(order);
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

    public int getDegree_id() {
        return degree_id;
    }

    public void setDegree_id(int degree_id) {
        this.degree_id = degree_id;
    }
}
