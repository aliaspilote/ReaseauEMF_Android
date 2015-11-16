package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.io.Serializable;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class Skill implements Serializable {

    private String label;
    private String description;
    private int skill_id;

    public Skill() {

        label = "";
        description = "";
        skill_id = 0;
    }

    public Skill(String label, String description, int skill_id) {
        setLabel(label);
        setDescription(description);
        setSkill_id(skill_id);
    }

    public Skill(String label, String description) {
        setLabel(label);
        setDescription(description);
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

    public int getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(int skill_id) {
        this.skill_id = skill_id;
    }
}
