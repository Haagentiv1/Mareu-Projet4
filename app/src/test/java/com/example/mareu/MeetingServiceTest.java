package com.example.mareu;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.service.DummyMeetingGenerator;
import com.example.mareu.service.DummyMeetingRoomGenerator;
import com.example.mareu.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {
    private MeetingApiService service;

    @Before
    public void setup(){service = DI.getNewInstanceApiService();}

    @Test
    public void getMeetingWithSuccess(){
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETING_TEST;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }
    @Test
    public void getMeetingRoomWithSuccess(){
        List<MeetingRoom> meetingRooms = service.getMeetingRooms();
        List<MeetingRoom> expectedMeetingRooms = DummyMeetingRoomGenerator.DUMMY_MEETINGROOM;
        assertThat(meetingRooms,IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetingRooms.toArray()));
    }
    @Test
    public void addMeetingWithSuccess(){
        Meeting meetingToAdd = new Meeting("Test","Test","Sam","12:30","13h30","28-4-2020","Test@gmail.com","#ff000000");
        service.addMeeting(meetingToAdd);
        assertTrue(service.getMeetings().contains(meetingToAdd));
    }
    @Test
    public void deleteMeetingWithSuccess(){
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }
    @Test
    public void FilterByHoursWithSuccess(){
        List<Meeting> FilteredList = service.filterByHours("12:30","13:30");
        assertSame("12:30", FilteredList.get(0).getStartHour());
    }
    @Test
    public void FilterByPlaceWithSuccess(){
        List<Meeting> FilteredList = service.filterByPlace("Aragorn");
        assertSame("Aragorn", FilteredList.get(0).getPlace());
    }
    @Test
    public void FilterByDateWithSuccess(){
        List<Meeting> FilteredList = service.filterByDate("28-04-2020");
        assertEquals("28-04-2020",FilteredList.get(0).getDate());
    }
    @Test
    public void concatStringMeetingDescWithSuccess(){
        Meeting meetingToTest = new Meeting("Test","Test","Sam","12:30","13:30","28-4-2020","Test@gmail.com","#ff000000");
        assertEquals(service.concatStringMeetingDEsc(meetingToTest), "Test - Sam - Test");
    }
    @Test
    public void EmailVerificationWithSuccess(){
        assertTrue(service.emailVerification("test.ok@gmail.com"));
        assertFalse(service.emailVerification("testmail.fr"));
        assertFalse(service.emailVerification("test@gmailfr"));
    }
    @Test
    public void dateVerificationWithSuccess(){
        assertFalse(service.dateVerification("1-04-2020"));
        assertFalse(service.dateVerification("01-3-2020"));
        assertFalse(service.dateVerification("28-05-20"));
        assertTrue(service.dateVerification("25-11-2020"));
    }
    @Test
    public void hourVerificationWithSuccess(){
        assertFalse(service.hourVerification("2:30"));
        assertFalse(service.hourVerification("12:6"));
        assertFalse(service.hourVerification("24:30"));
        assertTrue(service.hourVerification("12:30"));
    }
    @Test
    public void meetingVerificationWithSuccess(){
        assertFalse(service.meetingVerificationBis("Gandalf","28-04-2020","12:31","13:45"));
        assertTrue(service.meetingVerificationBis("Gandalf","30-06-2020","19:30","23:45"));
    }

}