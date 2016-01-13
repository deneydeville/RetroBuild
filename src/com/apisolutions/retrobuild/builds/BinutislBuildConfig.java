package com.apisolutions.retrobuild.builds;

public class BinutislBuildConfig extends BuildConfig {
    private static final String BASE = System.getProperty("user.home");

    @Override
    public String getSourcesFolder() {
        return BASE + "/Development/java-binutils/src";
    }

    @Override
    public String getResultFolder() {
        return BASE + "/Desktop/binutils_build";
    }

    @Override
    public String getThirdPartyJarsFolder() {
        return "";
    }

    @Override
    public String getMainClassInJar() {
        return "nl.lxtreme.binutils.elf.Elf";
    }

    @Override
    public String getJarName() {
        return "java-binutils.jar";
    }
}
