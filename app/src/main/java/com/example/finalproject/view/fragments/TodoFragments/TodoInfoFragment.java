package com.example.finalproject.view.fragments.TodoFragments;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
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
    private ImageButton mSaveButton, deleteTask;
    private EditText todoEditText;
    private Interactor interactor;
    private CheckBox isComplete;
    private TextView pickedTime, timePicker;
    private Calendar calendar;
    private Button deleteAlarm;
    private Switch mPriority;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.todo_edit_mode, container, false);
        initView(view);
        setValues();
        return view;
    }

    private void setValues() {

        String todoID = getArguments().getString("TODO_ID");
        interactor = new Interactor(getActivity());
        this.todo = interactor.getTodo(todoID);
        todoEditText.setText(todo.getText());
        if (todo.getIsComplete() == 1) {
            isComplete.setChecked(true);
        }
        if (todo.getReminderTime() != null) {
            pickedTime.setText(todo.getReminderTime());
        }
        if (todo.getPriority() == 1) {
            mPriority.setChecked(true);
        }

        setOnClickListeners();
    }

    private void initView(View view){
        mSaveButton = view.findViewById(R.id.todo_edit_fragment_save_button);
        todoEditText = view.findViewById(R.id.todo_edit_fragment_edit_text_view);
        deleteTask = view.findViewById(R.id.todo_info_fragment_delete_button);
        isComplete = view.findViewById(R.id.todo_info_checkbox);
        pickedTime = view.findViewById(R.id.todo_info_picked_time);
        timePicker = view.findViewById(R.id.todo_info_time_picker);
        deleteAlarm = view.findViewById(R.id.todo_info_cancel_alarm);
        mPriority = view.findViewById(R.id.todo_info_high_priority);
    }

    private void setOnClickListeners() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo.setText(todoEditText.getText().toString());
                interactor.updateTodoDb(todo);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(mSaveButton.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(todo, AlertDialog.TODO_FRAGMENT_ID, getView()).show(getActivity().getSupportFragmentManager(), "ALERT_DIALOG");
            }
        });

        isComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    todo.setIsComplete(1);
                }
                else{
                    todo.setIsComplete(0);
                }
                interactor.updateTodoDb(todo);
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerFragment((TimePickerDialog.OnTimeSetListener) TodoInfoFragment.this).show(getActivity().getSupportFragmentManager(), "TIME_PICKER_FRAGMENT");
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

        mPriority.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    todo.setPriority(1);
                }
                else{
                    todo.setPriority(0);
                }
                interactor.updateTodoDb(todo);
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
