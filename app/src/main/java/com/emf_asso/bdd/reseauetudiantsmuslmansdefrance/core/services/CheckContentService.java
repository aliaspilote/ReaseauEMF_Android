package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import java.util.regex.Pattern;

/**
 * Created by Omar_Desk on 11/11/2015.
 */
public class CheckContentService {
    private final static Pattern mail = Pattern.compile("[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[a-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?");
    private final static Pattern names = Pattern.compile("^[a-zA-Z\\s]+");
    private final static Pattern zipcode = Pattern.compile("\\d{5}");
    private final static Pattern phone = Pattern.compile("\\d{10}");

    public static boolean checkString(final String address) {
        if (names.matcher(address).matches())
            return true;
        else
            return false;
    }

    public static boolean checkConfirmation(final String str1, final String str2) {
        if (str1.equals(str2))
            return true;
        else
            return false;
    }

    public static boolean checkEmail(final String address) {
        if (mail.matcher(address).matches())
            return true;
        else
            return false;
    }

    public static boolean checkZipCode(final String zipCode) {
        if (zipcode.matcher(zipCode).matches())
            return true;
        else
            return false;
    }

    public static boolean checkPhone(final String phoneNum) {
        if (phone.matcher(phoneNum).matches())
            return true;
        else
            return false;
    }

    public enum Returned {OK, ERROR_EMAIL}
}
