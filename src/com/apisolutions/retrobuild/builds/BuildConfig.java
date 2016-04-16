package com.apisolutions.retrobuild.builds;

public abstract class BuildConfig {

    public abstract String getSourcesFolder();

    public abstract String getResultFolder();

    public abstract String getThirdPartyJarsFolder();

    public abstract String getResourcesFolder();

    public abstract String getMainClassInJar();

    public abstract String getJarName();
}
