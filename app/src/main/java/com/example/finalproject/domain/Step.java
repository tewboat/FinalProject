package com.example.finalproject.domain;

import androidx.annotation.Nullable;

import com.example.finalproject.repository.HashCodeGenerator;

public class Step {
    private String text;
    private String id;
    private String description;
    private int isDone;
    private int mColorID;
    public Step(@Nullable String id, String text, String description, int isDone, int colorId){
        this.text = text;
        this.description = description;
        this.isDone = isDone;
        this.mColorID = colorId;

        if(id == null){
            this.id = new HashCodeGenerator().generateID();
        }else{
            this.id = id;
        }
    }
    public String getText(){return text;}

    public String getId() {
        return id;
    }

    public int getIsDone(){return isDone;}

    public String getDescription() {
        return description;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }

    public int getColorId() {
        return mColorID;
    }

    public void setColorId(int ColorID) {
        this.mColorID = ColorID;
    }
}
