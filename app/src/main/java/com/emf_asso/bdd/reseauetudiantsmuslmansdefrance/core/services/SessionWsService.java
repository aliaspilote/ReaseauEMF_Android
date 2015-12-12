package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;

import org.joda.time.DateTime;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Omar_Desk on 10/11/2015.
 */
public class SessionWsService implements Serializable {

    public static SimpleDateFormat dateFormatter_MySQL = new SimpleDateFormat(DataContext.dateMysqlFormat);
    public static SimpleDateFormat dateFormatter_Display = new SimpleDateFormat(DataContext.dateDisplayFormat, Locale.FRANCE);
    public boolean inProfileView = false;
    public boolean inProssInscrView = false;
    private UserMember userMember;
    private DateTime timeOut;
    private String token;
    private Boolean isConnected;
    private DataContext dataContext = new DataContext();
    private ProcessInscriptionService ServiceProcessInscription = new ProcessInscriptionService();

    public SessionWsService() {
        setIsConnected(false);
    }

    public void resetLocationAllView() {
        inProfileView = false;
        inProssInscrView = false;
    }

    public void setSession(JSONObject jsonObject) {
        token = (jsonObject.get("token")).toString();
        userMember = new UserMember();
        userMember.setEmail((jsonObject.get("mail")).toString());
        setIsConnected(true);
    }

    public void setUser_From_DB(JSONObject JsonResult) {

        try {
            JSONObject JsonData = (JSONObject) JsonResult.get("data");
            JSONObject info_User = (JSONObject) JsonData.get("user_info");
            JSONObject list_Skills = (JSONObject) JsonData.get("user_skills_list");
            JSONObject list_Cursus = (JSONObject) JsonData.get("user_cursus_list");
            int count_cursus = 0;
            int count_skills = 0;
            try {
                count_cursus = Integer.parseInt((JsonData.get("user_cursus_count")).toString());
                count_skills = Integer.parseInt((JsonData.get("user_skills_count")).toString());
            } catch (Exception e) {
            }
            userMember.setName((info_User.get("name")).toString());
            userMember.setFirstname((info_User.get("firstname")).toString());
            userMember.setZip_code((info_User.get("zip_code")).toString());
            userMember.setCity((info_User.get("city")).toString());
            userMember.setPhone((info_User.get("phone")).toString());
            userMember.setPhone((info_User.get("phone")).toString());
            userMember.setSection(dataContext.getSectionById((info_User.get("section")).toString()));
            userMember.setInvolvement(dataContext.getInvolvementById((info_User.get("involvement")).toString()));
            try {
                userMember.setBirth_date(dateFormatter_MySQL.parse((info_User.get("birth_date")).toString()));
                userMember.setRegistration_date(dateFormatter_MySQL.parse((info_User.get("registration_date")).toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (count_cursus > 0) {
                    Curriculum cursusItem;
                    for (int i = 1; i <= count_cursus; i++) {
                        JSONObject cursusJson = (JSONObject) list_Cursus.get(i + "");
                        cursusItem = new Curriculum();
                        cursusItem.setId(cursusJson.get("id").toString());
                        cursusItem.setLabel(cursusJson.get("label").toString());
                        cursusItem.setEstablishment(cursusJson.get("establishment").toString());
                        cursusItem.setCity(cursusJson.get("city").toString());
                        cursusItem.setZip_code(Integer.parseInt(cursusJson.get("zip_code").toString()));
                        cursusItem.setDiscipline(dataContext.getDisciplineById(cursusJson.get("discipline_id").toString()));
                        cursusItem.setDegree(dataContext.getDegreeStudyById(cursusJson.get("degree_id").toString()));
                        try {
                            cursusItem.setStart_date(dateFormatter_MySQL.parse(cursusJson.get("start_date").toString()));
                            cursusItem.setEnd_date(dateFormatter_MySQL.parse(cursusJson.get("end_date").toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        userMember.getCurriculum().add(cursusItem);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (count_skills > 0) {
                    for (int i = 1; i <= count_cursus; i++) {
                        JSONObject skillsJson = (JSONObject) list_Skills.get(i + "");
                        userMember.getSkills().add(dataContext.getSkillById(skillsJson.get("skill_id").toString()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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


