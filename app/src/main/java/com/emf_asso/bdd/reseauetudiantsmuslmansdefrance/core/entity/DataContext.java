package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar_Desk on 24/11/2015.
 */
public class DataContext implements Serializable {

    public static String dateDisplayFormat = "dd/MM/yyyy";
    public static String dateMysqlFormat = "yyyy-MM-dd";
    public List<Involvement> involvementsList = new ArrayList<>();
    public List<Section> sectionList = new ArrayList<>();
    public List<DegreeStudy> degreeStudyList = new ArrayList<>();
    public List<Discipline> disciplineList = new ArrayList<>();
    public List<Skill> skillList = new ArrayList<>();
    public List<Curriculum> cursusList = new ArrayList<>();
    public List<CriteriaType> criteriaTypeList = new ArrayList<>();

    public Boolean involvementDL_OK = false;
    public Boolean sectionDL_OK = false;
    public Boolean degreeStudyDL_OK = false;
    public Boolean disciplineDL_OK = false;
    public Boolean skillLDL_OK = false;

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
                            ((JSONObject) item).get("discipline_id").toString())
            );
        }
        if (disciplineList.size() > 0)
            disciplineDL_OK = true;
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
                            ((JSONObject) item).get("degree_id").toString())
            );
        }
        if (degreeStudyList.size() > 0)
            degreeStudyDL_OK = true;
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
        if (involvementsList.size() > 0)
            involvementDL_OK = true;
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
                            ((JSONObject) item).get("skill_id").toString())
            );
        }
        if (skillList.size() > 0)
            skillLDL_OK = true;
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
                            ((JSONObject) item).get("section_id").toString(),
                            Boolean.parseBoolean(((JSONObject) item).get("isActive").toString()))
            );
        }
        if (sectionList.size() > 0)
            sectionDL_OK = true;
    }

    public Section getSectionById(String id) {
        for (Section s : sectionList) {
            if (id.contentEquals(s.getSection_id()))
                return s;
        }
        return null;
    }

    public Involvement getInvolvementById(String id) {
        for (Involvement v : involvementsList) {
            if (id.contentEquals(v.getInvolvement_id()))
                return v;
        }
        return null;
    }

    public DegreeStudy getDegreeStudyById(String id) {
        for (DegreeStudy ds : degreeStudyList) {
            if (id.contentEquals(ds.getDegree_id()))
                return ds;
        }
        return null;
    }

    public Discipline getDisciplineById(String id) {
        for (Discipline d : disciplineList) {
            if (id.contentEquals(d.getDiscipline_id()))
                return d;
        }
        return null;
    }

    public Skill getSkillById(String id) {
        for (Skill s : skillList) {
            if (id.contentEquals(s.getSkill_id()))
                return s;
        }
        return null;
    }

    public Section getSectionByLabel(String label) {
        for (Section s : sectionList) {
            if (label.contentEquals(s.getLabel()))
                return s;
        }
        return null;
    }

    public Involvement getInvolvementByLabel(String label) {
        for (Involvement v : involvementsList) {
            if (label.contentEquals(v.getLabel()))
                return v;
        }
        return null;
    }

    public DegreeStudy getDegreeStudyByLabel(String label) {
        for (DegreeStudy ds : degreeStudyList) {
            if (label.contentEquals(ds.getLabel()))
                return ds;
        }
        return null;
    }

    public Discipline getDisciplineByLabel(String label) {
        for (Discipline d : disciplineList) {
            if (label.contentEquals(d.getLabel()))
                return d;
        }
        return null;
    }

    public int getPositionSectionById(String id) {
        int pos = 0;
        for (Section s : sectionList) {
            if (id.contentEquals(s.getSection_id()))
                return pos;
            pos++;
        }
        return 0;
    }

    public int getPositionDisciplineById(String id) {
        int pos = 0;
        for (Discipline d : disciplineList) {
            if (id.contentEquals(d.getDiscipline_id()))
                return pos;
            pos++;
        }
        return 0;
    }

    public int getPositionInvolvementById(String id) {
        int pos = 0;
        for (Involvement v : involvementsList) {
            if (id.contentEquals(v.getInvolvement_id()))
                return pos;
            pos++;
        }
        return 0;
    }

    public int getPositionDegreeStudyById(String id) {
        int pos = 0;
        for (DegreeStudy ds : degreeStudyList) {
            if (id.contentEquals(ds.getDegree_id()))
                return pos;
            pos++;
        }
        return 0;
    }
    

}
