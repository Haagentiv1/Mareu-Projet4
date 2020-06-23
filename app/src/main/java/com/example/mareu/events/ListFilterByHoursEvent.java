package com.example.mareu.events;

public class ListFilterByHoursEvent {

    public String startHour,endHour;
    public ListFilterByHoursEvent(String startHour,String endHour){
        this.startHour = startHour;
        this.endHour = endHour;
    }
}
