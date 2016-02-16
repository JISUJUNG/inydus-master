package com.inydus.inydus.findSitter.sitter_profile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AbleTime implements Parcelable {

    public String sittertime_day;
    public int sittertime_starttime;
    public int sittertime_endtime;

    public AbleTime(String sittertime_day, int sittertime_starttime, int sittertime_endtime) {
        this.sittertime_day = sittertime_day;
        this.sittertime_starttime = sittertime_starttime;
        this.sittertime_endtime = sittertime_endtime;
    }

    protected AbleTime(Parcel in) {
        sittertime_day = in.readString();
        sittertime_starttime = in.readInt();
        sittertime_endtime = in.readInt();
    }

    public static final Creator<AbleTime> CREATOR = new Creator<AbleTime>() {
        @Override
        public AbleTime createFromParcel(Parcel in) {
            return new AbleTime(in);
        }

        @Override
        public AbleTime[] newArray(int size) {
            return new AbleTime[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sittertime_day);
        dest.writeInt(sittertime_starttime);
        dest.writeInt(sittertime_endtime);
    }
}
