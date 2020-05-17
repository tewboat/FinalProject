package com.example.finalproject.view.fragments.HabitsFragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.finalproject.R;
import com.example.finalproject.domain.Habit;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.interfaces.OnSetColor;
import com.example.finalproject.repository.notification.NotificationReceiver;
import com.example.finalproject.view.fragments.DialogFragments.ColorPickerFragment;
import com.example.finalproject.view.fragments.DialogFragments.TimePickerFragment;

import java.util.Calendar;
import java.util.Random;

public class HabitCreationFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, OnSetColor {

    private ImageButton cancel, enter;
    private Button cancelAlarm;
    private EditText habitName;
    private Interactor interactor;
    private TextView pickedTime, timePicker, colorPicker;
    private LinearLayout colorPickerLayout, headerLayout;
    private View pickedColor;
    private int colorID = R.color.colorMaterialGreen;
    private Calendar calendar;
    private int requestCode = new Random().nextInt();
    private boolean timePicked = false;

    public HabitCreationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.habit_creation_fragment, container, false);
        habitName = view.findViewById(R.id.habit_creation_fragment_habit_name_edit_text);
        cancel = view.findViewById(R.id.habit_creation_fragment_cancel_button);
        enter = view.findViewById(R.id.habit_creation_fragment_enter_button);
        pickedTime = view.findViewById(R.id.habit_creation_picked_time);
        timePicker = view.findViewById(R.id.habit_creation_pick_time);
        colorPicker = view.findViewById(R.id.habit_creation_color_picker);
        colorPickerLayout = view.findViewById(R.id.habit_creation_color_picker_layout);
        pickedColor = view.findViewById(R.id.habit_creation_chosen_color);
        headerLayout = view.findViewById(R.id.habit_creation_header_layout);
        cancelAlarm = view.findViewById(R.id.habit_creation_fragment_cancel_alarm);
        interactor = new Interactor(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnClickListeners();
    }

    public void setOnClickListeners() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (habitName.getText() != null && !habitName.getText().toString().equals("")) {
                    Habit habit = new Habit(null, habitName.getText().toString(),
                            colorID, pickedTime.getText().toString(), requestCode);
                    startAlarm(calendar, habitName.getText().toString());
                    interactor.insertHabitIntoDb(habit);
                    Navigation.findNavController(getView()).popBackStack();
                }
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerFragment((TimePickerDialog.OnTimeSetListener) HabitCreationFragment.this).show(getActivity().getSupportFragmentManager(), "TIME_PICKER_FRAGMENT");
            }
        });

        colorPickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerFragment(HabitCreationFragment.this).show(getActivity().getSupportFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerFragment(HabitCreationFragment.this).show(getActivity().getSupportFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePicked) {
                    calendar = null;
                    timePicked = false;
                    pickedTime.setText("");
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
        String time = mHour + ":" + mMinute;
        pickedTime.setText(time);
        timePicked = true;
    }

    @Override
    public void onSetColor(int colorID) {
        headerLayout.setBackgroundColor(getResources().getColor(colorID));
        pickedColor.setBackgroundColor(getResources().getColor(colorID));
        this.colorID = colorID;
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
