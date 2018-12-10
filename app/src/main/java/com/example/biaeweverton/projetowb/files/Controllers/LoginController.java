package com.example.biaeweverton.projetowb.files.Controllers;

/**
 * Created by biafo on 08/12/2018.
 */

public class LoginController {

    public int validation(String email, String password){
        if(email.isEmpty() || password.isEmpty()){
            return  1;
        }else if(email.contains("@") == false) {
            return 2;
        }else{
            return 3;
        }

        //missing return validation database email
    }
}
