package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    private static List<String> mail = Arrays.asList("tristan.claudic@gmail.com","test@gmail.com","claudic.pro@gmail.com");
    /**
     * class for test
     */

    public static List<Meeting> DUMMY_MEETING_TEST = Arrays.asList(
            new Meeting("Réunion A","visio", "Gandalf", "12:30","13:40", "25-06-2020",mail,"#ff33b5e5"),
            new Meeting("Réunion B","visio", "Aragorn", "12:30","14:30", "27-06-2020", mail,"#ff0099cc"),
            new Meeting("Réunion C","visio", "Legolas", "12:30","15:30", "27-06-2020", mail,"#ffffbb33"),
            new Meeting("Réunion D","visio", "Gimli", "15:30","17:30", "29-06-2020", mail,"#ffaa66cc"),
            new Meeting("Réunion E","visio", "Frodo", "12:45","21:15", "29-06-2020", mail,"#ffff4444"),
            new Meeting("Réunion F","visio", "Sam", "12:20","14:30", "27-06-2020", mail,"#ff0099cc"),
            new Meeting("Réunion G","visio", "Boromir", "12:30","15:30", "26-07-2020",mail,"#ff669900"),
            new Meeting("Réunion H","visio", "Pipin", "12:20","15:30", "22-06-2020", mail,"#ffff8800"),
            new Meeting("Réunion I","visio", "Gandalf", "12:20","15:30", "28-04-2020",mail,"#ffcc0000"),
            new Meeting("Réunion J","visio", "Sauron", "8:30","14:40", "13-07-2020", mail,"#002300")

    );

    static List<Meeting> generateMeeting(){return new ArrayList<>( DUMMY_MEETING_TEST);}


}
