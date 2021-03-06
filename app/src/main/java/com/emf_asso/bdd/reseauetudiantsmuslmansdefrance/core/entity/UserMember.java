package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity;


import org.json.simple.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class UserMember implements Serializable {

    //login
    private String email;
    private String hashed_pwd;
    private String hashed_pwd2;
    //identity
    private String name;
    private String firstname;
    private String civility;
    private String zip_code;
    private String city;
    private Date birth_date;
    private String phone;
    //profile EMF
    private Section section;
    private Involvement involvement;
    private Discipline dicipline;
    private List<Curriculum> curriculum;
    private List<Skill> skills;
    private ContactPreference status;
    //application informations
    private String right;
    private int number_id;
    private Date registration_date;
    private Date last_update_date;

    private boolean isAdmin;
    private boolean isEnable;

    public UserMember() {
        name = "n/a";
        email = "n/a";
        hashed_pwd = "n/a";
        dicipline = new Discipline();
        status = new ContactPreference();
        involvement = new Involvement();
        section = new Section();
        curriculum = new ArrayList<>();
        skills = new ArrayList<Skill>();
    }

    public UserMember(String name,
                      String firstname,
                      String civility,
                      String email,
                      String city,
                      Date birth_date,
                      String phone,
                      String hashed_pwd,
                      String hashed_pwd2) {
        setName(name);
        setFirstname(firstname);
        setCivility(civility);
        setEmail(email);
        setCity(city);
        setBirth_date(birth_date);
        setPhone(phone);
        setHashed_pwd(hashed_pwd);
        setHashed_pwd2(hashed_pwd2);
    }

    public UserMember(JSONObject jsonObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy", Locale.FRANCE);
        Date BD_date = new Date();

        try {
            BD_date = dateFormatter.parse((jsonObject.get("birth_date")).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setUserfromJSON(((jsonObject.get("name"))).toString(), (jsonObject.get("firstname")).toString(),
                (jsonObject.get("civility")).toString(), (jsonObject.get("email")).toString(),
                (jsonObject.get("city")).toString(), BD_date,
                (jsonObject.get("phone")).toString(), (jsonObject.get("hashed_pwd")).toString());

    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setUserfromJSON(String name, String firstname, String civility, String email, String city, Date birth_date, String phone, String hashed_pwd) {
        setName(name);
        setFirstname(firstname);
        setCivility(civility);
        setEmail(email);
        setCity(city);
        setBirth_date(birth_date);
        setPhone(phone);
        setHashed_pwd(hashed_pwd);
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashed_pwd() {
        return hashed_pwd;
    }

    public void setHashed_pwd(String hashed_pwd) {
        this.hashed_pwd = hashed_pwd;
    }

    public String getHashed_pwd2() {
        return hashed_pwd2;
    }

    public void setHashed_pwd2(String hashed_pwd2) {
        this.hashed_pwd2 = hashed_pwd2;
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

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Involvement getInvolvement() {
        return involvement;
    }

    public void setInvolvement(Involvement involvement) {
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
        if ((right.toLowerCase()).contains("admin"))
            isAdmin = true;
        else
            isAdmin = false;
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

    public void setCurriculum(List<Curriculum> curriculum) {
        this.curriculum = curriculum;
    }

    public void setCursuses(List<Curriculum> curriculum) {
        this.curriculum = curriculum;
    }
}
