package com.inydus.inydus.profile.sitter.profile.model;

import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;

import java.util.List;

public class SitterProfile {

    public final static int SIBLING_0 = 0, SIBLING_1 = 1, SIBLING_2 = 2;
    public final static int DESIRED_TIME_10 = 10, DESIRED_TIME_20 = 20, DESIRED_TIME_30 = 30;
    public final static String RANK_A = "A", RANK_B = "B", RANK_C = "C";
    public final static int RANK_PAY_A = 15000, RANK_PAY_B = 12000, RANK_PAY_C = 10000;
    public final static int SIBLING_PAY_0 = 0, SIBLING_PAY_1 = 3000, SIBLING_PAY_2 = 6000;

    public String user_id;
    public String user_name;
    public int sitter_gender;
    public int sitter_age;
    public String sitter_dept;
    public String sitter_info;
    public String sitter_exp_price;
    public String sitter_univ;
    public String sitter_able_loc;
    public List<AbleTime> able_times; //서버에서 받아올 때
    public String sitter_birth;



    public int sitter_judge;
    public int sitter_day;
    public String sitter_rank;
    public int sitter_edu;

    public SitterProfile(String user_id, int sitter_gender, int sitter_age, String sitter_birth, String sitter_dept, String sitter_info, String sitter_exp_price, String sitter_univ, String sitter_able_loc, List<AbleTime> able_times) {
        this.user_id = user_id;
        this.sitter_gender = sitter_gender;
        this.sitter_age = sitter_age;
        this.sitter_birth = sitter_birth;
        this.sitter_dept = sitter_dept;
        this.sitter_info = sitter_info;
        this.sitter_exp_price = sitter_exp_price;
        this.sitter_univ = sitter_univ;
        this.sitter_able_loc = sitter_able_loc;
        this.able_times = able_times;
    }


    public SitterProfile(String user_id, String user_name, int sitter_gender, int sitter_age, String sitter_birth, String sitter_dept, String sitter_info, String sitter_exp_price, String sitter_univ, String sitter_able_loc, List<AbleTime> able_times) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.sitter_gender = sitter_gender;
        this.sitter_age = sitter_age;
        this.sitter_birth = sitter_birth;
        this.sitter_dept = sitter_dept;
        this.sitter_info = sitter_info;
        this.sitter_exp_price = sitter_exp_price;
        this.sitter_univ = sitter_univ;
        this.sitter_able_loc = sitter_able_loc;
        this.able_times = able_times;
    }

    public SitterProfile(String user_id, int sitter_gender, int sitter_age, String sitter_dept, String sitter_info, String sitter_exp_price, String sitter_univ, List<AbleTime> able_times, String sitter_able_loc) {
        this.user_id = user_id;
        this.sitter_gender = sitter_gender;
        this.sitter_age = sitter_age;
        this.sitter_dept = sitter_dept;
        this.sitter_info = sitter_info;
        this.sitter_exp_price = sitter_exp_price;
        this.sitter_univ = sitter_univ;
        this.able_times = able_times;
        this.sitter_able_loc = sitter_able_loc;
    }
}