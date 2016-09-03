package com.apisolutions.retrobuild.builds;

import java.util.List;

public abstract class BuildConfig {

    public abstract String getSourcesFolder();

    public abstract String getResultFolder();

    public abstract String getThirdPartyJarsFolder();

    public abstract String getResourcesFolder();

    public abstract String getMainClassInJar();

    public abstract String getJarName();

    public abstract List<String> getOptionalThirdPartyJarsFolders();
}
