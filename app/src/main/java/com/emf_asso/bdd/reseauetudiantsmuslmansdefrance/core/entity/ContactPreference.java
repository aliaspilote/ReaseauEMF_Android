package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

/**
 * Created by Omar on 11/11/2015.
 */
public class ContactPreference {

    private Boolean jobs_offers;
    private Boolean city_activities;
    private Boolean national_activities;
    private Boolean project_volontary;

    public ContactPreference() {

        jobs_offers = false;
        city_activities = false;
        national_activities = false;
        project_volontary = false;
    }

    public ContactPreference(Boolean jobs_offers, Boolean city_activities, Boolean national_activities, Boolean project_volontary) {
        setJobs_offers(jobs_offers);
        setCity_activities(city_activities);
        setNational_activities(national_activities);
        setProject_volontary(project_volontary);
    }

    public Boolean getJobs_offers() {
        return jobs_offers;
    }

    public void setJobs_offers(Boolean jobs_offers) {
        this.jobs_offers = jobs_offers;
    }

    public Boolean getCity_activities() {
        return city_activities;
    }

    public void setCity_activities(Boolean city_activities) {
        this.city_activities = city_activities;
    }

    public Boolean getNational_activities() {
        return national_activities;
    }

    public void setNational_activities(Boolean national_activities) {
        this.national_activities = national_activities;
    }

    public Boolean getProject_volontary() {
        return project_volontary;
    }

    public void setProject_volontary(Boolean project_volontary) {
        this.project_volontary = project_volontary;
    }
}
