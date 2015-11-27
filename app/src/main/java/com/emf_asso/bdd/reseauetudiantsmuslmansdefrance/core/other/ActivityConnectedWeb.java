package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.ConnectionDetector;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;

/**
 * Created by Omar_Desk on 15/11/2015.
 */
public interface ActivityConnectedWeb {

    public ConnectionDetector testInternetConnection = new ConnectionDetector();

    void ReceptionResponse(HttpReponse Rep);

    void DisplayToast(String text, int time);

    void DisplayToast(String text);

}