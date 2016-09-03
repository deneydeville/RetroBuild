package com.apisolutions.retrobuild.builds;

import java.util.LinkedList;
import java.util.List;

public class SilverGhostBuildConfig extends BuildConfig {
    private static final String BASE = System.getProperty("user.home");

    @Override
    public String getSourcesFolder() {
        return BASE + "/Development/android-classyshark/ClassySharkWS/src/com/"
                + "google/silverghost";
    }

    @Override
    public String getResultFolder() {
        return BASE + "/Desktop/silverghost_build";
    }

    @Override
    public String getThirdPartyJarsFolder() {
        return BASE + "/Development/android-classyshark/third_party";
    }

    @Override
    public String getResourcesFolder() {
        return "";
    }

    @Override
    public String getMainClassInJar() {
        return "com.google.silverghost."
                + "translator.java.StressTest";
    }

    @Override
    public String getJarName() {
        return "c.jar";
    }

    @Override
    public List<String> getOptionalThirdPartyJarsFolders() {
        return new LinkedList<String>();
    }
}
