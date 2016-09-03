package com.apisolutions.retrobuild.builds;

import java.util.LinkedList;
import java.util.List;

public class CrazySharkBuildConfig extends ClassySharkBuildConfig {
    public CrazySharkBuildConfig() {
        super();
    }

    @Override
    public List<String> getOptionalThirdPartyJarsFolders() {
        String anotherPath = BASE + "/Development/external-libs";
        List<String> result = new LinkedList<String>();
        result.add(anotherPath);

        return result;
    }

    @Override
    public String getResultFolder() {
        return BASE + "/Desktop/crazyshark_build";
    }

    @Override
    public String getJarName() {
        return "CrazyShark.jar";
    }
}
