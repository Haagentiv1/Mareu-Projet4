package com.example.mareu.ui.meetingDialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingViewDialog extends AppCompatActivity {

    @BindView(R.id.MeetingRoomColor)
    ImageView mMeetingColor;
    @BindView(R.id.MeetingName)
    TextView mMeetingName;
    @BindView(R.id.meetingSubjectItem)
    TextView mMeetingSubject;
    @BindView(R.id.meetingDate)
    TextView mMeetingDate;
    @BindView(R.id.start_hour)
    TextView mMeetingStartHour;
    @BindView(R.id.end_hour)
    TextView mMeetingEndHour;
    @BindView(R.id.meetingParticipantsItem)
    TextView mMeetingEmail;
    @BindView(R.id.meetingRoomItem)
    TextView mMeetingRoom;


    private Meeting mMeeting;
    private MeetingApiService mApiService = DI.getMeetingApiService();

    private void getMeeting() {

        Intent intent = getIntent();
        if (intent != null) {
            mMeeting = intent.getParcelableExtra("Meeting");
            setLayoutDrawable();
        } else {
            Toast.makeText(this, "null object", Toast.LENGTH_LONG);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail);
        ButterKnife.bind(this);
        getMeeting();
    }

    public void setLayoutDrawable() {
        mMeetingColor.setColorFilter(Color.parseColor(mMeeting.getMeetingColor()));
        mMeetingName.setText(mMeeting.getName());
        mMeetingSubject.setText(mMeeting.getSubject());
        mMeetingRoom.setText(mMeeting.getPlace());
        mMeetingDate.setText(mMeeting.getDate());
        mMeetingStartHour.setText(mMeeting.getStartHour());
        mMeetingEndHour.setText(mMeeting.getEndHour());
        mMeetingEmail.setText(mApiService.concatEmail(mMeeting));
    }








}
