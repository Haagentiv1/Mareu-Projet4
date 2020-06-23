package com.example.mareu.service;

import com.example.mareu.model.MeetingRoom;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingRoomGenerator {

    public static List<MeetingRoom> DUMMY_MEETINGROOM = Arrays.asList(
            new MeetingRoom(0,"Gandalf","#ff33b5e5"),
            new MeetingRoom(1,"Aragorn","#ff0099cc"),
            new MeetingRoom(2,"Legolas","#ffffbb33"),
            new MeetingRoom(3,"Gimli","#ffaa66cc"),
            new MeetingRoom(4,"Frodo","#ffff4444"),
            new MeetingRoom(5,"Sam","#ff0099cc"),
            new MeetingRoom(6,"Boromir","#ff669900"),
            new MeetingRoom(7,"Pippin","#ffff8800"),
            new MeetingRoom(8,"Merry","#ffcc0000"),
            new MeetingRoom(9,"Sauron","#002300")
    );

    static List<MeetingRoom> generateMeetingRoom(){return new ArrayList<>(DUMMY_MEETINGROOM);}

}
