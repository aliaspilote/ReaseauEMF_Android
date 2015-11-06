package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

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

    public static RequestBody addUser(String... args) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "check_mail")
                .add("mail", args[0])
                .build();
        return formBody;
    }
}
