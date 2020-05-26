package com.example.finalproject.view.fragments.HabitsFragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.example.finalproject.view.fragments.DialogFragments.AlertDialog;
import com.example.finalproject.view.fragments.DialogFragments.ColorPickerFragment;
import com.example.finalproject.view.fragments.DialogFragments.TimePickerFragment;

import java.util.Calendar;

public class HabitInfoFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, OnSetColor {

    private Button cancelAlarm;
    private EditText habitNameEditMode;
    private TextView pickTime, colorPicker;
    private String habitID;
    private Interactor interactor;
    private ImageButton deleteHabit, mSaveButton;
    private TextView pickedTime;
    private Habit habit;
    private View pickedColor;
    private LinearLayout colorPickerLayout, headerLayout;
    private Calendar calendar;


    public HabitInfoFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.habit_edit_mode, container, false);
        deleteHabit = view.findViewById(R.id.habit_info_fragment_delete_button);
        pickedTime = view.findViewById(R.id.habit_picked_time);
        habitNameEditMode = view.findViewById(R.id.habit_info_fragment_habit_name_text_view_edit_mode);
        mSaveButton = view.findViewById(R.id.habit_info_fragment_save_button);
        pickTime = view.findViewById(R.id.pick_time);
        pickedColor = view.findViewById(R.id.habit_info_chosen_color);
        colorPickerLayout = view.findViewById(R.id.habit_info_color_picker_layout);
        colorPicker = view.findViewById(R.id.habit_info_color_picker);
        headerLayout = view.findViewById(R.id.habit_info_header_layout);
        cancelAlarm = view.findViewById(R.id.habit_info_fragment_cancel_alarm);
        habitID = getArguments().getString("HABIT_ID");
        interactor = new Interactor(getActivity());
        habit = interactor.getHabit(habitID);
        setValues();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnClickListeners();
    }

    private void setValues() {
        habitNameEditMode.setText(habit.getText());
        headerLayout.setBackgroundColor(getResources().getColor(habit.getColor()));
        pickedColor.setBackgroundColor(getResources().getColor(habit.getColor()));
        pickedTime.setText(habit.getRemindTime());
    }

    private void setOnClickListeners() {

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHabit();
                interactor.updateHabitsDb(habit);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(mSaveButton.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        deleteHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(habit, AlertDialog.HABIT_FRAGMENT_ID, getView()).show(getActivity().getSupportFragmentManager(), "ALERT_DIALOG");
            }
        });

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerFragment((TimePickerDialog.OnTimeSetListener)HabitInfoFragment.this).show(getActivity().getSupportFragmentManager(), "HABIT_TIME_PICKER");
            }
        });

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerFragment(HabitInfoFragment.this)
                        .show(getActivity().getSupportFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });

        colorPickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerFragment(HabitInfoFragment.this)
                        .show(getActivity().getSupportFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickedTime.setText("");
                habit.setRemindTime("");
                interactor.updateHabitsDb(habit);
                cancelAlarm();
            }
        });

    }

    private void updateHabit() {
        habit.setText(habitNameEditMode.getText().toString());
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
        habit.setRemindTime(pickedTime.getText().toString());
        interactor.updateHabitsDb(habit);
        startAlarm(calendar, habit.getText());
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        updateHabit();
//        interactor.updateHabitsDb(habit);
//    }

    @Override
    public void onSetColor(int colorID) {
        headerLayout.setBackgroundColor(getResources().getColor(colorID));
        pickedColor.setBackgroundColor(getResources().getColor(colorID));
        habit.setColor(colorID);
    }

    private void startAlarm(Calendar c, String title) {
        if (calendar != null) {
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), NotificationReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("id", habit.getRequestCode());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), habit.getRequestCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), habit.getRequestCode(), intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}

