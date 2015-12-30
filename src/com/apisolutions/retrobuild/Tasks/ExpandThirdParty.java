package com.apisolutions.retrobuild.Tasks;

import com.apisolutions.retrobuild.RetroBuild;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExpandThirdParty implements Task {

    @Override
    public void process() throws Exception {
        System.out.println("Expanding 3rd party jars");
        extractJars();
        System.out.println("Done Expanding 3rd party jars");
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
        Enumeration<? extends JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
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
    }

    private static void collectFileNamesTo(ArrayList<File> to, String from) {
        File directory = new File(from);

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
