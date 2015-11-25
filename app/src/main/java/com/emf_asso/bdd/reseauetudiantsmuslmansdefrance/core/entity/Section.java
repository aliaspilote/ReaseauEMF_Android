package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.io.Serializable;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class Section implements Serializable {

    private String label;
    private int section_id;
    private int[] zipCode;
    private boolean isActive;

    public Section(String lbl) {
        setLabel(lbl);
    }

    public Section() {

        label = "";
        isActive = true;
    }

    public Section(String lbl, int id, boolean active) {
        setLabel(lbl);
        setSection_id(id);
        setIsActive(active);
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

    public int[] getZipCode() {
        return zipCode;
    }

    public void setZipCode(int[] zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }
}
