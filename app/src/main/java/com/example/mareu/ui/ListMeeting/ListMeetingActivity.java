package com.example.mareu.ui.ListMeeting;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.ListFilterByDateEvent;
import com.example.mareu.events.ListFilterByHoursEvent;
import com.example.mareu.events.ListFilterByPLaceEvent;
import com.example.mareu.events.ResetFilterEvent;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.ui.AddMeeting.AddMeetingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.List;

public class ListMeetingActivity extends AppCompatActivity {


    private MeetingApiService mApiService = DI.getMeetingApiService();
    private final List<MeetingRoom> mMeetingRooms = mApiService.getMeetingRooms();

    private int mYear, mMonth, mDay, mHour, mMinute;

    private String mDate, mPlace,mStartTime,mEndTime;
    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mApiService = DI.getMeetingApiService();
        setSupportActionBar(toolbar);
        MeetingFragment fragment = new MeetingFragment();
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        mTransaction.add(R.id.contentMain, fragment);
        mTransaction.commit();
        FloatingActionButton mAddMeetingBTn = findViewById(R.id.addMeetingBtn);
        mAddMeetingBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddMeetingActivity();
            }
        });
    }
    private void openAddMeetingActivity() {
        Intent intent = new Intent(this, AddMeetingActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sorting_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SpinnerPlace:
                meetingRoomDialog();
                return true;
            case R.id.sortingByHoursItem:
                TimeEndDialog();
                timeStartDialog();
                return true;
            case R.id.FilterByDate:
                OpenDateDialog();
                return true;
            case R.id.ResetFilter:
                EventBus.getDefault().post(new ResetFilterEvent());
                Toast.makeText(this, R.string.reset_filter, Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void OpenDateDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mDate = String.format("%02d-%02d-%d",dayOfMonth,(month + 1),year);
                EventBus.getDefault().post(new ListFilterByDateEvent(mDate));
                Toast.makeText(getApplicationContext(), R.string.filter_by_date, Toast.LENGTH_SHORT).show();

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void timeStartDialog() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mStartTime = String.format ("%02d:%02d",hourOfDay , minute);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void TimeEndDialog() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mEndTime = String.format ("%02d:%02d",hourOfDay , minute);
                EventBus.getDefault().post(new ListFilterByHoursEvent(mStartTime,mEndTime));
                Toast.makeText(getApplicationContext(), R.string.filter_by_hours, Toast.LENGTH_SHORT).show();
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();}

    public void meetingRoomDialog(){
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ListMeetingActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.spinner_room_dialog,null);
        mBuilder.setTitle("MeetingRoom Filter");
        final Spinner mSpinner = (Spinner) mView.findViewById(R.id.RoomSpinnerDialog);
        ArrayAdapter<MeetingRoom> adapter = new ArrayAdapter<MeetingRoom>(ListMeetingActivity.this,android.R.layout.simple_spinner_item,mMeetingRooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              mPlace = mSpinner.getSelectedItem().toString();
              EventBus.getDefault().post(new ListFilterByPLaceEvent(mPlace));
              dialog.dismiss();
              Toast.makeText(getApplicationContext(), R.string.filter_by_place, Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
