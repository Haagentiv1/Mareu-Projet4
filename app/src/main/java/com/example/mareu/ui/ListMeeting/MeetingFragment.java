package com.example.mareu.ui.ListMeeting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.events.ListFilterByDateEvent;
import com.example.mareu.events.ListFilterByHoursEvent;
import com.example.mareu.events.ListFilterByPLaceEvent;
import com.example.mareu.events.ResetFilterEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MeetingFragment extends Fragment {

    private MeetingApiService mApiService;
    private List<Meeting> mMeeting;
    private RecyclerView mRecyclerView;
    private MeetingAdapter mAdapter;
    private RecyclerView.LayoutManager mManager;

    public static MeetingFragment NewInstance() {
        MeetingFragment fragment = new MeetingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting,container,false);
        mRecyclerView = view.findViewById(R.id.list_meeting);
        mManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new MeetingAdapter(mApiService.getMeetings());
        mRecyclerView.setAdapter(mAdapter);
        initList();
        return view;
    }
    private void initList(){
        mMeeting = mApiService.getMeetings();
        mRecyclerView.setAdapter(new MeetingAdapter(mMeeting));
    }
    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event){
        mApiService.deleteMeeting(event.meeting);
        initList();
    }
    @Subscribe
    public void onFilterByDate(ListFilterByDateEvent event){
        mRecyclerView.setAdapter(new MeetingAdapter(mApiService.filterByDate(event.Date)));
    }
    @Subscribe
    public void onFilterByHours(ListFilterByHoursEvent event){
        mRecyclerView.setAdapter(new MeetingAdapter(mApiService.filterByHours(event.startHour,event.endHour)));
    }
    @Subscribe
    public void onResetFilter(ResetFilterEvent event){
        initList();
    }

    @Subscribe
    public void onFilterByPlace(ListFilterByPLaceEvent event){
        mRecyclerView.setAdapter(new MeetingAdapter(mApiService.filterByPlace(event.mPlace)));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}