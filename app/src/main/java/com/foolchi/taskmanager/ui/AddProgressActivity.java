package com.foolchi.taskmanager.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.foolchi.taskmanager.R;
import com.foolchi.taskmanager.domain.Task;
/**
 * Created by foolchi on 7/10/14.
 * Change the current progress
 */
public class AddProgressActivity extends Activity implements OnClickListener{

    private Dialog dialog;
    private EditText et_currentProgress;
    private TextView tv_target;
    private Button bt_ok, bt_cancel;
    private Task task;
    private int currentProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dialog = new Dialog(this);

        View view = View.inflate(this, R.layout.add_progress_layout, null);
        et_currentProgress = (EditText)view.findViewById(R.id.et_currentProgress);
        tv_target = (TextView)view.findViewById(R.id.tv_target);
        bt_ok = (Button)view.findViewById(R.id.bt_ok);
        bt_cancel = (Button)view.findViewById(R.id.bt_cancel);
        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();

        task = new Task(this, data.getLong("id"), data.getInt("currentProgress"), data.getInt("target"), data.getString("taskName"));
        System.out.println(task.toString());
        currentProgress = task.getCurrentProgress();

        dialog.setTitle(task.getTaskName());

        tv_target.setText(task.getTarget()+"");
        et_currentProgress.setText(task.getCurrentProgress()+"");

        dialog.setContentView(view);
        dialog.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_ok:
                currentProgress = Integer.parseInt(et_currentProgress.getText().toString());
                if (currentProgress < 0) {
                    currentProgress = 0;
                    et_currentProgress.setText(currentProgress+"");
                }
                else if (currentProgress > task.getTarget()){
                    currentProgress = task.getTarget();
                    et_currentProgress.setText(currentProgress+"");
                }

                Intent intent_ok = getIntent();

                if (currentProgress != task.getCurrentProgress()) {
                    task.setCurrentProgress(currentProgress);
                    task.saveToSharedPreferences();
                    AddProgressActivity.this.setResult(1, intent_ok);
                }
                dialog.dismiss();

                AddProgressActivity.this.finish();
                break;

            case R.id.bt_cancel:
                Intent intent_cancel = getIntent();
                AddProgressActivity.this.setResult(0, intent_cancel);
                dialog.dismiss();
                AddProgressActivity.this.finish();
                break;

            default:
                break;
        }
    }
}
