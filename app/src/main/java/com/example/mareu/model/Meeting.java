package com.example.mareu.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class Meeting implements Parcelable {
    private String name;
    private String subject;
    private String place;
    private String startHour;
    private String endHour;
    private String date;
    private List<String> participants;
    private String meetingColor;




    public Meeting(String name, String subject, String place, String startHour, String  endHour, String date, List<String> participants, String meetingColor) {
        this.name = name;
        this.subject = subject;
        this.place = place;
        this.startHour = startHour;
        this.endHour = endHour;
        this.date = date;
        this.participants = participants;
        this.meetingColor = meetingColor;

    }

    protected Meeting(Parcel in) {
        name = in.readString();
        subject = in.readString();
        place = in.readString();
        startHour = in.readString();
        endHour = in.readString();
        date = in.readString();
        participants= new ArrayList<String>();
        in.readStringList(participants);
        meetingColor = in.readString();
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    public String getName() { return name; }

    public void setName(String name) { this.name = name;}

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public List<String> getParticipants() { return participants; }

    public void setParticipants(String participants) { getParticipants().add(participants); }

    public String getPlace() { return place; }

    public void setPlace( String place) { this.place = place; }

    public String getStartHour(){return startHour;}

    public void setStartHour(String startHour) { this.startHour = startHour; }

    public String getDate(){ return date; }

    public void setDate(String date){ this.date = date; }

    public String getMeetingColor() { return meetingColor; }

    public void setMeetingColor(String meetingColor) { this.meetingColor = meetingColor; }

    public String getEndHour() { return endHour; }

    public void setEndHour(String endHour) { this.endHour = endHour; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(subject);
        dest.writeString(place);
        dest.writeString(startHour);
        dest.writeString(endHour);
        dest.writeString(date);
        dest.writeStringList(participants);
        dest.writeString(meetingColor);
    }
}
