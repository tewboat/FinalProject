package com.example.finalproject.view.fragments.GoalsFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.domain.Goal;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.view.adapter.GoalsListAdapter;

import java.util.ArrayList;
import java.util.Random;

public class GoalsFragment extends Fragment implements GoalsListAdapter.OnGoalClick {
    private final String TAG = "GoalsFragment";
    private ImageButton addGoal;
    private RecyclerView recyclerView;
    private GoalsListAdapter goalsListAdapter;
    private Interactor interactor;
    private TextView mTextView;
    private ArrayList<Goal> goalArrayList;



    public GoalsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.goals_fragment, container, false);
        addGoal = view.findViewById(R.id.add_goal);
        mTextView = view.findViewById(R.id.time_to_add_goal);
        recyclerView = view.findViewById(R.id.goals_list_view);
        interactor = new Interactor(getActivity());
        init();
        setOnClickListeners();
        return view;
    }

    private void init() {
        goalArrayList = interactor.getGoalsList();
        Log.d(TAG, "init: goalArrayList получен");
        if(goalArrayList.size() == 0){
            Random random = new Random();
            int mRandomInt = random.nextInt(3);
            switch (mRandomInt){
                case 0:
                    mTextView.setText(R.string.offer_1);
                    break;
                case 1:
                    mTextView.setText(R.string.offer_goal);
                    break;
                case 2:
                    mTextView.setText(R.string.offer_2);
                    break;
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        goalsListAdapter = new GoalsListAdapter(goalArrayList, this);
        recyclerView.setAdapter(goalsListAdapter);
    }

    private void setOnClickListeners() {
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.goalCreationFragment);
            }
        });
    }

    @Override
    public void onGoalClick(int position) {
        Goal goal = goalsListAdapter.getGoal(position);
        Bundle bundle = new Bundle();
        bundle.putString("GOAL_ID", goal.getID());
        Navigation.findNavController(getView()).navigate(R.id.stepsFragment, bundle);
    }
}
