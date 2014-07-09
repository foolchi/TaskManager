package com.foolchi.taskmanager.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foolchi.taskmanager.R;
import com.foolchi.taskmanager.domain.Task;

/**
 * Created by foolchi on 7/9/14.
 */
public class AddTaskActivity extends Activity implements OnClickListener{

    private Button bt_addTask;
    private EditText et_currentProgress, et_target, et_taskName;
    private String taskName;
    private int target, currentProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_task_layout);
        bt_addTask = (Button)findViewById(R.id.bt_addTask);
        et_taskName = (EditText)findViewById(R.id.et_taskName);
        et_target = (EditText)findViewById(R.id.et_target);
        et_currentProgress = (EditText)findViewById(R.id.et_currentProgress);

        bt_addTask.setOnClickListener(this);
        et_taskName.setOnClickListener(this);
        et_target.setOnClickListener(this);
        et_currentProgress.setOnClickListener(this);

        taskName = null;
        target = currentProgress = 0;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.bt_addTask:
                taskName = et_taskName.getText().toString();
                try {
                    target = Integer.parseInt(et_target.getText().toString());
                    currentProgress = Integer.parseInt(et_currentProgress.getText().toString());
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                }

                if (target < 0)
                    target = 0;

                if (currentProgress < 0)
                    currentProgress = 0;
                if (taskName != null && target != 0){
                    if (currentProgress < target) {
                        Task task = new Task(AddTaskActivity.this, target, currentProgress, taskName);
                        task.saveToSharedPreferences();
                        finish();
                    }
                    else
                        Toast.makeText(AddTaskActivity.this, "Current Progress is bigger than target", Toast.LENGTH_LONG).show();
                }
                break;

            /*
            case R.id.tv_taskName:
                taskName = tv_taskName.getText().toString();
                break;

            case R.id.tv_target:
                target = Integer.parseInt(tv_target.getText().toString());
                if (target < 0)
                    target = 0;
                break;

            case R.id.tv_currentProgress:
                currentProgress = Integer.parseInt(tv_currentProgress.getText().toString());
                if (currentProgress < 0)
                    currentProgress = 0;
                break;
                */

            default:
                break;
        }
    }
}
