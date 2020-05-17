package com.example.finalproject.view.fragments.TodoFragments;

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
import com.example.finalproject.domain.Todo;
import com.example.finalproject.interactor.Interactor;
import com.example.finalproject.view.adapter.ToDoListAdapter;

import java.util.ArrayList;
import java.util.Random;

public class TodoFragment extends Fragment implements ToDoListAdapter.OnTodoClick {

    private final String TAG = "TodoFragment";
    private ImageButton addTodo;
    private RecyclerView recyclerView;
    private ArrayList<Todo> todoArrayList;
    private Interactor interactor;
    private ToDoListAdapter toDoListAdapter;
    private TextView itIsTimeToAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_fragment, container, false);
        recyclerView = view.findViewById(R.id.todo_list);
        addTodo = view.findViewById(R.id.add_task);
        itIsTimeToAdd = view.findViewById(R.id.time_to_add_task);

        Log.d(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnClickListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        interactor = new Interactor(getActivity());
        init();
    }

    private void init(){
        Log.d(TAG, "init: getting todoArrayList");
        todoArrayList = interactor.getTodoList();
        if(todoArrayList.size() == 0){
            Random random = new Random();
            int mRandomInt = random.nextInt(3);
            switch (mRandomInt){
                case 0:
                    itIsTimeToAdd.setText(R.string.offer_1);
                    break;
                case 1:
                    itIsTimeToAdd.setText(R.string.offer_task);
                    break;
                case 2:
                    itIsTimeToAdd.setText(R.string.offer_2);
                    break;
            }
        }
        Log.d(TAG, "init: todoArrayList successfully got");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        toDoListAdapter = new ToDoListAdapter(getContext(), todoArrayList, (ToDoListAdapter.OnTodoClick) this);
        recyclerView.setAdapter(toDoListAdapter);
    }

    private void setOnClickListener(){
        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.todoCreationFragment);
            }
        });
    }

    @Override
    public void onClick(int position) {
        Todo todo = toDoListAdapter.getTodo(position);
        Bundle bundle = new Bundle();
        bundle.putString("TODO_ID", todo.getId());
        Navigation.findNavController(getView()).navigate(R.id.todoInfoFragment, bundle);
    }


}
