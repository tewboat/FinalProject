package com.example.finalproject.repository;

import com.example.finalproject.domain.Goal;
import com.example.finalproject.domain.Habit;
import com.example.finalproject.domain.Step;
import com.example.finalproject.domain.Todo;

public class SQLScripts {

    public static String initTodoDatabaseScript() {
        return "create table todoDatabase(" +
                "id text not null primary key, " +
                "todoText text not null, " +
                "reminderTime text null, " +
                "isComplete integer not null, " +
                "requestCode integer not null, " +
                "priority integer not null);";
    }

    public static String initHabitsDatabaseScript() {
        return "create table habitsDatabase(" +
                "id text not null primary key, " +
                "habitText text not null, " +
                "color integer not null, " +
                "remindTime text null, " +
                "requestCode nut null);";
    }

    public static String initGoalsDatabaseScript() {
        return "create table goalsDatabase(" +
                "id text not null primary key, " +
                "goalText text not null, " +
                "progress integer not null, " +
                "stepsDone integer not null, " +
                "color integer not null);";
    }

    public static String initStepsDatabaseScript() {
        return "create table stepsDatabase(" +
                "id text not null primary key, " +
                "parentID text not null, " +
                "text text not null, " +
                "description text, " +
                "isDone integer not null);";
    }

    public static String removeObjectFromTodoDatabaseScript(Todo todo) {
        String id = "\"" + todo.getId() + "\"";
        return "delete from todoDatabase " +
                "where id = " + id + ";";
    }

    public static String removeObjectFromHabitsDatabaseScript(Habit habit) {
        String id = "\"" + habit.getId() + "\"";
        return "delete from habitsDatabase " +
                "where id = " + id + ";";
    }

    public static String removeObjectFromGoalsDatabaseScript(Goal goal) {
        String id = "\"" + goal.getID() + "\"";
        return "delete from goalsDatabase " +
                "where id = " + id + ";";
    }

    public static String deleteAllGoalStepsScript(Goal goal){
        String id = "\"" + goal.getID() + "\"";
        return "delete from stepsDatabase " +
                "where parentID = " + id + ";";
    }

    public static String removeObjectFromStepsDatabaseScript(Step step) {
        String id = "\"" + step.getId() + "\"";
        return "delete from stepsDatabase " +
                "where id = " + id + ";";
    }


    public static String insertObjIntoHabitsDatabaseScript(Habit habit) {
        String id = "\"" + habit.getId() + "\"";
        String text = "\"" + habit.getText() + "\"";
        String reminderTime = null;
        if (habit.getRemindTime() != null) {
            reminderTime = "\"" + habit.getRemindTime() + "\"";
        }
        return "INSERT INTO habitsDatabase(id, habitText, color, remindTime, requestCode)" +
                "VALUES(" + id + ", " +
                text + ", " +
                habit.getColor() + ", " +
                reminderTime + ", " + "" +
                habit.getRequestCode() + ");";
    }

    public static String insertObjIntoTodoDatabaseScript(Todo todo) {
        String id = "\"" + todo.getId() + "\"";
        String text = "\"" + todo.getText() + "\"";
        String reminderTime = null;
        if (todo.getReminderTime() != null) {
            reminderTime = "\"" + todo.getReminderTime() + "\"";
        }
        return "INSERT INTO todoDatabase(id, todoText, reminderTime, isComplete, requestCode, priority)" +
                "values(" + id + ", " +
                text + ", " +
                reminderTime + ", " +
                todo.getIsComplete() + ", " +
                todo.getRequestCode() + ", " +
                todo.getPriority() + ");";
    }

    public static String insertObjIntoGoalsDatabaseScript(Goal goal) {
        String id = "\"" + goal.getID() + "\"";
        String text = "\"" + goal.getText() + "\"";
        return "insert into goalsDatabase(id, goalText, progress, stepsDone, color)" +
                "values(" + id + ", " +
                text + ", " +
                0 + ", " +
                0 + ", " +
                goal.getColorID() + ");";
    }

    public static String insertObjIntoStepsDatabaseScript(Goal goal, Step step) {
        String id = "\"" + step.getId() + "\"";
        String parentId = "\"" + goal.getID() + "\"";
        String text = "\"" + step.getText() + "\"";
        String description = "\"" + step.getDescription() + "\"";
        return "INSERT INTO stepsDatabase(id, parentID, text, description, isDone)" +
                "VALUES(" + id + ", " +
                parentId + ", " +
                text + ", " +
                description + ", " +
                step.getIsDone() + ");";
    }

    public static String updateHabitsListScript(Habit habit) {
        String habitId = "\"" + habit.getId() + "\"";
        String habitText = "\"" + habit.getText() + "\"";
        String remindTime = "\"" + habit.getRemindTime() + "\"";
        return "UPDATE habitsDatabase " +
                "SET habitText = " + habitText + ", " +
                "color = " + habit.getColor() + ", " +
                "requestCode = " + habit.getRequestCode() + ", " +
                "remindTime = " + remindTime + " " +
                "WHERE id = " + habitId + ";";
    }

    public static String updateTodoListScript(Todo todo) {
        String id = "\"" + todo.getId() + "\"";
        String text = "\"" + todo.getText() + "\"";
        String reminderTime = "\"" + todo.getReminderTime() + "\"";
        int isComplete = todo.getIsComplete();
        return "UPDATE todoDatabase " +
                "SET todoText = " + text + ", " +
                "reminderTime = " + reminderTime + ", " +
                "isComplete = " + isComplete + ", " +
                "requestCode = " + todo.getRequestCode() + ", " +
                "priority = " + todo.getPriority() + " " +
                "WHERE id = " + id + ";";
    }

    public static String updateGoalListScript(Goal goal) {
        String id = "\"" + goal.getID() + "\"";
        String text = "\"" + goal.getText() + "\"";
        return "UPDATE goalsDatabase " +
                "SET goalText  = " + text + ", " +
                "progress = " + goal.getProgress() + ", " +
                "stepsDone = " + goal.getStepsDone() + ", " +
                "color = " + goal.getColorID() + " " +
                "WHERE id = " + id + ";";
    }

    public static String updateStepListScript(Step step) {
        String id = "\"" + step.getId() + "\"";
        String text = "\"" + step.getText() + "\"";
        String description = "\"" + step.getDescription() + "\"";
        return "UPDATE stepsDatabase " +
                "SET text = " + text + ", " +
                "description = " + description + ", " +
                "isDone = " + step.getIsDone() + " " +
                "WHERE id = " + id + ";";
    }

    public static String getGoalsListScript() {
        return "select * from goalsDatabase;";
    }

    public static String getTodoListScript() {
        return "select * from todoDatabase;";
    }

    public static String getHabitsListScript() {
        return "select * from habitsDatabase;";
    }

    public static String getStepsListScript(Goal goal) {
        String goalID = "\"" + goal.getID() + "\"";
        return "SELECT * FROM stepsDatabase " +
                "WHERE parentID = " + goalID + ";";
    }

    public static String getTodo(String id) {
        String todoID = "\"" + id + "\"";
        return "SELECT * FROM todoDatabase WHERE id = " + todoID + ";";
    }

    public static String getHabit(String id) {
        String habitID = "\"" + id + "\"";
        return "SELECT * FROM habitsDatabase WHERE id = " + habitID + ";";
    }

    public static String getGoal(String id) {
        String goalID = "\"" + id + "\"";
        return "SELECT * FROM goalsDatabase WHERE id = " + goalID + ";";
    }

    public static String getStep(String id) {
        String stepID = "\"" + id + "\"";
        return "SELECT * FROM stepsDatabase WHERE id = " + stepID + ";";
    }
}
