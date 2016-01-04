package com.apisolutions.retrobuild.tasks;

import com.apisolutions.retrobuild.builds.BuildConfig;
import com.apisolutions.retrobuild.builds.ClassySharkBuildConfig;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExpandThirdParty extends Task {

    public ExpandThirdParty(BuildConfig buildConfig) {
        super(buildConfig);
    }

    @Override
    public void process() throws Exception {
        System.out.println("Expanding 3rd party jars");
        extractJars(buildConfig.getThirdPartyJarsFolder(), buildConfig.getResultFolder());
        System.out.println("Done Expanding 3rd party jars");
    }

    private static void extractJars(String thirdPartyJarsFolder, String resultFolder) throws Exception {
        ArrayList<File> jars = new ArrayList<File>();
        collectJarNamesTo(jars, thirdPartyJarsFolder);

        for (File jar : jars) {
            extractJar(jar.getAbsolutePath(), resultFolder);
        }
    }

    private static void extractJar(String jarFile, String destDir) throws Exception {
        JarFile jar = new JarFile(jarFile);
        Enumeration<? extends JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            try {
                JarEntry file = entries.nextElement();
                java.io.File f = new java.io.File(destDir + java.io.File.separator + file.getName());
                if (file.isDirectory()) {
                    f.mkdir();
                    continue;
                }

                f.getParentFile().mkdirs();
                f.createNewFile();
                java.io.InputStream is = jar.getInputStream(file);

                FileOutputStream fos = new FileOutputStream(f);

                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                is.close();
            }
            catch (Exception e) {
                // TODO figure out why JarEntry file = entries.nextElement();
                // TODO throws Null Exception, the created jar is totally fine ?
            }
        }
    }

    private static void collectJarNamesTo(ArrayList<File> to, String from) {
        File directory = new File(from);

        File[] fList = directory.listFiles();

        if (fList == null) {
            return;
        }

        for (File file : fList) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                to.add(file);
            } else if (file.isDirectory()) {
                collectJarNamesTo(to, file.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new ExpandThirdParty(new ClassySharkBuildConfig()).process();
    }
}
