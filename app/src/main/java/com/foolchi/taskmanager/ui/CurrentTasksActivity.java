package com.foolchi.taskmanager.ui;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.foolchi.taskmanager.R;
import com.foolchi.taskmanager.provider.TaskProvider;
import com.foolchi.taskmanager.domain.Task;

import java.util.List;
import java.util.logging.Handler;

/**
 * Created by foolchi on 7/8/14.
 * Show current tasks
 */
public class CurrentTasksActivity extends Fragment{
    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private LayoutInflater inflater;
    private ListView lv_task;
    //public Handler handler;
    final int CHANGED = 0;
    private Dialog dialog;
    private int currentProgress;
    private EditText et_currentProgress;
    private TextView tv_target;
    private Button bt_ok;
    private Button bt_cancel;
    private Task task;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentTaskView = inflater.inflate(R.layout.fragment_main, container, false);
        this.inflater = inflater;
        lv_task = (ListView)currentTaskView.findViewById(R.id.lv_task);
        TaskProvider taskProvider = new TaskProvider(inflater.getContext());
        taskList = taskProvider.getAllTask();
        taskAdapter = new TaskAdapter();
        lv_task.setAdapter(taskAdapter);

        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task)lv_task.getItemAtPosition(position);
                createDialog(task);
                /*
                Bundle data = new Bundle();
                data.putLong("id", task.getId());
                data.putString("taskName", task.getTaskName());
                data.putInt("currentProgress", task.getCurrentProgress());
                data.putInt("target", task.getTarget());
                Intent intent = new Intent(inflater.getContext(), AddProgressActivity.class);
                intent.putExtras(data);
                startActivityForResult(intent, CHANGED);
                */
            }
        });
        return currentTaskView;
    }

    public void createDialog(Task task_in){
        task = task_in;
        dialog = new Dialog(inflater.getContext());
        currentProgress = task.getCurrentProgress();
        View view = View.inflate(inflater.getContext(), R.layout.add_progress_layout, null);
        et_currentProgress = (EditText)view.findViewById(R.id.et_currentProgress);
        tv_target = (TextView)view.findViewById(R.id.tv_target);
        bt_ok = (Button)view.findViewById(R.id.bt_ok);
        bt_cancel = (Button)view.findViewById(R.id.bt_cancel);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentProgress = Integer.parseInt(et_currentProgress.getText().toString());
                if (currentProgress < 0) {
                    currentProgress = 0;
                    et_currentProgress.setText(currentProgress+"");
                }
                else if (currentProgress > task.getTarget()){
                    currentProgress = task.getTarget();
                    et_currentProgress.setText(currentProgress+"");
                }

                if (currentProgress != task.getCurrentProgress()) {
                    task.setCurrentProgress(currentProgress);
                    task.saveToSharedPreferences();
                }
                dialog.dismiss();
                update();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        dialog.setTitle(task.getTaskName());

        tv_target.setText(task.getTarget()+"");
        et_currentProgress.setText(task.getCurrentProgress()+"");

        dialog.setContentView(view);
        dialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHANGED:
                if (resultCode == 1){
                    update();
                }
                break;

            default:
                break;
        }
    }

    private void update(){
        TaskProvider taskProvider = new TaskProvider(inflater.getContext());
        taskList = taskProvider.getAllTask();
        lv_task.setAdapter(taskAdapter);
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
                taskView.textView.setText(task.toString());

                view.setTag(taskView);
                return view;
            }
            else {
                TaskView taskView = (TaskView)convertView.getTag();
                taskView.progressBar.setProgress(task.getCurrentProgress());
                taskView.progressBar.setMax(task.getTarget());
                taskView.textView.setText(task.toString());
                return convertView;
            }
        }

        private class TaskView{
            public ProgressBar progressBar;
            public TextView textView;
        }
    }
}
