package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class UserMember implements Serializable {

    //login
    private ContactsContract.CommonDataKinds.Email email;
    private String hashed_pwd;
    //identity
    private String name;
    private String firstname;
    private String civility;
    private int zip_code;
    private String city;
    private Date birth_date;
    private ContactsContract.CommonDataKinds.Phone phone;
    //profile EMF
    private Section section;
    private String involvement;
    private Discipline dicipline;
    private List<Curriculum> curriculum;
    private List<Skill> skills;
    private ContactPreference status;
    //application informations
    private String right;
    private int number_id;
    private Date registration_date;
    private Date last_update_date;

    public UserMember() {
    }

    public UserMember(String name,
                      String firstname,
                      String civility,
                      ContactsContract.CommonDataKinds.Email email,
                      String city,
                      Date birth_date,
                      ContactsContract.CommonDataKinds.Phone phone,
                      String hashed_pwd) {
        setName(name);
        setFirstname(firstname);
        setCivility(civility);
        setEmail(email);
        setCity(city);
        setBirth_date(birth_date);
        setPhone(phone);
        setHashed_pwd(hashed_pwd);
    }

    public ContactsContract.CommonDataKinds.Email getEmail() {
        return email;
    }

    public void setEmail(ContactsContract.CommonDataKinds.Email email) {
        this.email = email;
    }

    public String getHashed_pwd() {
        return hashed_pwd;
    }

    public void setHashed_pwd(String hashed_pwd) {
        this.hashed_pwd = hashed_pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCivility() {
        return civility;
    }

    public void setCivility(String civility) {
        this.civility = civility;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public ContactsContract.CommonDataKinds.Phone getPhone() {
        return phone;
    }

    public void setPhone(ContactsContract.CommonDataKinds.Phone phone) {
        this.phone = phone;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getInvolvement() {
        return involvement;
    }

    public void setInvolvement(String involvement) {
        this.involvement = involvement;
    }

    public Discipline getDicipline() {
        return dicipline;
    }

    public void setDicipline(Discipline dicipline) {
        this.dicipline = dicipline;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public ContactPreference getStatus() {
        return status;
    }

    public void setStatus(ContactPreference status) {
        this.status = status;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public int getNumber_id() {
        return number_id;
    }

    public void setNumber_id(int number_id) {
        this.number_id = number_id;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public Date getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(Date last_update_date) {
        this.last_update_date = last_update_date;
    }

    public List<Curriculum> getCurriculum() {
        return curriculum;
    }

    public void setCursuses(List<Curriculum> curriculum) {
        this.curriculum = curriculum;
    }
}
