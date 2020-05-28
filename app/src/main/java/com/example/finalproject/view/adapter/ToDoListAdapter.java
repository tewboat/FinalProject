package com.example.finalproject.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.finalproject.R;
        import com.example.finalproject.domain.Todo;

        import java.util.ArrayList;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder> {

    private ArrayList<Todo> todoArrayList;
    private OnTodoClick onTodoClick;

    public ToDoListAdapter(ArrayList<Todo> todoArrayList, OnTodoClick onTodoClick) {
        this.todoArrayList = todoArrayList;
        this.onTodoClick = onTodoClick;
    }

    public Todo getTodo(int position){
        return todoArrayList.get(position);
    }

    @NonNull
    @Override
    public ToDoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);
        return new ToDoListViewHolder(view, onTodoClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListViewHolder holder, int position) {
        final Todo todo = todoArrayList.get(position);
        holder.bind(todo);

    }

    @Override
    public int getItemCount() {
        return todoArrayList.size();
    }

    class ToDoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView todoText;
        private View color;
        private OnTodoClick onTodoClick;

        public ToDoListViewHolder(@NonNull View itemView, OnTodoClick onTodoClick) {
            super(itemView);
            todoText = itemView.findViewById(R.id.todo_text);
            color = itemView.findViewById(R.id.todo_color_flag);
            this.onTodoClick = onTodoClick;
            itemView.setOnClickListener(this);
            todoText.setOnClickListener(this);
        }

        void bind(final Todo todo) {
            todoText.setText(todo.getText());
            color.setBackgroundResource(todo.getColor());
            if (todo.getIsComplete() == 1) {
                todoText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

        @Override
        public void onClick(View v) {
                onTodoClick.onClick(getAdapterPosition());
        }
    }

    public interface OnTodoClick {
        void onClick(int position);
    }

}
