package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.io.Serializable;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class Discipline implements Serializable {

    private String label;
    private String description;
    private String discipline_id;

    public Discipline() {

        label = "";
        description = "";
        discipline_id = "0";
    }

    public Discipline(String label, String description, String discipline_id) {
        setLabel(label);
        setDescription(description);
        setDiscipline_id(discipline_id);
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscipline_id() {
        return discipline_id;
    }

    public void setDiscipline_id(String discipline_id) {
        this.discipline_id = discipline_id;
    }
}
