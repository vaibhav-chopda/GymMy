package com.example.vaibhavchopda.gymmy24v11;

public class profilePage {

private String name,contact,age, workouts;

public profilePage() {
}

    public profilePage(String name, String contact, String age, String workouts) {
        this.name = name;
        this.contact = contact;
        this.age = age;
        this.workouts = workouts;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getAge() {
        return age;
    }

    public String getWorkouts() {
        return workouts;
    }
}
