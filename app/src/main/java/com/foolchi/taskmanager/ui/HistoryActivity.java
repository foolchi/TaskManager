package com.foolchi.taskmanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foolchi.taskmanager.R;

/**
 * Created by foolchi on 7/8/14.
 */
public class HistoryActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View historyView = inflater.inflate(R.layout.history_layout, container, false);
        return historyView;
    }
}
