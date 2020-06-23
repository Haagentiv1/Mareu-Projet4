package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingRoom;

import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();

    List<MeetingRoom> getMeetingRooms();

    List<String> getMeetingEmail();

    void addMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    List<Meeting> filterByHours(String startHour,String endHour);

    List<Meeting> filterByPlace(String Place);

    List<Meeting> filterByDate(String Date);

    String concatStringMeetingDEsc(Meeting meeting);

    String concatEmail(Meeting meeting);

    Boolean emailVerification(List<String>list);

    Boolean emptyTextVerification(String Text);

    Boolean dateVerification(String Date);

    Boolean hourVerification(String Hour);

    Boolean meetingVerificationBis(String Place, String Date, String startHour, String endHour);
}
