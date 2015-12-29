package com.apisolutions.retrobuild.Tasks;

import com.apisolutions.retrobuild.RetroBuild;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;

public class ExpandThirdParty implements Task {

    @Override
    public void process() throws Exception {
        extractJars();
    }

    private static void extractJars() throws Exception {
        ArrayList<File> jars = new ArrayList<File>();
        collectFileNamesTo(jars, RetroBuild.THIRD_PARTY_JARS);

        for (File jar : jars) {
            extractJar(jar.getAbsolutePath(), RetroBuild.RESULT_FOLDER);
        }
    }

    private static void extractJar(String jarFile, String destDir) throws Exception {
        JarFile jar = new JarFile(jarFile);
        Enumeration entries = jar.entries();
        while (entries.hasMoreElements()) {
            java.util.jar.JarEntry file = (java.util.jar.JarEntry) entries.nextElement();
            java.io.File f = new java.io.File(destDir + java.io.File.separator + file.getName());
            if (file.isDirectory()) { // if its a directory, create it
                f.mkdir();
                continue;
            }
            java.io.InputStream is = jar.getInputStream(file); // get the input stream

            f.getParentFile().mkdirs();
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);

            while (is.available() > 0) {  // write contents of 'is' to 'fos'
                fos.write(is.read());
            }
            fos.close();
            is.close();
        }
    }

    private static void collectFileNamesTo(ArrayList<File> to, String from) {
        File directory = new File(from);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                to.add(file);
            } else if (file.isDirectory()) {
                collectFileNamesTo(to, file.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new ExpandThirdParty().process();
    }
}
