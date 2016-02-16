package com.inydus.inydus.findSitter.request.model;

public class Proposal {

    String parent_id;
    String sitter_id;
    int pro_pay;
    String pro_day;
    int pro_hour;
    String pro_adres;
    int pro_sibling;
    String pro_accept;
    int pro_count;

    public Proposal(String parent_id, String sitter_id, int pro_pay, String pro_day, int pro_hour, String pro_adres, int pro_sibling, String pro_accept, int pro_count) {
        this.parent_id = parent_id;
        this.sitter_id = sitter_id;
        this.pro_pay = pro_pay;
        this.pro_day = pro_day;
        this.pro_hour = pro_hour;
        this.pro_adres = pro_adres;
        this.pro_sibling = pro_sibling;
        this.pro_accept = pro_accept;
        this.pro_count = pro_count;
    }
}
