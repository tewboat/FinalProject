package com.example.finalproject.view.fragments.GoalsFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.finalproject.R;
import com.example.finalproject.domain.Goal;
import com.example.finalproject.domain.Step;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.interfaces.OnSetColor;
import com.example.finalproject.view.fragments.DialogFragments.AlertDialog;
import com.example.finalproject.view.fragments.DialogFragments.ColorPickerFragment;

public class StepInfoFragment extends Fragment implements OnSetColor {
    private ImageButton mSaveButton, deleteStep;
    private EditText stepName, stepDescription;
    private CheckBox isComplete;
    private Step step;
    private Interactor interactor;
    private Goal goal;
    private final String TAG = "StepInfoFragment";
    private int isCompleteFlag;
    private LinearLayout mHeaderLayout, mColorPickerLayout;
    private TextView mColorPicker;
    private View mPickedColor;

    public StepInfoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_info, container, false);
        initView(view);
        interactor = new Interactor(getActivity());
        if (getArguments() != null) {
            goal = interactor.getGoal(getArguments().getString("GOAL_ID"));
            step = interactor.getStep(getArguments().getString("STEP_ID"));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setValues();
        setOnClickListeners();
    }

    private void initView(View view) {
        mSaveButton = view.findViewById(R.id.step_info_fragment_save_button);
        deleteStep = view.findViewById(R.id.step_info_fragment_delete_button);
        isComplete = view.findViewById(R.id.step_info_checkbox);
        stepName = view.findViewById(R.id.step_info_fragment_step_name_text_view);
        stepDescription = view.findViewById(R.id.step_info_fragment_description_text_view);
        mColorPicker = view.findViewById(R.id.step_info_color_picker);
        mColorPickerLayout = view.findViewById(R.id.step_info_color_picker_layout);
        mHeaderLayout = view.findViewById(R.id.step_info_header_layout);
        mPickedColor = view.findViewById(R.id.step_info_chosen_color);
    }

    private void setValues() {
        if (step.getIsDone() == 1) {
            isComplete.setChecked(true);
        }
        stepName.setText(step.getText());
        stepDescription.setText(step.getDescription());
        mHeaderLayout.setBackgroundColor(getResources().getColor(step.getColorId()));
        mPickedColor.setBackgroundColor(getResources().getColor(step.getColorId()));
    }

    private void setOnClickListeners() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step.setText(stepName.getText().toString());
                step.setDescription(stepDescription.getText().toString());
                goal.updateStepsDone(isCompleteFlag);
                interactor.updateGoalDb(goal);
                interactor.updateStepDb(step);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(mSaveButton.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        isComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    step.setIsDone(1);
                    isCompleteFlag = 1;
                } else {
                    step.setIsDone(0);
                    isCompleteFlag = -1;
                }

                interactor.updateStepDb(step);
                goal.updateStepsDone(isCompleteFlag);
                interactor.updateGoalDb(goal);

            }
        });

        deleteStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fg = getChildFragmentManager();
                new AlertDialog(step, goal, AlertDialog.STEP_FRAGMENT_ID, getView()).show(fg, "ALERT_DIALOG");
            }
        });

        mColorPickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerFragment(StepInfoFragment.this).show(getChildFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });

        mColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerFragment(StepInfoFragment.this).show(getChildFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });
    }

    @Override
    public void onSetColor(int colorID) {
        step.setColorId(colorID);
        mPickedColor.setBackgroundColor(getResources().getColor(colorID));
        mHeaderLayout.setBackgroundColor(getResources().getColor(colorID));
    }
}
