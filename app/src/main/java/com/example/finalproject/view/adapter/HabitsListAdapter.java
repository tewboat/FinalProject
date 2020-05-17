package com.example.finalproject.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.domain.Habit;

import java.util.ArrayList;

public class HabitsListAdapter extends RecyclerView.Adapter<HabitsListAdapter.HabitsListViewHolder> {

    ArrayList<Habit> habitArrayList;
    Context context;
    OnHabitClick onHabitClick;

    public HabitsListAdapter(ArrayList<Habit> habitsArrayList, Context context, OnHabitClick onHabitClick){
        this.habitArrayList = habitsArrayList;
        this.context = context;
        this.onHabitClick = onHabitClick;
    }

    public ArrayList<Habit> getHabitArrayList(){
        return habitArrayList;
    }

    @NonNull
    @Override
    public HabitsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_list_item, parent, false);
        return new HabitsListViewHolder(view, onHabitClick);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitsListViewHolder holder, int position) {
        Habit habit = habitArrayList.get(position);
        holder.bind(habit);
    }

    @Override
    public int getItemCount() {
        return habitArrayList.size();
    }


    class HabitsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView habitText;
        View color;
        OnHabitClick onHabitClick;

        public HabitsListViewHolder(@NonNull View itemView, OnHabitClick onHabitClick) {
            super(itemView);
            habitText = itemView.findViewById(R.id.habit_text);
            color = itemView.findViewById(R.id.habit_color_flag);
            this.onHabitClick = onHabitClick;
            itemView.setOnClickListener(this);
            habitText.setOnClickListener(this);
        }

        public void bind(Habit habit){
            habitText.setText(habit.getText());
            color.setBackgroundResource(habit.getColor());
        }

        @Override
        public void onClick(View v) {
            onHabitClick.onClick(getAdapterPosition());
        }
    }

    public interface OnHabitClick{
        void onClick(int position);
    }
}
