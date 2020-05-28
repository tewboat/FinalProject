package com.example.finalproject.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.domain.Goal;
import com.example.finalproject.domain.Habit;
import com.example.finalproject.domain.Step;
import com.example.finalproject.domain.Todo;

import java.util.ArrayList;

public class DatabaseRepository {
    private DatabaseHelper databaseHelper;

    public DatabaseRepository(Context context) {
        initDb(context);
    }

    private void initDb(Context context) {
        databaseHelper = new DatabaseHelper(context, "TodoDb", null, 1);
    }

    public void insertTodo(Todo todo) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.insertObjIntoTodoDatabaseScript(todo));
        db.close();
    }

    public ArrayList<Todo> getTodoList() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLScripts.getTodoListScript(), null);
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int textColIndex = cursor.getColumnIndex("todoText");
            int reminderTomeColIndex = cursor.getColumnIndex("reminderTime");
            int isDoneColIndex = cursor.getColumnIndex("isComplete");
            int requestCodeIndex = cursor.getColumnIndex("requestCode");
            int priorityColIndex = cursor.getColumnIndex("priority");
            int colorColIndex = cursor.getColumnIndex("color");
            ArrayList<Todo> todoArrayList = new ArrayList<>();
            do {
                Todo todo = new Todo(cursor.getString(idColIndex),
                        cursor.getString(textColIndex),
                        cursor.getInt(isDoneColIndex),
                        cursor.getString(reminderTomeColIndex),
                        cursor.getInt(requestCodeIndex),
                        cursor.getInt(priorityColIndex),
                        cursor.getInt(colorColIndex));
                todoArrayList.add(todo);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
            return todoArrayList;
        } else {
            cursor.close();
            db.close();
            return new ArrayList<>();
        }
    }

    public void insertGoal(Goal goal) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.insertObjIntoGoalsDatabaseScript(goal));
        db.close();
    }

    public void insertHabit(Habit habit) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.insertObjIntoHabitsDatabaseScript(habit));
        db.close();
    }

    public void insertStep(Goal goal, Step step) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.insertObjIntoStepsDatabaseScript(goal, step));
        db.close();
    }

    public void removeTodo(Todo todo) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.removeObjectFromTodoDatabaseScript(todo));
        db.close();
    }

    public void removeHabit(Habit habit) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.removeObjectFromHabitsDatabaseScript(habit));
        db.close();
    }

    public void removeGoal(Goal goal) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.removeObjectFromGoalsDatabaseScript(goal));
        db.execSQL(SQLScripts.deleteAllGoalStepsScript(goal));
        db.close();
    }

    public void removeStep(Step step) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.removeObjectFromStepsDatabaseScript(step));
        db.close();
    }

    public void updateHabitDb(Habit habit) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.updateHabitsListScript(habit));
        db.close();
    }

    public void updateTodoDb(Todo todo) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.updateTodoListScript(todo));
        db.close();
    }

    public void updateStepDb(Step step) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.updateStepListScript(step));
        db.close();
    }

    public void updateGoalDb(Goal goal) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(SQLScripts.updateGoalListScript(goal));
        db.close();
    }

    public ArrayList<Goal> getGoalsList() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLScripts.getGoalsListScript(), null);
        if (cursor.moveToFirst()) {
            int textColIndex = cursor.getColumnIndex("goalText");
            int idColIndex = cursor.getColumnIndex("id");
            int progressColIndex = cursor.getColumnIndex("progress");
            int stepsDoneColIndex = cursor.getColumnIndex("stepsDone");
            int colorColIndex = cursor.getColumnIndex("color");

            ArrayList<Goal> goalsArrayList = new ArrayList<>();
            do {
                Goal goal = new Goal(cursor.getString(idColIndex),
                        cursor.getString(textColIndex),
                        cursor.getInt(progressColIndex),
                        cursor.getInt(stepsDoneColIndex),
                        cursor.getInt(colorColIndex));
                goalsArrayList.add(goal);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
            return goalsArrayList;
        } else {
            cursor.close();
            db.close();
            return new ArrayList<>();
        }
    }

    public ArrayList<Habit> getHabitsList() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLScripts.getHabitsListScript(), null);
        if (cursor.moveToFirst()) {
            int textColIndex = cursor.getColumnIndex("habitText");
            int idColIndex = cursor.getColumnIndex("id");
            int colorColIndex = cursor.getColumnIndex("color");
            int requestCodeIndex = cursor.getColumnIndex("requestCode");
            int remindTimeColIndex = cursor.getColumnIndex("remindTime");
            ArrayList<Habit> habitsArrayList = new ArrayList<>();
            do {
                Habit habit = new Habit(cursor.getString(idColIndex),
                        cursor.getString(textColIndex),
                        cursor.getInt(colorColIndex),
                        cursor.getString(remindTimeColIndex),
                        cursor.getInt(requestCodeIndex));
                habitsArrayList.add(habit);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
            return habitsArrayList;
        } else {
            cursor.close();
            db.close();
            return new ArrayList<>();
        }
    }

    public ArrayList<Step> getStepList(Goal goal) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLScripts.getStepsListScript(goal), null);
        if (cursor.moveToFirst()) {
            int textColIndex = cursor.getColumnIndex("text");
            int idColIndex = cursor.getColumnIndex("id");
            int desColIndex = cursor.getColumnIndex("description");
            int isDoneColIndex = cursor.getColumnIndex("isDone");
            int colorColIndex = cursor.getColumnIndex("color");

            ArrayList<Step> stepsArrayList = new ArrayList<>();
            do {
                Step step = new Step(cursor.getString(idColIndex),
                        cursor.getString(textColIndex),
                        cursor.getString(desColIndex),
                        cursor.getInt(isDoneColIndex),
                        cursor.getInt(colorColIndex));
                stepsArrayList.add(step);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
            return stepsArrayList;
        } else {
            cursor.close();
            db.close();
            return new ArrayList<>();
        }
    }

    public Todo getTodo(String id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLScripts.getTodo(id), null);
        cursor.moveToFirst();
        int idColIndex = cursor.getColumnIndex("id");
        int textColIndex = cursor.getColumnIndex("todoText");
        int reminderTomeColIndex = cursor.getColumnIndex("reminderTime");
        int isDoneColIndex = cursor.getColumnIndex("isComplete");
        int requestCodeIndex = cursor.getColumnIndex("requestCode");
        int priorityColIndex = cursor.getColumnIndex("priority");
        int colorColIndex = cursor.getColumnIndex("color");
        Todo todo = new Todo(cursor.getString(idColIndex),
                cursor.getString(textColIndex),
                cursor.getInt(isDoneColIndex),
                cursor.getString(reminderTomeColIndex),
                cursor.getInt(requestCodeIndex),
                cursor.getInt(priorityColIndex),
                cursor.getInt(colorColIndex));
        cursor.close();
        db.close();
        return todo;
    }

    public Habit getHabit(String id){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLScripts.getHabit(id), null);
        cursor.moveToFirst();
        int textColIndex = cursor.getColumnIndex("habitText");
        int idColIndex = cursor.getColumnIndex("id");
        int colorColIndex = cursor.getColumnIndex("color");
        int requestCodeIndex = cursor.getColumnIndex("requestCode");
        int remindTimeColIndex = cursor.getColumnIndex("remindTime");
        Habit habit = new Habit(cursor.getString(idColIndex),
                cursor.getString(textColIndex),
                cursor.getInt(colorColIndex),
                cursor.getString(remindTimeColIndex),
                cursor.getInt(requestCodeIndex));
        cursor.close();
        db.close();
        return habit;
    }

    public Goal getGoal(String id){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLScripts.getGoal(id), null);
        cursor.moveToFirst();
        int textColIndex = cursor.getColumnIndex("goalText");
        int idColIndex = cursor.getColumnIndex("id");
        int progressColIndex = cursor.getColumnIndex("progress");
        int stepsDoneColIndex = cursor.getColumnIndex("stepsDone");
        int colorColIndex = cursor.getColumnIndex("color");
        Goal goal = new Goal(cursor.getString(idColIndex),
                cursor.getString(textColIndex),
                cursor.getInt(progressColIndex),
                cursor.getInt(stepsDoneColIndex),
                cursor.getInt(colorColIndex));
        cursor.close();
        db.close();
        return goal;
    }

    public Step getStep(String id){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLScripts.getStep(id), null);
        cursor.moveToFirst();
        int textColIndex = cursor.getColumnIndex("text");
        int idColIndex = cursor.getColumnIndex("id");
        int desColIndex = cursor.getColumnIndex("description");
        int isDoneColIndex = cursor.getColumnIndex("isDone");
        int colorColIndex = cursor.getColumnIndex("color");
        Step step = new Step(cursor.getString(idColIndex),
                cursor.getString(textColIndex),
                cursor.getString(desColIndex),
                cursor.getInt(isDoneColIndex),
                cursor.getInt(colorColIndex));
        cursor.close();
        db.close();
        return step;
    }
}