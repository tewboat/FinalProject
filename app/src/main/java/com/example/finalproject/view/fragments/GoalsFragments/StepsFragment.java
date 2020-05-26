package com.example.finalproject.view.fragments.GoalsFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

    private Goal mGoal;
    private Interactor mInteractor;
    private ImageButton mSaveButton, mDeleteButton;
    private ImageButton mAddStep;
    private EditText mGoalName;
    private RecyclerView mRecyclerView;
    private StepsListAdapter stepsListAdapter;
    private ArrayList<Step> stepArrayList;
    private final String TAG = "StepsFragment";
    private TextView colorPicker;
    private View pickedColor;
    private LinearLayout colorPickerLayout, headerLayout;


    public StepsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.steps_fragment, container, false);
        mAddStep = view.findViewById(R.id.add_step);
        mGoalName = view.findViewById(R.id.goal_text_steps_fragment);
        mRecyclerView = view.findViewById(R.id.steps_list_recycler_view);
        mSaveButton = view.findViewById(R.id.step_fragment_save_button);
        mDeleteButton = view.findViewById(R.id.goal_info_fragment_delete_button);
        colorPicker = view.findViewById(R.id.goal_info_color_picker);
        colorPickerLayout = view.findViewById(R.id.goal_info_color_picker_layout);
        pickedColor = view.findViewById(R.id.goal_info_chosen_color);
        headerLayout = view.findViewById(R.id.goal_info_header_layout);
        //progress = view.findViewById(R.id.step_fragment_progress_text);
        mInteractor = new Interactor(getActivity());
        mGoal = mInteractor.getGoal(getArguments().getString("GOAL_ID"));
        pickedColor.setBackgroundColor(getResources().getColor(mGoal.getColorID()));
        headerLayout.setBackgroundColor(getResources().getColor(mGoal.getColorID()));
        //progress.setText(goal.getProgress() + "%");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(mGoal, getActivity().getApplicationContext());
        setOnClickListeners();
        mGoal.updateProgress(stepArrayList.size());
        mInteractor.updateGoalDb(mGoal);
    }



    private void initView(Goal goal, Context context) {
        stepArrayList = mInteractor.getStepsList(goal);
        mGoalName.setText(goal.getText());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        stepsListAdapter = new StepsListAdapter(stepArrayList, context, this);
        mRecyclerView.setAdapter(stepsListAdapter);

    }

    private void setOnClickListeners() {
        mAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("GOAL_ID", mGoal.getID());
                Navigation.findNavController(getView()).navigate(R.id.stepCreationFragment, bundle);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoal.setText(mGoalName.getText().toString());
                mInteractor.updateGoalDb(mGoal);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(mSaveButton.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(mGoal, AlertDialog.GOAL_FRAGMENT_ID, getView()).show(getActivity().getSupportFragmentManager(), "ALERT_DIALOG");
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

//    @Override
//    public void onPause() {
//        goal.setText(goalName.getText().toString());
//        interactor.updateGoalDb(goal);
//        Log.d(TAG, "onPause: " + goal.toString());
//        super.onPause();
//    }

    @Override
    public void onClick(int position) {
        Step step = stepsListAdapter.getStep(position);
        Bundle bundle = new Bundle();
        bundle.putString("STEP_ID", step.getId());
        bundle.putString("GOAL_ID", mGoal.getID());
        Navigation.findNavController(getView()).navigate(R.id.stepInfoFragment, bundle);
    }

    @Override
    public void onSetColor(int colorID) {
        pickedColor.setBackgroundColor(getResources().getColor(colorID));
        headerLayout.setBackgroundColor(getResources().getColor(colorID));
        mGoal.setColorID(colorID);
    }
}
