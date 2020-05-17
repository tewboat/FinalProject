package com.example.finalproject.domain;

import androidx.annotation.Nullable;

import com.example.finalproject.repository.HashCodeGenerator;

public class Step {
    String text;
    String id;
    String description;
    int isDone;
    public Step(@Nullable String id, String text, String description, int isDone){
        this.text = text;
        this.description = description;
        this.isDone = isDone;

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

    public void changeIsDone(){
        switch(this.isDone){
            case 0:
                this.isDone = 1;
                break;
            case 1:
                this.isDone = 0;
                break;
        }
    }
}
