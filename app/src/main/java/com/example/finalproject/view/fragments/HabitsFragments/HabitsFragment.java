package com.example.finalproject.view.fragments.HabitsFragments;

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
import com.example.finalproject.domain.Habit;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.view.adapter.HabitsListAdapter;

import java.util.ArrayList;
import java.util.Random;

public class HabitsFragment extends Fragment implements HabitsListAdapter.OnHabitClick {

    private ImageButton addHabit;
    private RecyclerView recyclerView;
    private Interactor interactor;
    private HabitsListAdapter habitsListAdapter;
    private String TAG = "HabitsFragment";
    private TextView mTextView;
    private ArrayList<Habit> habitArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("FRAGMENT", "onCreateView: creating fragment");
        View view = inflater.inflate(R.layout.habits_fragment, container, false);
        addHabit = view.findViewById(R.id.add_habit);
        recyclerView = view.findViewById(R.id.habits_list_recycler_view);
        mTextView = view.findViewById(R.id.time_to_add_habit);
        interactor = new Interactor(getActivity().getApplicationContext());
        Log.d(TAG, "onCreateView: view created");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        setOnClickListeners();
    }

    private void init() {
        habitArrayList = interactor.getHabitsList();
        if(habitArrayList.size() == 0){
            Random random = new Random();
            int mRandomInt = random.nextInt(3);
            switch (mRandomInt){
                case 0:
                    mTextView.setText(R.string.offer_1);
                    break;
                case 1:
                    mTextView.setText(R.string.offer_habit);
                    break;
                case 2:
                    mTextView.setText(R.string.offer_2);
                    break;
            }
        }
        habitsListAdapter = new HabitsListAdapter(habitArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(habitsListAdapter);
    }

    private void setOnClickListeners() {
        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.habitCreationFragment);
            }
        });
    }

    @Override
    public void onClick(int position) {
        Habit habit = habitsListAdapter.getHabitArrayList().get(position);
        Bundle bundle = new Bundle();
        bundle.putString("HABIT_ID", habit.getId());
        Navigation.findNavController(getView()).navigate(R.id.habitInfoFragment, bundle);
    }
}
