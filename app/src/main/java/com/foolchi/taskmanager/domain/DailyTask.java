package com.foolchi.taskmanager.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.foolchi.taskmanager.domain.Sequence;

import java.util.Date;
import java.util.List;

/**
 * Created by foolchi on 7/8/14.
 */
public class DailyTask {
    private long id;
    private List<Sequence> sequenceList;
    private int frequence;
    private Date terminate;

    public DailyTask(Context context, Date terminate){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        id = sp.getLong("maxId", 0) + 1;
        sp.edit().putLong("maxId", id).commit();
        this.terminate = terminate;
        frequence = 1;
    }

    public DailyTask(Context context, Date terminate, int frequence){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        id = sp.getLong("maxId", 0) + 1;
        sp.edit().putLong("maxId", id).commit();
        this.terminate = terminate;
        this.frequence = frequence;
    }
}
