package com.example.finalproject.view.fragments.GoalsFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.interfaces.OnSetColor;
import com.example.finalproject.view.fragments.DialogFragments.ColorPickerFragment;

public class GoalCreationFragment extends Fragment implements OnSetColor {

    private ImageButton create, back;
    private EditText goalName;
    private Interactor interactor;
    private View colorView;
    private TextView colorPicker;
    private LinearLayout layout, header;
    private int colorID = R.color.colorMaterialGreen;

    public GoalCreationFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_creation_fragment, container, false);
        create = view.findViewById(R.id.goal_creation_fragment_create_button);
        back = view.findViewById(R.id.goal_creation_fragment_back_button);
        goalName = view.findViewById(R.id.goal_creation_fragment_goal_name_edit_text);
        colorView = view.findViewById(R.id.goal_creation_chosen_color);
        colorPicker = view.findViewById(R.id.goal_creation_color_picker);
        layout = view.findViewById(R.id.color_picker_layout);
        header = view.findViewById(R.id.goal_creation_header);
        interactor = new Interactor(getActivity().getApplicationContext());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnClickListeners();
    }

    private void setOnClickListeners(){
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goalName.getText() != null && !goalName.getText().toString().equals("")){
                    Goal goal = new Goal(null, goalName.getText().toString(), 0, 0, colorID);
                    interactor.insertGoalIntoDb(goal);
                    Navigation.findNavController(getView()).popBackStack();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GoalCreationFragment", "onClick: colorPicker");
                new ColorPickerFragment(GoalCreationFragment.this).show(getActivity().getSupportFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GoalCreationFragment", "onClick: colorPicker");
                new ColorPickerFragment(GoalCreationFragment.this).show(getActivity().getSupportFragmentManager(), "COLOR_PICKER_FRAGMENT");
            }
        });

    }


    @Override
    public void onSetColor(int colorID) {
        colorView.setBackgroundColor(getResources().getColor(colorID));
        header.setBackgroundColor(getResources().getColor(colorID));
        this.colorID = colorID;
    }
}
