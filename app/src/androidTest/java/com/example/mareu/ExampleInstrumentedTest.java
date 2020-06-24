package com.example.mareu;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.ui.ListMeeting.ListMeetingActivity;
import com.example.mareu.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private ListMeetingActivity mActivity;
    private static int ITEMS_COUNT =10;

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule = new ActivityTestRule<>(ListMeetingActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem(){
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT));
        // Given : We remove the element at position 2.
        onView(ViewMatchers.withId(R.id.list_meeting)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT - 1));
        // then : the number of element is 9
    }

    @Test
    public void myMeetingList_addMeetingAction_shouldAddItem(){
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT));
        //Given : We Create a Meeting
        onView(ViewMatchers.withId(R.id.addMeetingBtn)).perform(click());
        //when perform a click on a create icon
        onView(ViewMatchers.withId(R.id.edit_MeetingNameEditText)).perform(typeText("Test1"));
        onView(ViewMatchers.withId(R.id.MeetingSubjectEditText)).perform(typeText("Test1"));
        onView(ViewMatchers.withId(R.id.MeetingDateEditText)).perform(scrollTo()).perform(typeText("20-04-2020"));
        onView(ViewMatchers.withId(R.id.OpenTimeDialog)).perform(scrollTo(), click());
        onView(allOf(withClassName(Matchers.equalTo(TimePicker.class.getName())))).perform(PickerActions.setTime(10,30));
        onView(allOf(withId(android.R.id.button1), withText("OK") ,isDisplayed())).perform(scrollTo(), click());
        onView(allOf(withClassName(Matchers.equalTo(TimePicker.class.getName())))).perform(PickerActions.setTime(11,30));
        onView(allOf(withId(android.R.id.button1), withText("OK") ,isDisplayed())).perform(scrollTo(), click());
        onView(allOf(withId(R.id.emailSpinner))).perform(click());
        onData(anything()).inAdapterView(allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")))).atPosition(3).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("OK"),isDisplayed())).perform(scrollTo(),click());
        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.CreateMeetingBtn)).perform(click());
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT + 1));
        // then : the number of element is 11
    }
    @Test
    public void myMeetingList_FilterByPlace_shouldFilterTheNeighbourList(){
        //Given : We filter the meeting list by place
        onView(ViewMatchers.withId(R.id.sortingBtn)).perform(click());
        //When perform a click on the place filter
        onView(allOf(withId(R.id.title), withText("Par lieux"), isDisplayed())).perform(click());
        onView(ViewMatchers.withId(R.id.RoomSpinnerDialog)).perform(click());
        onData(anything()).inAdapterView(allOf(withId(R.id.select_dialog_listview))).atPosition(4).perform(click());
        onView(ViewMatchers.withId(android.R.id.button1)).perform(scrollTo()).perform(click());
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(1));
        //Then : The number of element is 1
    }
    @Test
    public void myMeetingList_FilterByHours_shouldFilterTheNeighbourList(){
        //Given : We filter the meeting list by hour
        onView(ViewMatchers.withId(R.id.sortingBtn)).perform(click());
        //When perform a click on the hour filter
        onView(allOf(withId(R.id.title), withText("Par heure"), isDisplayed())).perform(click());
        onView(allOf(withClassName(Matchers.equalTo(TimePicker.class.getName())))).perform(PickerActions.setTime(12,30));
        onView(allOf(withId(android.R.id.button1), withText("OK") ,isDisplayed())).perform(scrollTo(), click());
        onView(allOf(withClassName(Matchers.equalTo(TimePicker.class.getName())))).perform(PickerActions.setTime(13,30));
        onView(allOf(withId(android.R.id.button1), withText("OK") ,isDisplayed())).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(9));
        //Then : The number of element is 9
    }
    @Test
    public void myMeetingList_FilterByDate_shouldFilterTheNeighbourList(){
        //Given : we filter the meeting list by date
        onView(ViewMatchers.withId(R.id.sortingBtn)).perform(click());
        //When perform a click on the date filter button
        onView(allOf(withId(R.id.title), withText("Par date"), isDisplayed())).perform(click());
        onView(allOf(withClassName(Matchers.equalTo(DatePicker.class.getName())))).perform(PickerActions.setDate(2020,04,28));
        onView(allOf(withId(android.R.id.button1), withText("OK") ,isDisplayed())).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(1));
        //Then : The number of element is 1
    }

    @Test
    public void myMeetingList_ResetFilterAction_shouldResetFilter(){
        //Given : we had a first filter
        onView(ViewMatchers.withId(R.id.sortingBtn)).perform(click());
        onView(allOf(withId(R.id.title), withText("Par date"), isDisplayed())).perform(click());
        onView(allOf(withClassName(Matchers.equalTo(DatePicker.class.getName())))).perform(PickerActions.setDate(2020,04,28));
        onView(allOf(withId(android.R.id.button1), withText("OK") ,isDisplayed())).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(1));
        //When perform on a the reset filter button
        onView(ViewMatchers.withId(R.id.sortingBtn)).perform(click());
        onView(allOf(withId(R.id.title), withText("réinitialiser les filtres"), isDisplayed())).perform(click());
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT));
        //them : the number of element is 10
    }
    @Test
    public void myMeetingList_addMeetingAction_shouldNotAddMeetingInTheSameTimeAndRoomSlot(){
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT));
        //Given : We Create a Meeting
        onView(ViewMatchers.withId(R.id.addMeetingBtn)).perform(click());
        //when perform a click on a create icon
        onView(ViewMatchers.withId(R.id.edit_MeetingNameEditText)).perform(typeText("Test1"));
        onView(ViewMatchers.withId(R.id.MeetingSubjectEditText)).perform(typeText("Test1"));
        onView(ViewMatchers.withId(R.id.MeetingDateEditText)).perform(scrollTo()).perform(typeText("28-04-2020"));
        onView(ViewMatchers.withId(R.id.OpenTimeDialog)).perform(scrollTo(), click());
        onView(allOf(withClassName(Matchers.equalTo(TimePicker.class.getName())))).perform(PickerActions.setTime(12,40));
        onView(allOf(withId(android.R.id.button1), withText("OK") ,isDisplayed())).perform(scrollTo(), click());
        onView(allOf(withClassName(Matchers.equalTo(TimePicker.class.getName())))).perform(PickerActions.setTime(14,30));
        onView(allOf(withId(android.R.id.button1), withText("OK") ,isDisplayed())).perform(scrollTo(), click());
        onView(allOf(withId(R.id.emailSpinner))).perform(click());
        onData(anything()).inAdapterView(allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")))).atPosition(3).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("OK"),isDisplayed())).perform(scrollTo(),click());
        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.CreateMeetingBtn)).perform(click());
        pressBack();
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT));
    }
    @Test
    public void myMeetingList_clickOnMeeting_shouldShowMeetingDialog(){
        onView(ViewMatchers.withId(R.id.list_meeting)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(ViewMatchers.withId(R.id.meeting_detail_activity)).check(matches(isDisplayed()));
    }
    @Test
    public void myMeetingList_clickOnMeeting_shouldShowTheRightMeeting(){
        onView(ViewMatchers.withId(R.id.list_meeting)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(ViewMatchers.withId(R.id.MeetingName)).check(matches(withText("Réunion A")));

    }
}
