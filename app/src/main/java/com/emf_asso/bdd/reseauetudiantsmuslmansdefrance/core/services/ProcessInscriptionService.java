package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Inscription;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;

import java.util.List;

/**
 * Created by Omar_Desk on 11/11/2015.
 */
public class ProcessInscriptionService {

    private Inscription inscription;
    private List<Inscription> multi_inscriptions;
    private String[] errors;

    public boolean submit() {
        return submit(inscription);
    }

    public boolean submit(Inscription inscription) {

        return false;
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
            default:
                return false;
        }

    }

    public Boolean validated_screen1(Inscription inscription) {
        boolean bool = true;
        errors[1] = "";
        if (!CheckContentService.checkEmail(inscription.getUser().getEmail().toString())) {
            bool = false;
            errors[1] += Messages.error_email;
        }
        if (inscription.getUser().getHashed_pwd().isEmpty()) {
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

}
