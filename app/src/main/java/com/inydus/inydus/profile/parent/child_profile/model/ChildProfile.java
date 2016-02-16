package com.inydus.inydus.profile.parent.child_profile.model;

public class ChildProfile {

    public final static int MALE = 0;
    public final static int FEMALE = 1;
    public final static int NO_MATTER = 2;

    public String parent_id;
    public String child_name;
    public int child_age;
    public int child_gender;
    public String child_birth;
    public String child_adres;
    public String child_info;
    public String child_adres_detail;

    public ChildProfile(String parent_id, String child_name, int child_age, int child_gender, String child_info) {
        this.parent_id = parent_id;
        this.child_name = child_name;
        this.child_age = child_age;
        this.child_gender = child_gender;
        this.child_info = child_info;
    }


    public ChildProfile(String parent_id, String child_name, int child_gender, String child_info) {
        this.parent_id = parent_id;
        this.child_name = child_name;
        this.child_gender = child_gender;
        this.child_info = child_info;
    }


    public ChildProfile(String parent_id, String child_name, int child_age, int child_gender, String child_birth, String child_adres, String child_info, String child_adres_detail) {
        this.parent_id = parent_id;
        this.child_name = child_name;
        this.child_age = child_age;
        this.child_gender = child_gender;
        this.child_birth = child_birth;
        this.child_adres = child_adres;
        this.child_info = child_info;
        this.child_adres_detail = child_adres_detail;
    }
}
