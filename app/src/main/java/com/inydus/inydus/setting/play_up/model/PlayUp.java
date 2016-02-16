package com.inydus.inydus.setting.play_up.model;

public class PlayUp {
    public String user_id;
    public String user_name;
    public int playTime;
    public int totalPlayTime;

    public PlayUp(String user_id, String user_name, int playTime, int totalPlayTime) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.playTime = playTime;
        this.totalPlayTime = totalPlayTime;
    }
}
