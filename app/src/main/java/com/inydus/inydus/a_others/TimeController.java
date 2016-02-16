package com.inydus.inydus.a_others;

import android.util.Log;

import com.inydus.inydus.findSitter.sitter_profile.model.AbleTime;

import java.util.ArrayList;

public class TimeController {

    int startTime;
    int endTime;
    int totalTime;

    public String s_hour, s_min, e_hour, e_min, s_ap, e_ap;

    public TimeController() {
    }

    public void setTimes(AbleTime ableTime){
        this.startTime = ableTime.sittertime_starttime;
        this.endTime = ableTime.sittertime_endtime;

        if(startTime >= 1000){
            s_hour = String.valueOf(startTime).substring(0, 2);
            s_min = String.valueOf(startTime).substring(2);
        }
        else{
            s_hour = String.valueOf(startTime).substring(0, 1);
            s_min = String.valueOf(startTime).substring(1);
        }

        if(endTime >= 1000){
            e_hour = String.valueOf(endTime).substring(0, 2);
            e_min = String.valueOf(endTime).substring(2);
        }
        else{
            e_hour = String.valueOf(endTime).substring(0, 1);
            e_min = String.valueOf(endTime).substring(1);
        }

        if(Integer.parseInt(s_hour) > 12) {
            s_hour = String.valueOf(Integer.parseInt(s_hour) - 12);
        }

        if(Integer.parseInt(e_hour) > 12) {
            e_hour = String.valueOf(Integer.parseInt(e_hour) - 12);
        }

        if(startTime == 2400 || startTime < 1200)
            s_ap = "오전";
        else
            s_ap = "오후";

        if(endTime == 2400 || endTime < 1200)
            e_ap = "오전";
        else
            e_ap = "오후";

        totalTime = endTime - startTime;
    }

    public String mTimeFormat(){
        return String.format("%s %s시 %s분 ~ %s %s시 %s분(%s)", s_ap, s_hour, s_min, e_ap, e_hour, e_min, getTotalTime());
    }

    public String networkFormat(){
        return String.format("_%s_%s", startTime, endTime);
    }

    public String networkFormat(ArrayList<AbleTime> ableTimes){
        String ableTimesNetworkFormat = "";
        for(int i = 0; i < ableTimes.size(); i++){
            AbleTime ableTime = ableTimes.get(i);
            if(ableTime == null){
                continue;
            }
            else{
                ableTimesNetworkFormat += String.format("_%s_%s_%s",
                        ableTime.sittertime_day,
                        ableTime.sittertime_starttime,
                        ableTime.sittertime_endtime);
            }
        }
        return ableTimesNetworkFormat.substring(1);
    }

    public int combineHourAndMin(int hour, int min){
        String str_hour = String.valueOf(hour);
        String str_min;

        if(min < 10)
            str_min = "0" + String.valueOf(min);
        else
            str_min = String.valueOf(min);

        return Integer.parseInt(str_hour + str_min);
    }

    public String getTotalTime(){
        String[] hourMin = new String[2];
        if(totalTime >= 1000){
            hourMin[0] = String.valueOf(totalTime).substring(0, 2);
           hourMin[1] = String.valueOf(totalTime).substring(2);
        }
        else{
            hourMin[0] = String.valueOf(totalTime).substring(0, 1);
            hourMin[1] = String.valueOf(totalTime).substring(1);
        }

        if(hourMin[1].equals("30")){
            return String.format("%s시간 %s분", hourMin[0], hourMin[1]);
        }
        else{
            return String.format("%s시간", hourMin[0]);
        }
    }

    public float timeToFloat(AbleTime ableTime){
        int totalTime = ableTime.sittertime_endtime - ableTime.sittertime_starttime;

        String[] hourMin = new String[2];
        if(totalTime >= 1000){
            hourMin[0] = String.valueOf(totalTime).substring(0, 2);
            hourMin[1] = String.valueOf(totalTime).substring(2);
        }
        else{
            hourMin[0] = String.valueOf(totalTime).substring(0, 1);
            hourMin[1] = String.valueOf(totalTime).substring(1);
        }
        Log.i("MyTag", "hourMin[0] : " + hourMin[0]);
        Log.i("MyTag", "hourMin[1] : " + hourMin[1]);

        if(hourMin[1].equals("30")){
            return Float.parseFloat(hourMin[0]) + (float)0.5;
        }
        else{
            return Float.parseFloat(hourMin[0]);
        }
    }

    public String getTotalTime(Float totalTime){

        String[] hourMin = new String[2];
        int hour = totalTime.intValue();
        hourMin[0] = String.valueOf(hour);
        hourMin[1] = String.valueOf(totalTime - hour);
        if((totalTime - hour) == 0.5){
            return String.format("%s시간 30분", hourMin[0]);
        }
        else{
            return String.format("%s시간", hourMin[0]);
        }
    }
}
