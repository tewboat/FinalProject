package com.example.finalproject.view.fragments.GoalsFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.domain.Goal;
import com.example.finalproject.domain.Step;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.interfaces.OnSetColor;
import com.example.finalproject.view.adapter.StepsListAdapter;
import com.example.finalproject.view.fragments.DialogFragments.AlertDialog;
import com.example.finalproject.view.fragments.DialogFragments.ColorPickerFragment;

import java.util.ArrayList;


public class StepsFragment extends Fragment implements StepsListAdapter.StepsOnClick, OnSetColor {

    private Goal goal;
    private Interactor interactor;
    private ImageButton backButton, deleteButton;
    private ImageButton addStep;
    private EditText goalName;
    private RecyclerView recyclerView;
    private StepsListAdapter stepsListAdapter;
    private ArrayList<Step> stepArrayList;
    private final String TAG = "StepsFragment";
    private final int fragmentID = 2;
    private TextView colorPicker;
    private View pickedColor;
    private LinearLayout colorPickerLayout, headerLayout;


    public StepsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.steps_fragment, container, false);
        addStep = view.findViewById(R.id.add_step);
        goalName = view.findViewById(R.id.goal_text_steps_fragment);
        recyclerView = view.findViewById(R.id.steps_list_recycler_view);
        backButton = view.findViewById(R.id.step_fragment_back_button);
        deleteButton = view.findViewById(R.id.goal_info_fragment_delete_button);
        colorPicker = view.findViewById(R.id.goal_info_color_picker);
        colorPickerLayout = view.findViewById(R.id.goal_info_color_picker_layout);
        pickedColor = view.findViewById(R.id.goal_info_chosen_color);
        headerLayout = view.findViewById(R.id.goal_info_header_layout);
        interactor = new Interactor(getActivity());
        goal = interactor.getGoal(getArguments().getString("GOAL_ID"));
        pickedColor.setBackgroundColor(getResources().getColor(goal.getColorID()));
        headerLayout.setBackgroundColor(getResources().getColor(goal.getColorID()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(goal, getActivity().getApplicationContext());
        setOnClickListeners();
        goal.updateProgress(stepArrayList.size());
        interactor.updateGoalDb(goal);
    }

    private void initView(Goal goal, Context context) {
        stepArrayList = interactor.getStepsList(goal);
        goalName.setText(goal.getText());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        stepsListAdapter = new StepsListAdapter(stepArrayList, context, this);
        recyclerView.setAdapter(stepsListAdapter);

    }

    private void setOnClickListeners() {
        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("GOAL_ID", goal.getID());
                Navigation.findNavController(getView()).navigate(R.id.stepCreationFragment, bundle);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(goal, fragmentID, getView()).show(getActivity().getSupportFragmentManager(), "ALERT_DIALOG");
            }
        });

        colorPickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerFragment(StepsFragment.this).show(getActivity().getSupportFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerFragment(StepsFragment.this).show(getActivity().getSupportFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });


    }

    @Override
    public void onPause() {
        goal.setText(goalName.getText().toString());
        interactor.updateGoalDb(goal);
        Log.d(TAG, "onPause: " + goal.toString());
        super.onPause();
    }

    @Override
    public void onClick(int position) {
        Step step = stepsListAdapter.getStep(position);
        Bundle bundle = new Bundle();
        bundle.putString("STEP_ID", step.getId());
        bundle.putString("GOAL_ID", goal.getID());
        Navigation.findNavController(getView()).navigate(R.id.stepInfoFragment, bundle);
    }

    @Override
    public void onSetColor(int colorID) {
        pickedColor.setBackgroundColor(getResources().getColor(colorID));
        headerLayout.setBackgroundColor(getResources().getColor(colorID));
        goal.setColorID(colorID);
    }
}
