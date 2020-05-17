package com.example.finalproject.view.fragments.TodoFragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.finalproject.R;
import com.example.finalproject.domain.Todo;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.repository.notification.NotificationReceiver;
import com.example.finalproject.view.fragments.DialogFragments.AlertDialog;
import com.example.finalproject.view.fragments.DialogFragments.TimePickerFragment;

import java.util.Calendar;

public class TodoInfoFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {
    private Todo todo;
    private ImageButton cancel, deleteTask;
    private EditText todoEditText;
    private Interactor interactor;
    private final int fragmentID = 0;
    private ImageButton isComplete;
    private TextView pickedTime, timePicker;
    private Calendar calendar;
    private Button deleteAlarm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.todo_edit_mode, container, false);
        cancel = view.findViewById(R.id.todo_edit_fragment_cancel_button);
        todoEditText = view.findViewById(R.id.todo_edit_fragment_edit_text_view);
        deleteTask = view.findViewById(R.id.todo_info_fragment_delete_button);
        isComplete = view.findViewById(R.id.todo_complete_button);
        pickedTime = view.findViewById(R.id.todo_info_picked_time);
        timePicker = view.findViewById(R.id.todo_info_time_picker);
        deleteAlarm = view.findViewById(R.id.todo_info_cancel_alarm);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String todoID = getArguments().getString("TODO_ID");
        interactor = new Interactor(getActivity());
        this.todo = interactor.getTodo(todoID);
        todoEditText.setText(todo.getText());
        if(todo.getIsComplete() == 1){
            isComplete.setImageResource(R.drawable.ic_check_box_ticked);
        }
        if(todo.getReminderTime() != null){
            pickedTime.setText(todo.getReminderTime());
        }
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(todo, fragmentID, getView()).show(getActivity().getSupportFragmentManager(), "ALERT_DIALOG");
            }
        });

        isComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (todo.getIsComplete()){
                    case 0:
                        todo.setIsComplete(1);
                        isComplete.setImageResource(R.drawable.ic_check_box_ticked);
                        break;
                    case 1:
                        todo.setIsComplete(0);
                        isComplete.setImageResource(R.drawable.ic_check_box_outline);
                }
                interactor.updateTodoDb(todo);
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerFragment((TimePickerDialog.OnTimeSetListener)TodoInfoFragment.this).show(getActivity().getSupportFragmentManager(), "TIME_PICKER_FRAGMENT");
            }
        });

        deleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickedTime.setText("");
                todo.setReminderTime("");
                interactor.updateTodoDb(todo);
                cancelAlarm();
            }
        });
    }

    @Override
    public void onPause() {
        Log.d("TodoInfoFragment", "onPause");
        updateTodo();
        interactor.updateTodoDb(todo);
        super.onPause();
    }

    private void updateTodo() {
        todo.setText(todoEditText.getText().toString());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String mHour;
        String mMinute;
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if(hourOfDay > 9){
            mHour = hourOfDay + "";
        }else{
            mHour = "0" + hourOfDay;
        }
        if (minute > 9){
            mMinute = "" + minute;
        }else{
            mMinute = "0" + minute;
        }
        String time = mHour + ":" + mMinute;
        pickedTime.setText(time);
        todo.setReminderTime(pickedTime.getText().toString());
        interactor.updateTodoDb(todo);
        startAlarm(calendar, todo.getText());
    }

    private void startAlarm(Calendar c, String title) {
        if (calendar != null) {
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), NotificationReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("id", todo.getRequestCode());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), todo.getRequestCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), todo.getRequestCode(), intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
