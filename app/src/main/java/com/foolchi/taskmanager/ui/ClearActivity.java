package com.foolchi.taskmanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foolchi.taskmanager.R;
import com.foolchi.taskmanager.provider.TaskProvider;
/**
 * Created by foolchi on 7/9/14.
 */
public class ClearActivity extends Fragment {

    private Button bt_clear;
    private LayoutInflater inflater;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View currentTaskView = inflater.inflate(R.layout.clear_task_layout, container, false);
        this.inflater = inflater;

        bt_clear = (Button)currentTaskView.findViewById(R.id.bt_clear);
        bt_clear.setText("Clear");
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskProvider taskProvider = new TaskProvider(inflater.getContext());
                taskProvider.removeAll();
                bt_clear.setText("Finished");
            }
        });

        return currentTaskView;
    }
}
