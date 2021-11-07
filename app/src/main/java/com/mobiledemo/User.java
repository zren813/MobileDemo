package com.mobiledemo;

import java.util.List;
import java.util.Map;

public class User {
    public String nickName, email, moment, firstName, lastName, major;
    public Map<String, String> courses;

    public User() {

    }
    public User(String email) {
        this.email = email;
    }


}
