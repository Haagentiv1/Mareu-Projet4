package com.example.mareu.ui.ListMeeting;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.ui.meetingDialog.MeetingViewDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;
    private MeetingApiService mApiService = DI.getMeetingApiService();
    public MeetingAdapter(List<Meeting> items){
        mMeetings = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_meeting,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Meeting meeting = mMeetings.get(position);
        holder.mRoomColor.setColorFilter(Color.parseColor(meeting.getMeetingColor()));
        holder.mMeetingDesc.setText(mApiService.concatStringMeetingDEsc(meeting));
        holder.mContributors.setText(mApiService.concatEmail(meeting));
        holder.mDeleteBtn.setImageResource(R.drawable.ic_delete_black_24dp);
        holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
        holder.mOpenDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MeetingViewDialog.class);
                intent.putExtra("Meeting", meeting);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mRoomColor;
        public TextView mMeetingDesc;
        public TextView mContributors;
        public ImageButton mDeleteBtn;
        public ConstraintLayout mOpenDialogBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoomColor = itemView.findViewById(R.id.item_list_RoomColor);
            mMeetingDesc = itemView.findViewById(R.id.item_list_MeetingName);
            mContributors = itemView.findViewById(R.id.item_list_MeetingContributor);
            mDeleteBtn = itemView.findViewById(R.id.item_list_delete_button);
            mOpenDialogBtn = itemView.findViewById(R.id.itemLayout);
        }
    }
}
