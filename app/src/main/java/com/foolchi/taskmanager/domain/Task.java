package com.foolchi.taskmanager.domain;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import com.foolchi.taskmanager.provider.TaskProvider;
/**
 * Created by foolchi on 7/8/14.
 * Task class
 */
public class Task{
    private long id;
    private int currentProgress;
    private int target;
    private boolean isFinished;
    private Context context;
    private String taskName;

    public Task(Context context, int target, String taskName){
        this.context = context;
        SharedPreferences spId = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        id = spId.getLong("maxId", 0) + 1;
        spId.edit().putLong("maxId", id).apply();
        setTaskName(taskName);
        setCurrentProgress(0);
        setTarget(target);
        isFinished = false;
    }

    public Task(Context context, int target, int current, String taskName) {
        this.context = context;
        SharedPreferences spId = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        id = spId.getLong("maxId", 0) + 1;
        spId.edit().putLong("maxId", id).apply();
        setTaskName(taskName);
        setCurrentProgress(current);
        setTarget(target);
        isFinished = false;
    }

    public Task(Context context, long id, int currentProgress, int target, String taskName){
        this.context = context;
        this.id = id;
        setTaskName(taskName);
        setCurrentProgress(currentProgress);
        setTarget(target);
        isFinished = false;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id + ": ");
        stringBuilder.append(taskName);
        stringBuilder.append("(").append(currentProgress).append("/").append(target).append(")");
        return stringBuilder.toString();
    }

    public void saveToSharedPreferences(){
        TaskProvider taskProvider = new TaskProvider(context);
        taskProvider.add(this);
    }


    public void remove(){
        TaskProvider taskProvider = new TaskProvider(context);
        taskProvider.remove(this);
    }
    public static void clearAllTask(Context context){
        TaskProvider taskProvider = new TaskProvider(context);
        taskProvider.removeAll();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isFinished(){
        return isFinished;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
