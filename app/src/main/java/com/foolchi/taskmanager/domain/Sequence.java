package com.foolchi.taskmanager.domain;

import java.util.Date;

/**
 * Created by foolchi on 7/8/14.
 */
public class Sequence {
    public Sequence(Date date, int currentProcess){
        this.date = date;
        this.currentProcess = currentProcess;
    }

    public Date date;
    public int currentProcess;
}
