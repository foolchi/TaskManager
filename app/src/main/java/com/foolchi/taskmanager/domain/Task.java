package com.foolchi.taskmanager.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.foolchi.taskmanager.domain.Sequence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by foolchi on 7/8/14.
 */
public class Task {
    private long id;
    private List<Sequence> sequenceList;
    private int currentProgress;
    private int target;
    private boolean isFinished;
    private Context context;
    private String taskName;



    public Task(Context context, int target, String taskName){
        this.context = context;
        SharedPreferences spId = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        id = spId.getLong("maxId", 0) + 1;
        spId.edit().putLong("maxId", id).commit();
        setTaskName(taskName);
        setCurrentProgress(0);
        setTarget(target);
        initialSequenceList();
        isFinished = false;
    }

    public Task(Context context, int target, int current, String taskName) {
        this.context = context;
        SharedPreferences spId = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        id = spId.getLong("maxId", 0) + 1;
        spId.edit().putLong("maxId", id).commit();
        setTaskName(taskName);
        setCurrentProgress(current);
        setTarget(target);
        initialSequenceList();
        isFinished = false;
    }

    public Task(Context context, long id, int currentProgress, int target, String taskName){
        this.context = context;
        this.id = id;
        setTaskName(taskName);
        setCurrentProgress(currentProgress);
        setTarget(target);
        initialSequenceList();
        isFinished = false;
    }

    public void addSequence(Date date, int progress){
        sequenceList.add(new Sequence(date, progress));
        if (progress >= target){
            isFinished = true;
        }
    }

    public void saveToSharedPreferences(){
        SharedPreferences sp = context.getSharedPreferences(""+id, Context.MODE_PRIVATE);
        sp.edit().putInt("currentProgress", currentProgress).putInt("target", target).putString("taskName", taskName).commit();
    }

    private void initialSequenceList(){
        sequenceList = new ArrayList<Sequence>();
        addSequence(new Date(), currentProgress);
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

    private long getId(){
        return id;
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
