package edu.uca.registration.model;

import java.util.ArrayList;
import java.util.List;

public class Course implements Registrable {
    private String id;
    private String name;
    private int capacity;

    private List<String> roster = new ArrayList<>();
    private List<String> waitlist = new ArrayList<>();

    public Course() {}

    public Course(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
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
