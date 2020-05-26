package com.example.finalproject.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.domain.Goal;

import java.util.ArrayList;

public class GoalsListAdapter extends RecyclerView.Adapter<GoalsListAdapter.GoalsListViewHolder> {

    private ArrayList<Goal> goalArrayList;
    private OnGoalClick onGoalClick;

    public GoalsListAdapter(ArrayList<Goal> goals, OnGoalClick onGoalClickListener) {
        this.goalArrayList = goals;
        this.onGoalClick = onGoalClickListener;
    }

    public Goal getGoal(int position) {
        return goalArrayList.get(position);
    }

    @NonNull
    @Override
    public GoalsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_list_item, parent, false);
        return new GoalsListViewHolder(view, onGoalClick);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsListViewHolder holder, int position) {
        Goal goal = goalArrayList.get(position);
        holder.bind(goal);
    }

    @Override
    public int getItemCount() {
        return goalArrayList.size();
    }

    class GoalsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView goalText;
        //TextView goalProgress;
        OnGoalClick onGoalClick;
        View color;

        public GoalsListViewHolder(@NonNull View itemView, OnGoalClick onGoalClickListener) {
            super(itemView);
            this.goalText = itemView.findViewById(R.id.goal_text);
            this.color = itemView.findViewById(R.id.goal_color_flag);
            //this.goalProgress = itemView.findViewById(R.id.goal_progress);
            this.onGoalClick = onGoalClickListener;

            itemView.setOnClickListener(this);
            goalText.setOnClickListener(this);
        }

        void bind(Goal goal) {
            goalText.setText(goal.getText());
            color.setBackgroundResource(goal.getColorID());
            //goalProgress.setText(goal.getProgress() + "%");
        }

        @Override
        public void onClick(View v) {
            onGoalClick.onGoalClick(getAdapterPosition());
        }
    }

    public interface OnGoalClick {
        void onGoalClick(int position);
    }
}
