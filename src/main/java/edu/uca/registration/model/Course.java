package edu.uca.registration.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String code, title;
    private int capacity;

    private List<String> roster = new ArrayList<>();
    private List<String> waitlist = new ArrayList<>();

    public Course(String code,
                  String title,
                  int capacity) {
        this.code = code;
        this.title = title;
        this.capacity = capacity;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getRoster() {
        return roster;
    }

    public List<String> getWaitlist() {
        return waitlist;
    }

    public void addToRoster(String studentID) {
        roster.add(studentID);
    }

    public void addToWaitlist(String studentID) {
        waitlist.add(studentID);
    }
}
