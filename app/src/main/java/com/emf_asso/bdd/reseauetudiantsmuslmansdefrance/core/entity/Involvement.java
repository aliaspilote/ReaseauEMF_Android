package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.io.Serializable;

/**
 * Created by Omar_Desk on 13/11/2015.
 */
public class Involvement implements Serializable {

    private String label;
    private String description;
    private String involvement_id;

    public Involvement() {

        label = "";
        description = "";
        involvement_id = "";
    }

    public Involvement(String lbl, String desc, String id) {
        setLabel(lbl);
        setDescription(desc);
        setInvolvement_id(id);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String involvement) {
        this.description = involvement;
    }

    public String getInvolvement_id() {
        return involvement_id;
    }

    public void setInvolvement_id(String involvement_id) {
        this.involvement_id = involvement_id;
    }
}
