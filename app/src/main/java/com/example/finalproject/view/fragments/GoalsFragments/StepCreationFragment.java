package com.example.finalproject.view.fragments.GoalsFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.finalproject.R;
import com.example.finalproject.domain.Goal;
import com.example.finalproject.domain.Step;
import com.example.finalproject.interactor.Interactor;

public class StepCreationFragment extends Fragment {

    private Interactor interactor;
    private ImageButton back, create;
    private EditText stepName, stepDescription;
    private Goal goal;
    private String goalId;

    public StepCreationFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_creation_fragment, container, false);
        back = view.findViewById(R.id.step_creation_fragment_back_button);
        create = view.findViewById(R.id.step_creation_fragment_create_button);
        stepName = view.findViewById(R.id.step_creation_fragment_step_name_edit_text);
        stepDescription = view.findViewById(R.id.step_creation_fragment_step_description_edit_text);
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
                    Step step = new Step(null, stepName.getText().toString(), stepDescription.getText().toString(), 0);
                    interactor.insertStepIntoDb(goal, step);
                    Navigation.findNavController(getView()).popBackStack();
                }
            }
        });
    }
}
