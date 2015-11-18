/*
package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import org.jasypt.util.password.StrongPasswordEncryptor;

*/
/**
 * Created by Omar on 18/11/2015.
 *//*

public class Crypt {

    // On utilise ici une méthode de type sha1Hex

    public static Boolean Check(String notcryptedPswd,String storedcryptedPswd){

        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(notcryptedPswd);

        if (passwordEncryptor.checkPassword(storedcryptedPswd, encryptedPassword))
            return true;
        else
            return false;
    }

    public static String Encrypt(String notcryptedPswd) {

        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(notcryptedPswd);

        return encryptedPassword;
    }

}
*/
