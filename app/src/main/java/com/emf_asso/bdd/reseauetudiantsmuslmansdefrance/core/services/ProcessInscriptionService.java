package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.ContactPreference;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Discipline;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Inscription;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Involvement;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Section;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Omar_Desk on 11/11/2015.
 */
public class ProcessInscriptionService implements Serializable {

    private Inscription inscription;
    private List<Inscription> multi_inscriptions;
    private String[] errors = new String[5];
    private Boolean onGoingInscr = false;


    public void onStart() {
        if (!onGoingInscr) {
            inscription = new Inscription();
            multi_inscriptions = new ArrayList<>();
            onGoingInscr = true;
        }
    }

    public boolean submit() {
        return submit(inscription);
    }

    public boolean submit(Inscription inscription) {

        onGoingInscr = false;
        return true;
    }

    public boolean submit_all() {

        return false;
    }

    public boolean validated_step(int k, Inscription inscription) {
        switch (k) {
            case 1:
                return validated_screen1(inscription);
            case 2:
                return validated_screen2(inscription);
            case 3:
                return validated_screen3(inscription);
            default:
                return false;
        }
    }

    public void set_data_inscription1(Inscription inscription, String Email, String Password, String Password2) {
        inscription.getUser().setEmail(Email);
        inscription.getUser().setHashed_pwd((Password));
        inscription.getUser().setHashed_pwd2((Password2));
    }

    public void set_data_inscription2(Inscription inscription, String Name, String Firstname, String ZipCode, String City, String Phone, Date BirthDay) {
        inscription.getUser().setName(Name);
        inscription.getUser().setFirstname(Firstname);
        inscription.getUser().setZip_code(ZipCode);
        inscription.getUser().setPhone(Phone);
        inscription.getUser().setCity(City);
        inscription.getUser().setBirth_date(BirthDay);
    }

    public void set_data_inscription3(Inscription inscription,
                                      Involvement involvement,
                                      Section section,
                                      ContactPreference contactPreference,
                                      Discipline discipline) {
        inscription.getUser().setInvolvement(involvement);
        inscription.getUser().setSection(section);
        inscription.getUser().setStatus(contactPreference);
        inscription.getUser().setInvolvement(involvement);
        inscription.getUser().setSection(section);
        inscription.getUser().setDicipline(discipline);
    }

    public void set_data_inscription4(Inscription inscription, List<Skill> skills) {
        inscription.getUser().setSkills(skills);
    }

    public void set_data_inscription5(Inscription inscription, List<Curriculum> cursus) {
        if (cursus != null)
            if (cursus.size() > 0)
                inscription.getUser().setCursuses(cursus);
    }

    public Boolean validated_screen1(Inscription inscription) {
        boolean bool = true;
        errors[1] = "";
        if (!CheckContentService.checkEmail(inscription.getUser().getEmail().toString())) {
            bool = false;
            errors[1] += Messages.error_email;
        }
        if (inscription.getUser().getHashed_pwd().length() < 6) {
            bool = false;
            errors[1] += Messages.error_shortpassword;
        } else if (!inscription.getUser().getHashed_pwd()
                .equals(inscription.getUser().getHashed_pwd2())) {
            bool = false;
            errors[1] += Messages.error_password;
        }
        return bool;
    }

    public Boolean validated_screen2(Inscription inscription) {
        boolean bool = true;
        errors[2] = "";
        if (!CheckContentService.checkString(inscription.getUser().getName())) {
            bool = false;
            errors[2] += Messages.error_name;
        }
        if (!CheckContentService.checkString(inscription.getUser().getFirstname())) {
            bool = false;
            errors[2] += Messages.error_firstname;
        }
        if (!CheckContentService.checkZipCode(inscription.getUser().getZip_code())) {
            bool = false;
            errors[2] += Messages.error_zipcode;
        }
        if (!CheckContentService.checkPhone(inscription.getUser().getPhone())) {
            bool = false;
            errors[2] += Messages.error_phone;
        }
        if (inscription.getUser().getCity().isEmpty()) {
            bool = false;
            errors[2] += Messages.error_city;
        }
        return bool;
    }

    public Boolean validated_screen3(Inscription inscription) {
        boolean bool = true;
        errors[3] = "";
        if (!(inscription.getUser().getSection() != null)) {
            bool = false;
            errors[3] += Messages.error_section;
        }
        if (!(inscription.getUser().getInvolvement() != null)) {
            bool = false;
            errors[3] += Messages.error_involvement;
        }
        return bool;
    }

    public Boolean validated_screen4(Inscription inscription) {
        boolean bool = true;
        errors[4] = "";
        return bool;
    }

    public Boolean validated_screen5(Inscription inscription) {
        boolean bool = true;
        errors[5] = "";
        return bool;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public List<Inscription> getMulti_inscriptions() {
        return multi_inscriptions;
    }

    public void setMulti_inscriptions(List<Inscription> multi_inscriptions) {
        this.multi_inscriptions = multi_inscriptions;
    }

    public String getErrors(int step) {
        return errors[step];
    }
    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

    public Boolean getOnGoingInscr() {
        return onGoingInscr;
    }

    public void setOnGoingInscr(Boolean onGoingInscr) {
        this.onGoingInscr = onGoingInscr;
    }
}
