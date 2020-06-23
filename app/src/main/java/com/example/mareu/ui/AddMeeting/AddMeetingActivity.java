package com.example.mareu.ui.AddMeeting;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sayantan.advancedspinner.MultiSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddMeetingActivity extends AppCompatActivity {

    private MeetingApiService mApiService = DI.getMeetingApiService();

    private final List<MeetingRoom> mMeetingRooms = mApiService.getMeetingRooms();
    private final List<String> mMeetingEmail = mApiService.getMeetingEmail();


    @BindView(R.id.MeetingColor)
    ImageView mMeetingColor;
    @BindView(R.id.MeetingNameLyt)
    TextInputLayout mMeetingName;
    @BindView(R.id.MeetingSubjectLyt)
    TextInputLayout mMeetingSubject;
    @BindView(R.id.PlaceSpinner)
    Spinner mMeetingPlaceSpinner;
    @BindView(R.id.MeetingHourLyt)
    TextInputLayout mMeetingHours;
    @BindView(R.id.MeetingDateLyt)
    TextInputLayout mMeetingDate;
    @BindView(R.id.MeetingHourEditText)
    TextInputEditText mMeetingHour;
    @BindView(R.id.MeetingDateEditText)
    TextInputEditText mMeetingTime;
    @BindView(R.id.CreateMeetingBtn)
    MaterialButton mCreateBtn;
    String mPlace;
    @BindView(R.id.OpenDateDialog)
    ImageButton mOpnDialog;
    @BindView(R.id.OpenTimeDialog)
    ImageButton mTimeDialog;
    @BindView(R.id.emailSpinner)
    MultiSpinner mEmailSpinner;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private String mColor,mEndHour,mStartHour;
    private ArrayList<String> mContributors;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        mApiService = DI.getMeetingApiService();
        ButterKnife.bind(this);
        setMeetingPlaceSpinner();
        setEmailSpinner();
        mOpnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog();
            }
        });
        mTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeEndDialog();
                TimeDialog();
            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mApiService.meetingVerificationBis(getSelectedPlace(),mMeetingDate.getEditText().getText().toString(),mStartHour,mEndHour)){
                    Toast.makeText(getApplicationContext(), R.string.error_room, Toast.LENGTH_SHORT).show();

                if (!EditTextVerification()) {
                    Toast.makeText(getApplicationContext(),R.string.editText_error , Toast.LENGTH_SHORT).show();

                }}
                 else {
                    mApiService.addMeeting(CreateMeeting());
                    AddMeetingActivity.this.finish(); }
            }
        });
    }


    public Boolean EditTextVerification() {
         if (mApiService.emptyTextVerification(mMeetingName.getEditText().getText().toString()) |
                mApiService.emptyTextVerification(mMeetingSubject.getEditText().getText().toString())|
               !mApiService.hourVerification(mStartHour) |
                 !mApiService.hourVerification(mEndHour)|
                !mApiService.dateVerification(mMeetingDate.getEditText().getText().toString()))
                 {
             return false;
         }else return true;
    }

    public Meeting CreateMeeting() {
        Meeting meeting = new Meeting(
                mMeetingName.getEditText().getText().toString().trim(),
                mMeetingSubject.getEditText().getText().toString().trim(),
                mPlace = getSelectedPlace(),
                mStartHour,
                mEndHour,
                mMeetingDate.getEditText().getText().toString().trim(),
                getSelectedEmail(),
                getRoomColor()
        );
        return meeting;
    }


    public void setMeetingPlaceSpinner() {
        ArrayAdapter<MeetingRoom> adapter = new ArrayAdapter<MeetingRoom>(this, android.R.layout.simple_spinner_dropdown_item, mMeetingRooms);
        mMeetingPlaceSpinner.setAdapter(adapter);
    }

    public ArrayList<String> getSelectedEmail(){
        mContributors = mEmailSpinner.getSelectedItems();
        return mContributors;
    }

    public String getRoomColor(){
        MeetingRoom mRoom = (MeetingRoom) mMeetingPlaceSpinner.getSelectedItem();
        mColor = mRoom.getRoomColor();
        return mColor;
    }

    public String getSelectedPlace() {
        MeetingRoom mRoom = (MeetingRoom) mMeetingPlaceSpinner.getSelectedItem();
        String mPlace = mRoom.getName();
        mColor = mRoom.getRoomColor();
        return mPlace;
    }

    public void OpenDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mMeetingTime.setText(String.format("%02d-%02d-%d", dayOfMonth ,(month + 1) , year));
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void TimeDialog() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                mStartHour = String.format("%02d:%02d" ,hourOfDay , minute);
                mMeetingHour.setText(mStartHour);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void timeEndDialog(){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mEndHour = String.format("%02d:%02d" ,hourOfDay , minute);
                mMeetingHour.setText(mStartHour + " - " +  mEndHour);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void setEmailSpinner(){
        mEmailSpinner.setSpinnerList(mMeetingEmail);
    }


}
