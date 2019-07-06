package com.boaz.dragonski.myremoteserverapp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExe {
    private static final AppExe myInstance = new AppExe(Executors.newSingleThreadExecutor());
    private Executor mThread;
    private AppExe(Executor mThread) {
        this.mThread = mThread;
    }
    public static AppExe getInstance() {
        return myInstance;
    }
    public Executor getThread() {
        return mThread;
    }
}
