package com.example.finalproject.domain;

import androidx.annotation.NonNull;

import com.example.finalproject.repository.HashCodeGenerator;

public class Todo {
    private String text;
    private int isComplete;
    private int requestCode;
    private String reminderTime;
    private String id;


    public Todo(String id, String text, int isComplete, String reminderTime, int requestCode){
        this.text = text;
        this.isComplete = isComplete;
        this.reminderTime = reminderTime;
        this.requestCode = requestCode;
        if(id == null){
            this.id = new HashCodeGenerator().generateID();
        }else{
            this.id = id;
        }
    }

    public String getId() {
        return id;
    }

    public String getText(){return text;}

    public int getIsComplete(){return isComplete;}

    public String getReminderTime() {
        return reminderTime;
    }

    public void setText(String text){this.text = text;}

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
    }


    @NonNull
    @Override
    public String toString() {
        return text;
    }




}
