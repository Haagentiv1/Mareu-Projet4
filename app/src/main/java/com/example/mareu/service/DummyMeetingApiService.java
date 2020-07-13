package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingRoom;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings = DummyMeetingGenerator.generateMeeting();

    private List<MeetingRoom> mMeetingRooms = DummyMeetingRoomGenerator.generateMeetingRoom();

    private List<String> mMeetingEmail = DummyEmailGenerator.generateEmail();

    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public List<MeetingRoom> getMeetingRooms() {
        return mMeetingRooms;
    }

    @Override
    public List<String> getMeetingEmail() { return mMeetingEmail; }

    @Override
    public void addMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    @Override
    public List<Meeting> filterByHours(String startHour,String endHour) {
        List<Meeting> FilterByHours = new ArrayList<>();

        for (Meeting meeting : mMeetings){
            DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm");
            LocalTime meetingStart = df.parseLocalTime(meeting.getStartHour());
            LocalTime meetingEnd = df.parseLocalTime(meeting.getEndHour());
            LocalTime newMeetingStart = df.parseLocalTime(startHour);
            LocalTime newMeetingEnd = df.parseLocalTime(endHour);
            if ((newMeetingStart.isAfter(meetingStart) && newMeetingStart.isBefore(meetingEnd)) || (newMeetingEnd.isAfter(meetingStart) && newMeetingEnd.isBefore(meetingEnd))){
                FilterByHours.add(meeting);
            }
        }return FilterByHours;
    }

    @Override
    public List<Meeting> filterByPlace(String place) {
        List<Meeting> FilterByPlace = new ArrayList<>();

        for (Meeting meeting : mMeetings){
            if (meeting.getPlace().equals(place)){
                FilterByPlace.add(meeting);
            }
        }return FilterByPlace;
    }

    @Override
    public List<Meeting> filterByDate(String Date) {
        List<Meeting> FilterByDate = new ArrayList<>();

        for (Meeting meeting : mMeetings){
            if (meeting.getDate().equals(Date)){
                FilterByDate.add(meeting);
            }
        }return FilterByDate;
    }

    @Override
    public String concatStringMeetingDEsc(Meeting meeting) {
        String name = meeting.getName();
        String hour = meeting.getStartHour();
        String place = meeting.getPlace();

        String meetingDescription = name + " - " + hour + " - " + place;

        return meetingDescription;
    }

    @Override
    public String concatEmail(Meeting meeting) {
        String concatEmail = new String();
        List<String> contributors = meeting.getParticipants();
        for(String string: contributors){
            concatEmail = concatEmail + " " + string; }
        return concatEmail;
    }

    @Override
    public Boolean emptyTextVerification(String Text) {
        return Text.length() == 0;
    }

    @Override
    public Boolean dateVerification(String Date) {
        String DatePattern = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";
        return Date.matches(DatePattern);
    }

    @Override
    public Boolean hourVerification(String Hour) {
        String HourPattern = "([0-1][0-9]|2[0-3]):[0-5][0-9] - ([0-1][0-9]|2[0-3]):[0-5][0-9]";
        return Hour.matches(HourPattern);
    }
    /**
     * This method check if a new meeting is between an other one.
     * first time, we check if we have a meeting in the same room #PLace at the same Date
     * If the first condition is validate we check if the starting or ending hour of our new meeting is between an other meeting.
     * @param Place
     * @param Date
     * @param startHour
     * @param endHour
     * @return false if the new meeting is between in a created meeting.
     */
    @Override
    public Boolean meetingVerificationBis(String Place, String Date, String startHour, String endHour) {
        
        for (Meeting meeting : mMeetings){
            if (meeting.getPlace().equals(Place) && meeting.getDate().equals(Date)){
                DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm");
                LocalTime meetingStart = df.parseLocalTime(meeting.getStartHour());
                LocalTime meetingEnd = df.parseLocalTime(meeting.getEndHour());
                LocalTime newMeetingStart = df.parseLocalTime(startHour);
                LocalTime newMeetingEnd = df.parseLocalTime(endHour);

                if (((newMeetingStart.isAfter(meetingStart) || newMeetingStart.equals(meetingStart)) && newMeetingStart.isBefore(meetingEnd))
                        || ((newMeetingEnd.isAfter(meetingStart)|| newMeetingEnd.equals(meetingEnd)) && newMeetingEnd.isBefore(meetingEnd))){
                    return false;
                }
            }
        }
        return true;
    }
}
