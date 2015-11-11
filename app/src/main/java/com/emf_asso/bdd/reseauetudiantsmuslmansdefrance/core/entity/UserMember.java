package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import android.provider.ContactsContract;

import java.util.Date;
import java.util.List;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class UserMember {
    private String name;
    private String firstname;
    private String civility;
    private String hashed_pwd;
    private String right;
    private String address;
    private String zip_code;
    private String city;
    private ContactsContract.CommonDataKinds.Phone phone;
    private ContactsContract.CommonDataKinds.Email email;
    private String involvement;
    private Discipline dicipline;
    private Date birth_date;
    private String numero;
    private Date registration_date;
    private Date last_update_date;
    private List<Skill> skills;
    // private String statut=0;
    private Section section;
}
