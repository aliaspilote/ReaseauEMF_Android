package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class Inscription implements Serializable {

    private UserMember user;
    private Date registration_date;
    private int count_Curriculum;

    public Inscription() {
        user = new UserMember();
        setRegistration_date(new Date());
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        user.setRegistration_date(registration_date);
        this.registration_date = registration_date;
    }

    public UserMember getUser() {
        return user;
    }

    public void setUser(UserMember user) {
        this.user = user;
    }

    public int getCount_Curriculum() {
        if (user != null)
            if (user.getCurriculum() != null)
                return user.getCurriculum().size();
        return 0;
    }

}
