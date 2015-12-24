package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 22/12/2015.
 */
public class CriteriaType {

    static ArrayAdapter<String> adapter;


    static public ArrayAdapter<String> getAdapter(Context parent) {
        List<String> l = new ArrayList<>();
        l.add("Nom");
        l.add("Domaine");
        l.add("Ville");
        adapter = new ArrayAdapter<String>(parent, android.R.layout.simple_spinner_item, l);
        return adapter;
    }

}
