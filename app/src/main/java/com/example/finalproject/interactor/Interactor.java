package com.example.finalproject.interactor;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.finalproject.domain.Goal;
import com.example.finalproject.domain.Habit;
import com.example.finalproject.domain.Step;
import com.example.finalproject.domain.Todo;
import com.example.finalproject.repository.DatabaseRepository;

import java.util.ArrayList;

public class Interactor {

    DatabaseRepository repository;

    public Interactor(Context context){
        repository = new DatabaseRepository(context);
    }

    public void insertTodoIntoDb(Todo todo){
        repository.insertTodo(todo);
    }

    public void insertGoalIntoDb(Goal goal){repository.insertGoal(goal);}

    public void insertHabitIntoDb(Habit habit){repository.insertHabit(habit);}

    public void insertStepIntoDb(Goal goal, Step step){repository.insertStep(goal, step);}

    public void removeTodoFromDb(Todo todo){repository.removeTodo(todo);}

    public void removeGoalFromDb(Goal goal){repository.removeGoal(goal);}

    public void removeHabitFromDb(Habit habit){repository.removeHabit(habit);}

    public void removeStepFromDb(Step step){repository.removeStep(step);}

    public ArrayList<Todo> getTodoList(){
        return repository.getTodoList();
    }

    public ArrayList<Goal> getGoalsList(){
        return repository.getGoalsList();
    }

    public ArrayList<Habit> getHabitsList(){return repository.getHabitsList();}

    public ArrayList<Step> getStepsList(Goal goal){return repository.getStepList(goal);}

    public void updateHabitsDb(Habit habit){repository.updateHabitDb(habit);}

    public void updateTodoDb(Todo todo){repository.updateTodoDb(todo);}

    public void updateGoalDb(Goal goal){repository.updateGoalDb(goal);}

    public void updateStepDb(Step step){repository.updateStepDb(step);}

    public Todo getTodo(String id){return repository.getTodo(id);}

    public Habit getHabit(String id){return repository.getHabit(id);}

    public Goal getGoal(String id){return repository.getGoal(id);}

    public Step getStep(String id){return repository.getStep(id);}


}
