package com.example.finalproject.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.repository.HashCodeGenerator;

public class Goal {

    int stepsDone;
    String text;
    int progress;
    final String id;
    int colorID;
    public Goal(@Nullable String id, @NonNull String text, int progress, int stepsDone, int colorID){
        this.text = text;
        this.stepsDone = stepsDone;
        this.progress = progress;
        this.colorID = colorID;
        if(id == null){
            this.id = new HashCodeGenerator().generateID();
        }else{
            this.id = id;
        }

    }

    public int getStepsDone() {return stepsDone;}

    public String getText() {
        return text;
    }

    public int getProgress() {
        return progress;
    }

    public String getStringProgress(){
        return progress + "%";
    }

    public String getID() {
        return id;
    }

    public void setStepsDone(int stepsDone) {
        this.stepsDone = stepsDone;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void updateProgress(int stepsArrayListSize) {
        try{
        this.progress = (stepsDone * 100) / stepsArrayListSize;}
        catch (ArithmeticException e){
            this.progress = 0;
        }
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public int getColorID() {
        return colorID;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "stepsDone=" + stepsDone +
                ", text='" + text + '\'' +
                ", progress=" + progress +
                ", id='" + id + '\'' +
                ", colorID=" + colorID +
                '}';
    }

    public void updateStepsDone(int stepsDone){
        this.stepsDone += stepsDone;
    }
}
