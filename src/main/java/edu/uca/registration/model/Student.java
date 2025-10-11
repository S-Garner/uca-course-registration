package edu.uca.registration.model;

import java.io.*;

public class Student implements Registrable {
    private String id, name, email;

    public Student() {};

    public Student (String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String toString(){
        return this.id + " " + this.name + " <" + this.email + ">";
    }
    

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
