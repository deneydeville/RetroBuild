package com.apisolutions.retrobuild.tasks;

import com.apisolutions.retrobuild.builds.BuildConfig;

public abstract class Task {

    protected BuildConfig buildConfig;

    public Task(BuildConfig buildConfig) {
        this.buildConfig = buildConfig;
    }
    public abstract void process() throws Exception;
}
