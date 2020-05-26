package com.example.finalproject.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.domain.Step;

import java.util.ArrayList;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsListViewHolder> {

    ArrayList<Step> stepsArrayList;
    Context context;
    StepsOnClick stepsOnClick;

    public StepsListAdapter(ArrayList<Step> stepArrayList, Context context, StepsOnClick stepsOnClick) {
        this.stepsArrayList = stepArrayList;
        this.context = context;
        this.stepsOnClick = stepsOnClick;
    }

    public Step getStep(int position){
        return stepsArrayList.get(position);
    }

    public ArrayList<Step> getStepsArrayList() {
        return stepsArrayList;
    }

    @NonNull
    @Override
    public StepsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.step_list_item, parent, false);
        return new StepsListViewHolder(view, stepsOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsListViewHolder holder, int position) {
        Step step = stepsArrayList.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        return stepsArrayList.size();
    }

    class StepsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView stepText;
        View color;
        StepsOnClick stepsOnClick;

        public StepsListViewHolder(@NonNull View itemView, StepsOnClick stepsOnClick) {
            super(itemView);
            stepText = itemView.findViewById(R.id.step_text_list_item);
            color = itemView.findViewById(R.id.step_color_flag);
            this.stepsOnClick = stepsOnClick;
            itemView.setOnClickListener(this);
            stepText.setOnClickListener(this);
        }

        void bind(Step step){
            stepText.setText(step.getText());
            if(step.getIsDone() == 1){
                color.setBackgroundResource(R.color.colorMaterialGreen);
            }
        }

        @Override
        public void onClick(View v) {
            stepsOnClick.onClick(getAdapterPosition());
        }
    }

    public interface StepsOnClick{
        void onClick(int position);
    }

}

