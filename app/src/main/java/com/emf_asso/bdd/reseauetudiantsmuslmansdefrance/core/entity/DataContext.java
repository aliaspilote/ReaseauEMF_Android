package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar_Desk on 24/11/2015.
 */
public class DataContext implements Serializable {

    public List<Involvement> involvementsList = new ArrayList<>();
    public List<Section> sectionList = new ArrayList<>();
    public List<DegreeStudy> degreeStudyList = new ArrayList<>();
    public List<Discipline> disciplineList = new ArrayList<>();
    public List<Skill> skillList = new ArrayList<>();
    public List<Curriculum> cursusList = new ArrayList<>();

    public DataContext() {

    }

    public void setDisciplineList(JSONObject obj) {
        String count = obj.get("count").toString();
        int c = Integer.parseInt(count);

        disciplineList = new ArrayList<>();
        Object resultJSON = obj.get("list");
        Object item = "";
        for (int i = 1; i <= c; i++) {
            item = ((JSONObject) resultJSON).get(Integer.toString(i));
            disciplineList.add(
                    new Discipline(
                            ((JSONObject) item).get("label").toString(),
                            ((JSONObject) item).get("description").toString(),
                            Integer.parseInt(((JSONObject) item).get("discipline_id").toString()))
            );
        }
    }

    public void setDegreeStudyList(JSONObject obj) {
        String count = obj.get("count").toString();
        int c = Integer.parseInt(count);

        degreeStudyList = new ArrayList<>();
        Object resultJSON = obj.get("list");
        Object item = "";
        for (int i = 1; i <= c; i++) {
            item = ((JSONObject) resultJSON).get(Integer.toString(i));
            degreeStudyList.add(
                    new DegreeStudy(
                            ((JSONObject) item).get("label").toString(),
                            1,
                            //Integer.parseInt(((JSONObject) item).get("order").toString()),
                            Integer.parseInt(((JSONObject) item).get("degree_id").toString()))
            );
        }
    }

    public void setInvolvementsList(JSONObject obj) {
        String count = obj.get("count").toString();
        int c = Integer.parseInt(count);

        involvementsList = new ArrayList<>();
        Object resultJSON = obj.get("list");
        Object item = "";
        for (int i = 1; i <= c; i++) {
            item = ((JSONObject) resultJSON).get(Integer.toString(i));
            involvementsList.add(
                    new Involvement(
                            ((JSONObject) item).get("label").toString(),
                            ((JSONObject) item).get("description").toString(),
                            ((JSONObject) item).get("involvement_id").toString())
            );
        }
    }

    public void setSkillList(JSONObject obj) {
        String count = obj.get("count").toString();
        int c = Integer.parseInt(count);

        skillList = new ArrayList<>();
        Object resultJSON = obj.get("list");
        Object item = "";
        for (int i = 1; i <= c; i++) {
            item = ((JSONObject) resultJSON).get(Integer.toString(i));
            skillList.add(
                    new Skill(
                            ((JSONObject) item).get("label").toString(),
                            ((JSONObject) item).get("description").toString(),
                            Integer.parseInt(((JSONObject) item).get("skill_id").toString()))
            );
        }
    }

    public void setSectionList(JSONObject obj) {
        String count = obj.get("count").toString();
        int c = Integer.parseInt(count);

        sectionList = new ArrayList<>();
        Object resultJSON = obj.get("list");
        Object item = "";
        for (int i = 1; i <= c; i++) {
            item = ((JSONObject) resultJSON).get(Integer.toString(i));
            sectionList.add(
                    new Section(
                            ((JSONObject) item).get("label").toString(),
                            Integer.parseInt(((JSONObject) item).get("section_id").toString()),
                            Boolean.parseBoolean(((JSONObject) item).get("isActive").toString()))
            );
        }
    }
}
