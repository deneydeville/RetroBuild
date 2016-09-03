package com.apisolutions.retrobuild.tasks;

import com.apisolutions.retrobuild.builds.BuildConfig;
import java.util.List;

public class ExpandThirdPartyFolders extends ExpandThirdParty {

    public ExpandThirdPartyFolders(BuildConfig buildConfig) {
        super(buildConfig);
    }

    @Override
    public void process() throws Exception {
        System.out.println("Expanding additional 3rd party jars");

        List<String> optionalThirdPartyFolders =
                buildConfig.getOptionalThirdPartyJarsFolders();

        for (String ThirdPartyFolder : optionalThirdPartyFolders) {
            extractJars(ThirdPartyFolder, buildConfig.getResultFolder());
        }

        System.out.println("Done Expanding additional 3rd party jars");
    }
}