package com.apisolutions.retrobuild.tasks;

import com.apisolutions.retrobuild.builds.BuildConfig;
import com.apisolutions.retrobuild.builds.ClassySharkBuildConfig;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class WriteResultJar extends Task {

    public WriteResultJar(BuildConfig buildConfig) {
        super(buildConfig);
    }

    @Override
    public void process() throws Exception {
        System.out.println("Writing result jar");
        //prepare Manifest file
        Manifest manifest = new Manifest();
        Attributes global = manifest.getMainAttributes();
        global.put(Attributes.Name.MANIFEST_VERSION, "1.0.0");
        global.put(Attributes.Name.MAIN_CLASS, buildConfig.getMainClassInJar());

        //create required jar name
        String jarFileName = buildConfig.getResultFolder() + "/" + buildConfig.getJarName();
        JarOutputStream jos = null;
        try {
            File jarFile = new File(jarFileName);
            jarFile.createNewFile();
            OutputStream os = new FileOutputStream(jarFile);
            jos = new JarOutputStream(os, manifest);

            addFolderToJar(new File(buildConfig.getResultFolder()), jos, buildConfig.getResultFolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
        jos.close();
        System.out.println("Done Writing result jar");
    }

    private static void addFolderToJar(File source, JarOutputStream target, String jarResultFolder) throws IOException {
        BufferedInputStream in = null;
        try {
            if (source.isDirectory()) {
                String name = source.getPath().replace("\\", "/");
                if (!name.isEmpty()) {

                    // zip format all folder names end with '/'
                    String fName = convert(name, jarResultFolder);

                    if (!fName.endsWith("/")) {
                        fName += "/";
                    }
                    JarEntry entry = new JarEntry(fName);
                    entry.setTime(source.lastModified());
                    target.putNextEntry(entry);
                    target.closeEntry();
                }
                for (File nestedFile : source.listFiles())
                    addFolderToJar(nestedFile, target, jarResultFolder);
                return;
            }

            JarEntry entry = new JarEntry(convert(source.getPath(), jarResultFolder).replace("\\", "/"));
            entry.setTime(source.lastModified());

            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));

            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    break;
                }
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    private static String convert(String path, String base) {
        String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
        return relative;
    }

    public static void main(String[] args) throws Exception {
        //new WriteResultJar(new SilverGhostBuildConfig()).process();
        new WriteResultJar(new ClassySharkBuildConfig()).process();
    }
}
