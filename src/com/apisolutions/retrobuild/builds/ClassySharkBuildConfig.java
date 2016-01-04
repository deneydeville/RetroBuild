package com.apisolutions.retrobuild.builds;

public class ClassySharkBuildConfig extends BuildConfig {
    private static final String BASE = System.getProperty("user.home");

    @Override
    public String getSourcesFolder() {
        return BASE + "/Development/android-classyshark/ClassySharkWS/src";
    }

    @Override
    public String getResultFolder() {
        return BASE + "/Desktop/test";
    }

    @Override
    public String getThirdPartyJarsFolder() {
        return BASE + "/Development/android-classyshark/third_party";
    }

    @Override
    public String getMainClassInJar() {
        return "com.google.classyshark.ui.Main";
    }

    @Override
    public String getJarName() {
        return "ClassyShark.jar";
    }
}
