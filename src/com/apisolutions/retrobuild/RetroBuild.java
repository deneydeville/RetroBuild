package com.apisolutions.retrobuild;

import com.apisolutions.retrobuild.builds.BuildConfig;
import com.apisolutions.retrobuild.builds.ClassySharkBuildConfig;
import com.apisolutions.retrobuild.tasks.CompileSrc;
import com.apisolutions.retrobuild.tasks.ExpandThirdParty;
import com.apisolutions.retrobuild.tasks.MakeFolder;
import com.apisolutions.retrobuild.tasks.Task;
import com.apisolutions.retrobuild.tasks.WriteResultJar;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RetroBuild {

    private BuildConfig buildConfig;

    private RetroBuild(BuildConfig buildConfig) {
        this.buildConfig = buildConfig;
    }

    public void build() throws Exception {
        long start = System.currentTimeMillis();
        ArrayList<Task> tasks = new ArrayList<Task>();

        tasks.add(new MakeFolder(buildConfig));
        tasks.add(new CompileSrc(buildConfig));
        tasks.add(new ExpandThirdParty(buildConfig));
        tasks.add(new WriteResultJar(buildConfig));

        for (Task task : tasks) {
            task.process();
        }

        System.out.println("DONE "
                + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start)
                + " seconds");
    }

    private static RetroBuild with(BuildConfig buildConfig) {
        return new RetroBuild(buildConfig);
    }

    public static void main(String[] args) throws Exception {
        RetroBuild.with(new ClassySharkBuildConfig()).build();
    }
}
