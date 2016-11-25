package com.example.mahmoud.datashow;

/**
 * Created by Mahmoud on 11/20/2016.
 */

public class User {

    private String name;
    private String email;
    private String phone;
    private String jobTitle;
    private String id;




    public User(){}

    public User(String id,String jobTitle,String email,String name,String phone){
        this.id=id;
        this.name=name;
        this.email=email;
        this.jobTitle=jobTitle;
        this.phone=phone;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
