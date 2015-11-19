package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.oauth.OAuthService;

/**
 * Created by Omar_Desk on 19/11/2015.
 */
public class LinkedInParse {


    OAuthService service = new ServiceBuilder()
            .provider(LinkedInApi.class)
            .apiKey("7710fgc74kv8bh")
            .apiSecret("dwO7gREjSYFSOTHS")
            .build();
}
