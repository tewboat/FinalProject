package com.example.finalproject.domain;

import androidx.annotation.Nullable;

import com.example.finalproject.repository.HashCodeGenerator;

public class Habit {

    private  String text;
    private String id;
    private int color;
    private int requestCode;
    private String remindTime;

    public Habit(@Nullable String id, String text, int color, @Nullable String remindTime, int requestCode){
        this.text = text;
        this.color = color;
        this.requestCode = requestCode;
        this.remindTime = remindTime;
        if(id == null){
            this.id = new HashCodeGenerator().generateID();
        }else{
            this.id = id;
        }
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }
}
