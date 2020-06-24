package com.example.mareu.model;


public class MeetingRoom {

    private Integer id;

    private String name;

    private String roomColor;


    public MeetingRoom(Integer id , String name, String roomColor){
        this.id = id;
        this.name = name;
        this.roomColor = roomColor;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomColor() { return roomColor; }

    public void setRoomColor(String roomColor) { this.roomColor = roomColor;}


    @Override
    public String toString(){
        return name;
    }
}
