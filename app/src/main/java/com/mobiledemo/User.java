package com.mobiledemo;

import java.util.List;
import java.util.Map;

public class User {
    public String nickName, email, moment, firstName, lastName, major, interestSemester, interestYear;
    public Map<String, String> courses;

    public User(String email) {
        this.email = email;
    }
    public User() {

    }


}
