package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
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
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DataContext.dateMysqlFormat);
        String lesCursusJson = "";
        if (user.getCurriculum() != null) {
            lesCursusJson = "{\n" + "  \"cursus\":[";
            int i = 0;
            for (Curriculum c : user.getCurriculum()) {
                lesCursusJson += "\n" + "    {";
                lesCursusJson += '"' + "label" + '"' + ':' + '"' + c.getLabel() + '"' + ',' + "/n";
                lesCursusJson += '"' + "establishment" + '"' + ':' + '"' + c.getEstablishment() + '"' + ',' + "/n";
                lesCursusJson += '"' + "city" + '"' + ':' + '"' + c.getCity() + '"' + ',' + "/n";
                lesCursusJson += '"' + "degree_id" + '"' + ':' + '"' + c.getDegree().getDegree_id() + '"' + ',' + "/n";
                lesCursusJson += '"' + "discipline_id" + '"' + ':' + '"' + c.getDiscipline().getDiscipline_id() + '"' + ',' + "/n";
                lesCursusJson += '"' + "start_date" + '"' + ':' + '"' + sdf.format(c.getStart_date()) + '"' + ',' + "/n";
                lesCursusJson += '"' + "end_date" + '"' + ':' + '"' + sdf.format(c.getEnd_date()) + '"' + ',' + "/n";
                lesCursusJson += '"' + "end_date" + '"' + ':' + '"' + c.getId() + '"' + "/n";
                i++;
                lesCursusJson += "\n" + "    }";
                if (i < user.getCurriculum().size())
                    lesCursusJson += ",";
            }
            lesCursusJson += "\n" + "  ]" + ",";
            lesCursusJson += '"' + "count" + '"' + ':' + '"' + Integer.toString(i) + '"' + "/n";
            lesCursusJson += "\n" + "}";
        }
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "add_user")
                .add("mail", user.getEmail() + "")
                .add("name", user.getName() + "")
                .add("firstname", user.getFirstname() + "")
                .add("civility", user.getCivility() + "")
                .add("zip_code", user.getZip_code() + "")
                .add("city", user.getCity() + "")
                .add("section", user.getSection().getLabel())
                .add("dicipline", user.getDicipline().getLabel())
                .add("niveau", "")
                .add("involvement", user.getInvolvement().getInvolvement_id().toString())
                .add("phone", user.getPhone() + "")
                .add("birth_date", sdf.format(user.getBirth_date()) + "")
                .add("registration_date", sdf.format(user.getRegistration_date()) + "")
                .add("skills", "expert Android")
                .add("hashed_pwd", user.getHashed_pwd() + "")
                .add("listCursus", lesCursusJson)
                .build();
        // .add("dicipline", user.getDicipline().getLabel() + "")
        // .add("skills", user.getSkills().get(1).getLabel() + "")
        return formBody;
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

}
