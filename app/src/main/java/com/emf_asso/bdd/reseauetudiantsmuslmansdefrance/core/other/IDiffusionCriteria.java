package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 24/12/2015.
 */
public interface IDiffusionCriteria {


    public List<String> choicesCriteriasList = new ArrayList<>();

    public Object type = null;

    public Object value = null;

    public Object getType();

    public void setType(Object type);

    public Object getValue();

    public void setValue(Object value);
}
