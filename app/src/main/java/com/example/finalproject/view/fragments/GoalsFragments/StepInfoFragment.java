package com.example.finalproject.view.fragments.GoalsFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.R;
import com.example.finalproject.domain.Goal;
import com.example.finalproject.domain.Step;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.view.fragments.DialogFragments.AlertDialog;

public class StepInfoFragment extends Fragment {
    private ImageButton back, deleteStep;
    private EditText stepName, stepDescription;
    private ImageButton isComplete;
    private  Step step;
    private Interactor interactor;
    private  Goal goal;
    private final String TAG = "StepInfoFragment";
    private int isCompleteFlag;
    private final int fragmentID = 4;

    public StepInfoFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_info, container, false);
        back = view.findViewById(R.id.step_info_fragment_back_button);
        deleteStep = view.findViewById(R.id.step_info_fragment_delete_button);
        isComplete = view.findViewById(R.id.step_info_fragment_complete_button);
        stepName = view.findViewById(R.id.step_info_fragment_step_name_text_view);
        stepDescription = view.findViewById(R.id.step_info_fragment_description_text_view);
        interactor = new Interactor(getActivity().getApplicationContext());
        goal = interactor.getGoal(getArguments().getString("GOAL_ID"));
        step = interactor.getStep(getArguments().getString("STEP_ID"));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setValues();
        setOnClickListeners();
    }

    private void setValues(){
        if(step.getIsDone() == 1){
            isComplete.setImageResource(R.drawable.ic_check_box_ticked);
        }
        stepName.setText(step.getText());
        stepDescription.setText(step.getDescription());
    }

    private void setOnClickListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        isComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step.changeIsDone();
                if(step.getIsDone() == 1){
                    isComplete.setImageResource(R.drawable.ic_check_box_ticked);
                    isCompleteFlag = 1;
                    Log.d(TAG, "onClick: " + isCompleteFlag);
                }
                else{
                    isComplete.setImageResource(R.drawable.ic_check_box_outline);
                    isCompleteFlag = -1;
                    Log.d(TAG, "onClick: " + isCompleteFlag);
                }
            }
        });

        deleteStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(step, fragmentID, getView()).show(getActivity().getSupportFragmentManager(), "ALERT_DIALOG");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        goal.updateStepsDone(isCompleteFlag);
        interactor.updateGoalDb(goal);
        interactor.updateStepDb(step);
    }

}
