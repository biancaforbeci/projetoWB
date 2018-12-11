package com.example.biaeweverton.projetowb.files.Controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by biafo on 08/12/2018.
 */

public class LoginController {

    public int validation(String email, String password){
        if(email.isEmpty() || password.isEmpty()){
            return  1;
        }else if(isEmailValid(email) == false) {
            return 2;
        }else{
            return 3;
        }

        //missing return validation database email
    }


    public static boolean isEmailValid(String email) {

        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
            return true;
        else
            return false;
    }
}
