package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar_Desk on 24/11/2015.
 */
public class DataContext {

    public static List<Involvement> involvementsList;
    public static List<Section> sectionList;
    public static List<DegreeStudy> degreeStudyList;
    public static List<Discipline> disciplineList;
    public static List<Skill> skillList;
    public static List<Curriculum> cursusList;

    public static void setInvolvementsList(JSONObject obj) {
        String count = obj.get("count").toString();
        int c = Integer.parseInt(count);

        involvementsList = new ArrayList<>();
        Object InvolJSON = obj.get("list");
        Object invol = "";
        for (int i = 1; i <= c; i++) {
            invol = ((JSONObject) InvolJSON).get(Integer.toString(i));
            involvementsList.add(
                    new Involvement(
                            ((JSONObject) invol).get("label").toString(),
                            "",
                            ((JSONObject) invol).get("involvement_id").toString())
            );
        }
    }
}
