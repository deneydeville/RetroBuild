package com.apisolutions.retrobuild;

import com.apisolutions.retrobuild.Tasks.CompileSrc;
import com.apisolutions.retrobuild.Tasks.ExpandThirdParty;
import com.apisolutions.retrobuild.Tasks.MakeFolder;
import com.apisolutions.retrobuild.Tasks.Task;
import com.apisolutions.retrobuild.Tasks.WriteResultJar;
import java.util.ArrayList;

public class RetroBuild {
    public static final String SOURCES_FOLDER =
            "/Users/bfarber/Development/android-classyshark/ClassySharkWS/src";

    public static final String RESULT_FOLDER =
            "/Users/bfarber/Desktop/test";

    public static final String THIRD_PARTY_JARS =
            "/Users/bfarber/Development/android-classyshark/third_party";

    public static void main(String[] args) throws Exception {
        ArrayList<Task> tasks = new ArrayList<Task>();

        tasks.add(new MakeFolder());
        tasks.add(new CompileSrc());
        tasks.add(new ExpandThirdParty());
        tasks.add(new WriteResultJar());

        for(Task task : tasks) {
            task.process();
        }
    }
}
