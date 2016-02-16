package com.inydus.inydus.register.able_location;

public class AbleLocation {

    public final static int NORMAL_TEXT_COLOR_CODE = 0;
    public final static int CLICKED_TEXT_COLOR_CODE = 1;

    int textColorCode;
    String location;

    public AbleLocation(int textColorCode, String location) {
        this.textColorCode = textColorCode;
        this.location = location;
    }
}
