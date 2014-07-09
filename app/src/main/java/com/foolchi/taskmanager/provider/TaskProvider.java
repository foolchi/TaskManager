package com.foolchi.taskmanager.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.foolchi.taskmanager.domain.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.foolchi.taskmanager.provider.DBHelper;

/**
 * Created by foolchi on 7/8/14.
 * Provider class to get all the tasks
 */
public class TaskProvider {

    private DBHelper dbHelper;
    private Context context;
    public TaskProvider(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public boolean find(long id){
        boolean result = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (db.isOpen()){
            Cursor cursor = db.rawQuery("select * from task where id = ?", new String[]{id+""});
            if (cursor.moveToNext()){
                result = true;
            }
            cursor.close();
            db.close();
        }
        return result;
    }

    public void add(Task task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Date d =  new Date();
        if (db.isOpen()){
            db.execSQL("insert into task (id, taskName, current, target, date) values(?, ?, ?, ?, ?)", new String[]{task.getId()+"", task.getTaskName(), task.getCurrentProgress()+"", task.getTarget()+"", d.getTime()+""});
            db.close();
        }
    }

    public void remove(Task task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (db.isOpen()){
            db.execSQL("delete from task where id = ?", new String[]{task.getId()+""});
            db.close();
        }
    }

    public void removeAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (db.isOpen()){
            db.execSQL("delete from task");
            db.close();
        }
    }
    public List<Task> getAllTask(){
        List<Task> tasks = new ArrayList<Task>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        long maxId = sp.getLong("maxId", 0);
        if (db.isOpen()){
            for (long i = 1; i <= maxId; i++){
                Task task = null;
                Cursor cursor = db.rawQuery("select * from task where id = ?", new String[]{i+""});
                long latestDate = 0, currentDate = 0;
                while (cursor.moveToNext()){
                    currentDate = Long.parseLong(cursor.getString(4));
                    if (currentDate > latestDate){
                        latestDate = currentDate;
                        task = new Task(context, Long.parseLong(cursor.getString(0)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), cursor.getString(1));
                    }
                }
                if (task != null){
                    tasks.add(task);
                }
                cursor.close();
            }
            db.close();
        }
        return tasks;
    }

    /*
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
    */
}
