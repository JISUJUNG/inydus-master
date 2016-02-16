package com.inydus.inydus.main.mypage.model;

import java.io.Serializable;

/**
 * Created by JUNGJISU on 2016. 1. 25..
 */
public class Playdiary implements Serializable {

    public String sitter_id;
    public String parent_id;
    public String child_name;
    public int child_gender;
    public String play_date;
    public String play_title;
    public String play_keyword;
    public String play_photo1;
    public String play_photo2;
    public String play_photo3;
    public int play_no;

    public Playdiary(String sitter_id, String parent_id, String child_name, int child_gender, String play_date, String play_title, String play_keyword, String play_photo1, String play_photo2, String play_photo3, int play_no) {
        this.sitter_id = sitter_id;
        this.parent_id = parent_id;
        this.child_name = child_name;
        this.child_gender = child_gender;
        this.play_date = play_date;
        this.play_title = play_title;
        this.play_keyword = play_keyword;
        this.play_photo1 = play_photo1;
        this.play_photo2 = play_photo2;
        this.play_photo3 = play_photo3;
        this.play_no = play_no;
    }
}
