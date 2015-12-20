package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.util.List;

/**
 * Created by Omar_Desk on 20/12/2015.
 */
public class DiffusionList {

    String label;
    String id;
    List<DiffusionCriteria> diffusionCriterias;

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

    public List<DiffusionCriteria> getDiffusionCriterias() {
        return diffusionCriterias;
    }

    public void setDiffusionCriterias(List<DiffusionCriteria> diffusionCriterias) {
        this.diffusionCriterias = diffusionCriterias;
    }
}
