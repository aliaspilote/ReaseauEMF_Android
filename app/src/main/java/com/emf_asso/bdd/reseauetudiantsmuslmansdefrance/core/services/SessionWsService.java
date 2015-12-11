package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;

import org.joda.time.DateTime;
import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class SessionWsService implements Serializable {

    private UserMember userMember;
    private DateTime timeOut;
    private String token;
    private Boolean isConnected;
    private DataContext dataContext = new DataContext();
    private ProcessInscriptionService ServiceProcessInscription = new ProcessInscriptionService();

    public SessionWsService() {
        setIsConnected(false);
    }


    public void setSession(JSONObject jsonObject) {
        token = (jsonObject.get("token")).toString();
        userMember = new UserMember();
        userMember.setEmail((jsonObject.get("mail")).toString());
        setIsConnected(true);
    }

    public void setUser_From_DB(JSONObject jsonObject) {

        //{"action":"get_user","result":"true","
        // data":{"action":"get_user","result":"true",
        //      "user_info":{"name":null,"firstname":null,"mail":null,"zip_code":null,"city":null,"section":null,"phone":null,"involvement":null,"birth_date":null,"registration_date":null},
        //      "user_right":"simple_user",
        //      "user_cursus_list":{"4":{"id":"2022","label":"le cursus","start_date":"2015-12-17","end_date":"2015-12-15","establishment":"UTBM","city":"Belfort","zip_code":"90000","discipline_id":"9","degree_id":"5"}},
        //      "user_skills_list":{"1":{"skill_id":"5"},"2":{"skill_id":"13"},"3":{"skill_id":"19"},"4":{"skill_id":"24"}},
        //      "user_cursus_count":2,
        //      "user_skills_count":4}}
    }

    public ProcessInscriptionService getServiceProcessInscription() {
        return ServiceProcessInscription;
    }

    public void setServiceProcessInscription(ProcessInscriptionService serviceProcessInscription) {
        ServiceProcessInscription = serviceProcessInscription;
    }

    public UserMember getUserMember() {
        return userMember;
    }

    public void setUserMember(UserMember userMember) {
        this.userMember = userMember;
    }

    public DateTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(DateTime timeOut) {
        this.timeOut = timeOut;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(Boolean isConnected) {
        this.isConnected = isConnected;
    }

    public DataContext getDataContext() {
        return dataContext;
    }

    public void setDataContext(DataContext dataContext) {
        this.dataContext = dataContext;
    }

}


