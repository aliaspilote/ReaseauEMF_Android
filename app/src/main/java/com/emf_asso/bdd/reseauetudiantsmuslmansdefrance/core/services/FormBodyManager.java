package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.CriteriaObject;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionList;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Omar_Desk on 02/11/2015.
 */
public class FormBodyManager {

    public static java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DataContext.dateMysqlFormat);


    public static RequestBody template(SessionWsService AppCtx) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "save_infoperso")
                .add("mail", AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "")
                .add("token", AppCtx != null ? AppCtx.getToken() : "");

        return formBody.build();
    }
    
    public static RequestBody get_user(String mail, String token) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "get_user")
                .add("mail", mail)
                .add("token", token)
                .build();
        return formBody;

    }

    public static RequestBody get_ldf(String mail, String token) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "get_ldf")
                .add("mail", mail == null ? "" : mail)
                .add("token", token == null ? "" : token)
                .build();
        return formBody;
    }

/*
messageMail.setObject(
messageMail.setCorps(m
messageMail.setSender(
messageMail.setM_abstr
messageMail.setNote(((
 */


    public static RequestBody sendMailLDF(SessionWsService AppCtx) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "send_mail_ldf")
                .add("mail", AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "")
                .add("token", AppCtx.getToken() != null ? AppCtx.getToken() : "")
                .add("msg_obj", AppCtx.getServiceLDF().getMessage().getObject() + "")
                .add("msg_abs", AppCtx.getServiceLDF().getMessage().getM_abstract() + "")
                .add("msg_corps", AppCtx.getServiceLDF().getMessage().getCorps() + "")
                .add("msg_note", AppCtx.getServiceLDF().getMessage().getNote() + "");
        formBody = addSeleectLDFToFormBody(AppCtx.getServiceLDF().getNumSelectedLdf(), formBody);
        return formBody.build();
    }

    private static FormEncodingBuilder addSeleectLDFToFormBody(Map<String, Boolean> numSelectedLdf, FormEncodingBuilder form) {
        if (numSelectedLdf != null) {
            //form.add("ldf_count",numSelectedLdf.size()+"" );
            int i = 0;
            for (Map.Entry<String, Boolean> entry : numSelectedLdf.entrySet()) {
                if (entry.getValue()) {
                    form.add("ldf_id" + i, entry.getKey() + "");
                    i++;
                }
            }
            form.add("ldf_count", i + "");
        } else
            form.add("skills_count", "0");
        return form;
    }

    public static RequestBody checkmail(String mail) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "check_mail")
                .add("mail", mail)
                .build();
        return formBody;
    }

    public static RequestBody forgotPswd(String mail) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "forgot_pswd")
                .add("mail", mail)
                .build();
        return formBody;
    }


    public static RequestBody syncLDF(DiffusionList ldf, SessionWsService AppCtx) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "sync_ldf")
                .add("ldf_id", ldf.getId())
                .add("ldf_label", ldf.getLabel() + "")
                .add("mail", AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "")
                .add("token", AppCtx.getToken() != null ? AppCtx.getToken() : "");
        formBody = addLDFCriteriasFormBody(ldf.DiffusionCriteriaListViewValuesArr, formBody);
        return formBody.build();
    }


    public static RequestBody deleteLDF(DiffusionList ldf, SessionWsService AppCtx) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "delete_ldf")
                .add("mail", AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "")
                .add("token", AppCtx.getToken() != null ? AppCtx.getToken() : "")
                .add("ldf_id", ldf.getId());
        return formBody.build();
    }

    private static FormEncodingBuilder addLDFCriteriasFormBody(List<DiffusionCriteria> ldf_criterias, FormEncodingBuilder form) {
        if (ldf_criterias != null) {
            form.add("criterias_count", ldf_criterias.size() + "");
            int i = 0;
            for (DiffusionCriteria crt : ldf_criterias) {
                form.add("criteria_id" + i, crt.getCriteria_id() + "");
                form.add("criteria_name" + i, crt.getCriteria_Ref() + "");
                if (crt.isSpinner_type())
                    form.add("criteria_value" + i, ((CriteriaObject) crt.getValue()).getCriteriaValue() + "");
                else
                    form.add("criteria_value" + i, crt.getValue().toString() + "");

                i++;
            }
        } else
            form.add("criterias_count", "0");
        return form;
    }


    public static RequestBody addUser(UserMember user) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "add_user")
                .add("mail", user.getEmail() + "")
                .add("name", user.getName() + "")
                .add("firstname", user.getFirstname() + "")
                .add("zip_code", user.getZip_code() + "")
                .add("city", user.getCity() + "")
                .add("section", (user.getSection() != null ? user.getSection().getLabel() : ""))
                .add("phone", user.getPhone() + "")
                .add("hashed_pwd", user.getHashed_pwd() + "")
                .add("birth_date", (user.getBirth_date() != null ? sdf.format(user.getBirth_date()) : ""))
                .add("registration_date", (user.getBirth_date() != null ? sdf.format(user.getRegistration_date()) : ""))
                .add("involvement", (user.getInvolvement() != null ? user.getInvolvement().getLabel() : ""));
        formBody = addCursusToFormBody(user.getCurriculum(), formBody);
        formBody = addSkillsToFormBody(user.getSkills(), formBody);
        //.add("civility", user.getCivility() + "") à ajouter dans le formulaire
        return formBody.build();
    }
    public static RequestBody auth(String mail, String password) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "auth")
                .add("mail", mail + "")
                .add("hashed_pwd", password + "")
                .build();
        return formBody;
    }

    public static RequestBody getAction(String actionName) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", actionName)
                .build();
        return formBody;
    }


    private static FormEncodingBuilder addCursusToFormBody(List<Curriculum> listCursus, FormEncodingBuilder form) {
        if (listCursus != null) {
            form.add("cursus_count", listCursus.size() + "");
            int i = 0;
            for (Curriculum c : listCursus) {
                form.add("cursus_lbl_" + i, c.getLabel() + "");
                form.add("cursus_est_" + i, c.getEstablishment() + "");
                form.add("cursus_cit_" + i, c.getCity() + "");
                form.add("cursus_deg_id_" + i, c.getDegree().getDegree_id() + "");
                form.add("cursus_dis_id_" + i, c.getDiscipline().getDiscipline_id() + "");
                form.add("cursus_sta_" + i, sdf.format(c.getStart_date()) + "");
                form.add("cursus_end_" + i, sdf.format(c.getEnd_date()) + "");
                form.add("cursus_id_" + i, c.getId() + "");
                i++;
            }
        } else
            form.add("cursus_count", "0");
        return form;
    }
    private static FormEncodingBuilder addSkillsToFormBody(List<Skill> listSkills, FormEncodingBuilder form) {
        if (listSkills != null) {
            form.add("skills_count",listSkills.size()+"" );
            int i = 0;
            for (Skill s : listSkills) {
                i++;
                form.add("skill_id_"+i,s.getSkill_id()+"" );
            }
        } else
            form.add("skills_count", "0");
        return form;
    }


    public static RequestBody update_infoperso(SessionWsService AppCtx) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "save_infoperso")
                .add("mail", AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "")
                .add("token", AppCtx != null ? AppCtx.getToken() : "")
                .add("name", AppCtx.getUserMember().getName() + "")
                .add("firstname", AppCtx.getUserMember().getFirstname() + "")
                .add("zip_code", AppCtx.getUserMember().getZip_code() + "")
                .add("city", AppCtx.getUserMember().getCity() + "")
                .add("section", (AppCtx.getUserMember().getSection() != null ? AppCtx.getUserMember().getSection().getLabel() : ""))
                .add("phone", AppCtx.getUserMember().getPhone() + "")
                .add("hashed_pwd", AppCtx.getUserMember().getHashed_pwd() + "")
                .add("birth_date", (AppCtx.getUserMember().getBirth_date() != null ? sdf.format(AppCtx.getUserMember().getBirth_date()) : ""))
                .add("registration_date", (AppCtx.getUserMember().getBirth_date() != null ? sdf.format(AppCtx.getUserMember().getRegistration_date()) : ""))
                .add("involvement", (AppCtx.getUserMember().getInvolvement() != null ? AppCtx.getUserMember().getInvolvement().getLabel() : ""));
        //formBody = addCursusToFormBody(AppCtx.getUserMember().getCurriculum(), formBody);
        //formBody = addSkillsToFormBody(AppCtx.getUserMember().getSkills(), formBody);

        return formBody.build();
    }

    public static RequestBody change_pwd(SessionWsService AppCtx, String pswd) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "change_pwd")
                .add("mail", AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "")
                .add("hashed_pwd", pswd != null ? pswd : "")
                .add("token", AppCtx != null ? AppCtx.getToken() : "");

        return formBody.build();
    }

    public static RequestBody check_pwd(SessionWsService AppCtx, String Old_pswd) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "check_pwd")
                .add("mail", AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "")
                .add("hashed_pwd", Old_pswd != null ? Old_pswd : "")
                .add("token", AppCtx != null ? AppCtx.getToken() : "");

        return formBody.build();
    }

    public static RequestBody update_skills(SessionWsService AppCtx) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "save_skills")
                .add("mail", AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "")
                .add("token", AppCtx != null ? AppCtx.getToken() : "")
                .add("name", AppCtx.getUserMember().getName() + "")
                .add("firstname", AppCtx.getUserMember().getFirstname() + "")
                .add("zip_code", AppCtx.getUserMember().getZip_code() + "")
                .add("city", AppCtx.getUserMember().getCity() + "")
                .add("section", (AppCtx.getUserMember().getSection() != null ? AppCtx.getUserMember().getSection().getLabel() : ""))
                .add("phone", AppCtx.getUserMember().getPhone() + "")
                .add("hashed_pwd", AppCtx.getUserMember().getHashed_pwd() + "")
                .add("birth_date", (AppCtx.getUserMember().getBirth_date() != null ? sdf.format(AppCtx.getUserMember().getBirth_date()) : ""))
                .add("registration_date", (AppCtx.getUserMember().getBirth_date() != null ? sdf.format(AppCtx.getUserMember().getRegistration_date()) : ""))
                .add("involvement", (AppCtx.getUserMember().getInvolvement() != null ? AppCtx.getUserMember().getInvolvement().getLabel() : ""));
        // formBody = addCursusToFormBody(AppCtx.getUserMember().getCurriculum(), formBody);
        formBody = addSkillsToFormBody(AppCtx.getUserMember().getSkills(), formBody);

        return formBody.build();
    }

    public static RequestBody update_emfprofile(SessionWsService AppCtx) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "save_emfprofile")
                .add("mail", AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "")
                .add("token", AppCtx != null ? AppCtx.getToken() : "")
                .add("name", AppCtx.getUserMember().getName() + "")
                .add("firstname", AppCtx.getUserMember().getFirstname() + "")
                .add("zip_code", AppCtx.getUserMember().getZip_code() + "")
                .add("city", AppCtx.getUserMember().getCity() + "")
                .add("section", (AppCtx.getUserMember().getSection() != null ? AppCtx.getUserMember().getSection().getLabel() : ""))
                .add("phone", AppCtx.getUserMember().getPhone() + "")
                .add("hashed_pwd", AppCtx.getUserMember().getHashed_pwd() + "")
                .add("birth_date", (AppCtx.getUserMember().getBirth_date() != null ? sdf.format(AppCtx.getUserMember().getBirth_date()) : ""))
                .add("registration_date", (AppCtx.getUserMember().getBirth_date() != null ? sdf.format(AppCtx.getUserMember().getRegistration_date()) : ""))
                .add("involvement", (AppCtx.getUserMember().getInvolvement() != null ? AppCtx.getUserMember().getInvolvement().getLabel() : ""));
        //formBody = addCursusToFormBody(AppCtx.getUserMember().getCurriculum(), formBody);
        //formBody = addSkillsToFormBody(AppCtx.getUserMember().getSkills(), formBody);

        return formBody.build();
    }

    public static RequestBody update_cursus(SessionWsService AppCtx) {
        FormEncodingBuilder formBody = new FormEncodingBuilder()
                .add("action", "save_cursus")
                .add("mail", AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "")
                .add("token", AppCtx != null ? AppCtx.getToken() : "")
                .add("name", AppCtx.getUserMember().getName() + "")
                .add("firstname", AppCtx.getUserMember().getFirstname() + "")
                .add("zip_code", AppCtx.getUserMember().getZip_code() + "")
                .add("city", AppCtx.getUserMember().getCity() + "")
                .add("section", (AppCtx.getUserMember().getSection() != null ? AppCtx.getUserMember().getSection().getLabel() : ""))
                .add("phone", AppCtx.getUserMember().getPhone() + "")
                .add("hashed_pwd", AppCtx.getUserMember().getHashed_pwd() + "")
                .add("birth_date", (AppCtx.getUserMember().getBirth_date() != null ? sdf.format(AppCtx.getUserMember().getBirth_date()) : ""))
                .add("registration_date", (AppCtx.getUserMember().getBirth_date() != null ? sdf.format(AppCtx.getUserMember().getRegistration_date()) : ""))
                .add("involvement", (AppCtx.getUserMember().getInvolvement() != null ? AppCtx.getUserMember().getInvolvement().getLabel() : ""));
        formBody = addCursusToFormBody(AppCtx.getUserMember().getCurriculum(), formBody);
        //formBody = addSkillsToFormBody(AppCtx.getUserMember().getSkills(), formBody);

        return formBody.build();
    }
    

}
