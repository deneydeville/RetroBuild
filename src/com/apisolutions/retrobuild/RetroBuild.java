package com.apisolutions.retrobuild;

import com.apisolutions.retrobuild.Tasks.CompileSrc;
import com.apisolutions.retrobuild.Tasks.ExpandThirdParty;
import com.apisolutions.retrobuild.Tasks.MakeFolder;
import com.apisolutions.retrobuild.Tasks.Task;
import com.apisolutions.retrobuild.Tasks.WriteResultJar;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RetroBuild {
    public static final String SOURCES_FOLDER =
            "/Users/bfarber/Development/android-classyshark/ClassySharkWS/src";

    public static final String RESULT_FOLDER =
            "/Users/bfarber/Desktop/test";

    public static final String THIRD_PARTY_JARS =
            "/Users/bfarber/Development/android-classyshark/third_party";

    public static final String MAIN_CLASS_IN_JAR =
            "com.google.classyshark.ui.Main";

    public static final String JAR_NAME =
            "ClassyShark.jar";

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        ArrayList<Task> tasks = new ArrayList<Task>();

        tasks.add(new MakeFolder());
        tasks.add(new CompileSrc());
        tasks.add(new ExpandThirdParty());
        tasks.add(new WriteResultJar());

        for (Task task : tasks) {
            task.process();
        }

        System.out.println("DONE "
                + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start)
                + " seconds");
    }
}
