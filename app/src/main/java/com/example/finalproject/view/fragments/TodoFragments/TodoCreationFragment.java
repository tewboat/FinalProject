package com.example.finalproject.view.fragments.TodoFragments;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.finalproject.R;
import com.example.finalproject.domain.Todo;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.repository.notification.NotificationHelper;
import com.example.finalproject.repository.notification.NotificationReceiver;
import com.example.finalproject.view.fragments.DialogFragments.TimePickerFragment;

import java.util.Calendar;
import java.util.Random;


public class TodoCreationFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private Button cancelAlarm;
    private ImageButton create, cancel;
    private EditText todoName;
    private Interactor interactor;
    private TextView pickedTime, timePicker;
    private boolean timePicked = false;
    private Calendar calendar;
    private Switch mPriority;
    private int requestCode =  new Random().nextInt();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_creation_fragment, container, false);
        cancelAlarm = view.findViewById(R.id.todo_creation_fragment_cancel_alarm);
        cancel = view.findViewById(R.id.todo_creation_fragment_cancel_button);
        create = view.findViewById(R.id.todo_creation_fragment_enter_button);
        todoName = view.findViewById(R.id.todo_creation_fragment_todo_name_edit_text);
        pickedTime = view.findViewById(R.id.todo_creation_picked_time);
        timePicker = view.findViewById(R.id.todo_creation_time_picker);
        mPriority = view.findViewById(R.id.todo_creation_high_priority);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        interactor = new Interactor(getActivity());
        setOnClickListeners();
    }

    public void setOnClickListeners() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todoName.getText() != null && !todoName.getText().toString().equals("")) {
                    Todo todo = new Todo(null, todoName.getText().toString(), 0, pickedTime.getText().toString(), requestCode, mPriority.isChecked() ? 1 : 0);
                    interactor.insertTodoIntoDb(todo);
                    if (timePicked) {
                        startAlarm(calendar, todoName.getText().toString());
                    }
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.hideSoftInputFromWindow(create.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    Navigation.findNavController(getView()).popBackStack();
                }
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerFragment(TodoCreationFragment.this).show(getActivity().getSupportFragmentManager(), "TIME_PICKER_FRAGMENT");
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePicked) {
                    pickedTime.setText("");
                    calendar = null;
                    timePicked = false;
                }
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String mHour;
        String mMinute;
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (hourOfDay > 9) {
            mHour = hourOfDay + "";
        } else {
            mHour = "0" + hourOfDay;
        }
        if (minute > 9) {
            mMinute = "" + minute;
        } else {
            mMinute = "0" + minute;
        }
        pickedTime.setText(mHour + ":" + mMinute);
        timePicked = true;
    }

    private void startAlarm(Calendar c, String title) {
        if (calendar != null) {
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), NotificationReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("id", requestCode);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

}
