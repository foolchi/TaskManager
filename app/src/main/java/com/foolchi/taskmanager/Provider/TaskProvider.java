package com.foolchi.taskmanager.Provider;

import android.content.Context;
import android.content.SharedPreferences;

import com.foolchi.taskmanager.domain.Task;

import java.util.ArrayList;
import java.util.List;

import com.foolchi.taskmanager.domain.Task;

/**
 * Created by foolchi on 7/8/14.
 */
public class TaskProvider {

    private SharedPreferences sp;
    private Context context;
    public TaskProvider(Context context){
        this.context = context;
    }

    public List<Task> getAllTask(){
        List<Task> tasks = new ArrayList<Task>();
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        long maxId = sp.getLong("maxId", 0);
        if (maxId == 0)
            return tasks;

        for (long i = 1; i <= maxId; i++){
            sp = context.getSharedPreferences(""+i, Context.MODE_PRIVATE);
            if (sp.contains("taskName")) {
                tasks.add(new Task(context, i, sp.getInt("currentProgress", 0),
                        sp.getInt("target", 0), sp.getString("taskName", " ")));
            }
        }

        tasks.add(new Task(context, 100, "default"));

        System.out.println("Total tasks: " + tasks.size());
        return tasks;
    }
}
