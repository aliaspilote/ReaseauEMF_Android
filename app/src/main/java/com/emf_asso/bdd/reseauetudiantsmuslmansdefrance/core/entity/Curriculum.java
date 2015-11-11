package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class Curriculum implements Serializable {

    private String label;
    private Date start_date;
    private Date end_date;
    private Discipline discipline;
    private String establishment;
    private int zip_code;
    private DegreeStudy degree;

    public Curriculum() {
    }

    public Curriculum(String label, Date start_date, Date end_date, Discipline discipline) {
        setLabel(label);
        setStart_date(start_date);
        setEnd_date(end_date);
        setDiscipline(discipline);
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
}
