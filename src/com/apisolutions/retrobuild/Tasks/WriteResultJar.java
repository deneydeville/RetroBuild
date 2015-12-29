package com.apisolutions.retrobuild.Tasks;

import com.apisolutions.retrobuild.RetroBuild;
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

public class WriteResultJar implements Task {

    @Override
    public void process() throws Exception {
        System.out.println("Writing result jar");
        //prepare Manifest file
        Manifest manifest = new Manifest();
        Attributes global = manifest.getMainAttributes();
        global.put(Attributes.Name.MANIFEST_VERSION, "1.0.0");
        global.put(Attributes.Name.MAIN_CLASS, RetroBuild.MAIN_CLASS_IN_JAR);

        //create required jar name
        String jarFileName = RetroBuild.RESULT_FOLDER + "/" + RetroBuild.JAR_NAME;
        JarOutputStream jos = null;
        try {
            File jarFile = new File(jarFileName);
            OutputStream os = new FileOutputStream(jarFile);
            jos = new JarOutputStream(os, manifest);

            addFolderToJar(new File(RetroBuild.RESULT_FOLDER), jos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        jos.close();
        System.out.println("Done Writing result jar");
    }

    private static void addFolderToJar(File source, JarOutputStream target) throws IOException {
        BufferedInputStream in = null;
        try {
            if (source.isDirectory()) {
                String name = source.getPath().replace("\\", "/");
                if (!name.isEmpty()) {
                    if (!name.endsWith("/")) {
                        name += "/";
                    }
                    JarEntry entry = new JarEntry(convert(name, RetroBuild.RESULT_FOLDER));
                    entry.setTime(source.lastModified());
                    target.putNextEntry(entry);
                    target.closeEntry();
                }
                for (File nestedFile : source.listFiles())
                    addFolderToJar(nestedFile, target);
                return;
            }

            JarEntry entry = new JarEntry(convert(source.getPath(), RetroBuild.RESULT_FOLDER).replace("\\", "/"));
            entry.setTime(source.lastModified());

            // TODO hack need to fix, foreign manifest
            if (!entry.getName().equals("META-INF/MANIFEST.MF")) {
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
            }
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
        new WriteResultJar().process();
    }
}
