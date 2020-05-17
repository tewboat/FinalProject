package com.example.finalproject.view.fragments.DialogFragments;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.example.finalproject.domain.Goal;
import com.example.finalproject.domain.Habit;
import com.example.finalproject.domain.Step;
import com.example.finalproject.domain.Todo;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.repository.notification.NotificationReceiver;

public class AlertDialog extends DialogFragment {

    private Object object;
    private int fragmentID;
    private View view;

    public AlertDialog(Object object, int fragmentID, View view){
        this.object = object;
        this.fragmentID = fragmentID;
        this.view = view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure you want to delete?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Interactor interactor = new Interactor(getActivity());
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                        Intent intent = new Intent(getActivity(), NotificationReceiver.class);
                        switch(fragmentID){
                            case 0:
                                Todo todo = (Todo)object;
                                interactor.removeTodoFromDb(todo);
                                PendingIntent todoPendingIntent = PendingIntent.getBroadcast(getActivity(), todo.getRequestCode(), intent, 0);
                                if (alarmManager != null) {
                                    alarmManager.cancel(todoPendingIntent);
                                }
                                Navigation.findNavController(view).popBackStack();
                                break;
                            case 1:
                                Habit habit = (Habit) object;
                                interactor.removeHabitFromDb(habit);
                                PendingIntent habitPendingIntent = PendingIntent.getBroadcast(getActivity(), habit.getRequestCode(), intent, 0);
                                if (alarmManager != null) {
                                    alarmManager.cancel(habitPendingIntent);
                                }
                                Navigation.findNavController(view).popBackStack();
                                break;
                            case 2:
                                interactor.removeGoalFromDb((Goal)object);
                                Navigation.findNavController(view).popBackStack();
                                break;
                            case 3:
                                interactor.removeStepFromDb((Step)object);
                                Navigation.findNavController(view).popBackStack();
                                break;
                        }
                    }
                });
        return builder.create();
    }

}
