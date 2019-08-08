package com.example.vaibhavchopda.gymmy24v11;
/*All the methods mentioned here are called in RecyclerView_peoplefinder*/
public class Profile { //This is for the recycler view in people finder activity all set methods set values and get ones fetch
    private String name;
    private String age;
    private String profilePic;
    private String contact;
    private boolean permission;
    private String workouts;

    public Profile() {
    }

    public Profile(String name, String age, String profilePic, String workouts,boolean permission,String contact) {
        this.name = name;
        this.age = age;
        this.contact=contact;
        this.profilePic = profilePic;
        this.permission = permission;
        this.workouts= workouts;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getage() {
        return age;
    }

    public void setage(String age) {
        this.age = age;
    }

    public String getWorkouts() {

        return workouts;
    }

    public void setWorkouts(String workouts) {
        this.workouts = workouts;
    }


    public String getContact() {

        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean getPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}
