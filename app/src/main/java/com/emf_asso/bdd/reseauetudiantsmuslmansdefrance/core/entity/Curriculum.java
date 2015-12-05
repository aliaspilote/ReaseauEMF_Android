package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class Curriculum implements Serializable {

    public String id;
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    private String label;
    private Date start_date;
    private Date end_date;
    private Discipline discipline;
    private String establishment;
    private int zip_code;
    private DegreeStudy degree;
    private String City;

    public Curriculum() {
        label = "";
    }

    public Curriculum(String label) {
        this((new DateTime()).toString(), label, new Date(), new Date(),
                new Discipline("", "", 1)
                , "", "",
                new DegreeStudy("", 1, 1));
    }

    public Curriculum(String label, Date start_date, Date end_date,
                      Discipline discipline, String etablissement,
                      String City, DegreeStudy DegreeStudy) {
        setLabel(label);
        setStart_date(start_date);
        setEnd_date(end_date);
        setDiscipline(discipline);
        setCity(City);
        setEstablishment(etablissement);
        setDegree(DegreeStudy);
    }

    public Curriculum(String id, String label, Date start_date, Date end_date,
                      Discipline discipline, String etablissement,
                      String City, DegreeStudy DegreeStudy) {
        setId(id);
        setLabel(label);
        setStart_date(start_date);
        setEnd_date(end_date);
        setDiscipline(discipline);
        setCity(City);
        setEstablishment(etablissement);
        setDegree(DegreeStudy);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public String getEstablishment() {
        return establishment;
    }

    public void setEstablishment(String establishment) {
        this.establishment = establishment;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public DegreeStudy getDegree() {
        return degree;
    }

    public void setDegree(DegreeStudy degree) {
        this.degree = degree;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return getLabel();
    }
}
