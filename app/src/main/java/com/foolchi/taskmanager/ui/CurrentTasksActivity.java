package com.foolchi.taskmanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.foolchi.taskmanager.R;
import com.foolchi.taskmanager.Provider.TaskProvider;
import com.foolchi.taskmanager.domain.Task;

import java.util.List;

/**
 * Created by foolchi on 7/8/14.
 */
public class CurrentTasksActivity extends Fragment{
    private Button bt_test;
    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private LayoutInflater inflater;
    private ListView lv_task;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentTaskView = inflater.inflate(R.layout.fragment_main, container, false);
        this.inflater = inflater;
        lv_task = (ListView)currentTaskView.findViewById(R.id.lv_task);
        TaskProvider taskProvider = new TaskProvider(inflater.getContext());
        taskList = taskProvider.getAllTask();
        taskAdapter = new TaskAdapter();
        lv_task.setAdapter(taskAdapter);
        return currentTaskView;
    }

    private class TaskAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return taskList.size();
        }

        @Override
        public Object getItem(int i) {
            return taskList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            Task task = taskList.get(position);
            if (convertView == null){
                View view = View.inflate(inflater.getContext(), R.layout.task_item_layout, null);
                TaskView taskView = new TaskView();

                taskView.progressBar = (ProgressBar)view.findViewById(R.id.pb_task);
                taskView.progressBar.setMax(task.getTarget());
                taskView.progressBar.setProgress(task.getCurrentProgress());
                taskView.textView = (TextView)view.findViewById(R.id.tv_task);
                taskView.textView.setText(task.getTaskName());

                view.setTag(taskView);
                return view;
            }
            else {
                TaskView taskView = (TaskView)convertView.getTag();
                taskView.progressBar.setProgress(task.getCurrentProgress());
                taskView.progressBar.setMax(task.getTarget());
                taskView.textView.setText(task.getTaskName());
                return convertView;
            }
        }

        private class TaskView{
            public ProgressBar progressBar;
            public TextView textView;
        }
    }
}
