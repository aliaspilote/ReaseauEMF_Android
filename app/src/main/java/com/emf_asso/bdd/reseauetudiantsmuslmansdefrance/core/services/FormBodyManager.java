package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

/**
 * Created by Omar_Desk on 02/11/2015.
 */
public class FormBodyManager {

    public static RequestBody checkmail(String mail) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "check_mail")
                .add("mail", mail)
                .build();
        return formBody;
    }

    public static RequestBody addUser(UserMember user) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "add_user")
                .add("mail", user.getEmail() + "")
                .add("name", user.getName() + "")
                .add("firstname", user.getFirstname() + "")
                .add("civility", user.getCivility() + "")
                .add("zip_code", user.getZip_code() + "")
                .add("city", user.getCity() + "")
                .add("section", user.getSection().getLabel() + "")
                .add("dicipline", "android")
                .add("niveau", "")
                .add("involvement", user.getInvolvement().getLabel() + "")
                .add("phone", user.getPhone() + "")
                .add("birth_date", sdf.format(user.getBirth_date()) + "")
                .add("registration_date", sdf.format(user.getRegistration_date()) + "")
                .add("skills", user.getSkills().get(1).getLabel() + "")
                .add("hashed_pwd", user.getHashed_pwd() + "")
                .build();
        // .add("dicipline", user.getDicipline().getLabel() + "")
        return formBody;
    }
}
