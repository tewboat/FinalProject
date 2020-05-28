package com.example.finalproject.view.fragments.GoalsFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.finalproject.R;
import com.example.finalproject.domain.Goal;
import com.example.finalproject.domain.Step;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.interfaces.OnSetColor;
import com.example.finalproject.view.fragments.DialogFragments.ColorPickerFragment;

public class StepCreationFragment extends Fragment implements OnSetColor {

    private Interactor interactor;
    private ImageButton back, create;
    private EditText stepName, stepDescription;
    private Goal goal;
    private String goalId;
    private LinearLayout mHeaderLayout, mColorPickerLayout;
    private TextView mColorPicker;
    private View mPickedColor;
    private int mColorId = R.color.colorMaterialGreen;

    public StepCreationFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_creation_fragment, container, false);
        initView(view);
        interactor = new Interactor(getActivity());
        goalId = getArguments().getString("GOAL_ID");
        goal = interactor.getGoal(goalId);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnClickListeners();
    }

    private void initView(View view){
        back = view.findViewById(R.id.step_creation_fragment_back_button);
        create = view.findViewById(R.id.step_creation_fragment_create_button);
        stepName = view.findViewById(R.id.step_creation_fragment_step_name_edit_text);
        stepDescription = view.findViewById(R.id.step_creation_fragment_step_description_edit_text);
        mColorPickerLayout = view.findViewById(R.id.step_creation_color_picker_layout);
        mColorPicker = view.findViewById(R.id.step_creation_color_picker);
        mPickedColor = view.findViewById(R.id.step_creation_chosen_color);
        mHeaderLayout = view.findViewById(R.id.step_creation_header_layout);
    }

    private void setOnClickListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stepName.getText() != null && !stepName.getText().toString().equals("")){
                    Step step = new Step(null, stepName.getText().toString(), stepDescription.getText().toString(), 0, mColorId);
                    interactor.insertStepIntoDb(goal, step);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.hideSoftInputFromWindow(create.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    Navigation.findNavController(getView()).popBackStack();
                }
            }
        });

        mColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerFragment(StepCreationFragment.this).show(getChildFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });

        mColorPickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerFragment(StepCreationFragment.this).show(getChildFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });

    }

    @Override
    public void onSetColor(int colorID) {
        mColorId = colorID;
        mHeaderLayout.setBackgroundColor(getResources().getColor(colorID));
        mPickedColor.setBackgroundColor(getResources().getColor(colorID));
    }
}
