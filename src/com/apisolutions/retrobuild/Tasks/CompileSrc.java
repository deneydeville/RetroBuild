package com.apisolutions.retrobuild.Tasks;

import com.apisolutions.retrobuild.RetroBuild;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * based on https://github.com/EsotericSoftware/scar/blob/master/src/com/esotericsoftware/scar/Scar.java
 */
public class CompileSrc implements Task {

    @Override
    public void process() throws Exception {
        System.out.println("Compiling");
        ArrayList<String> allSources = new ArrayList<String>();
        collectFileNamesTo(allSources, RetroBuild.SOURCES_FOLDER, ".java");

        ArrayList<String> allThirdParty = new ArrayList<String>();
        collectFileNamesTo(allThirdParty, RetroBuild.THIRD_PARTY_JARS, ".jar");

        compile(allSources, allThirdParty, RetroBuild.RESULT_FOLDER, "1.7");
        System.out.println("Done Compiling");
    }

    /**
     * True if running on a Windows OS.
     */
    public static final boolean isWindows =
            System.getProperty("os.name").toLowerCase().contains("windows");

    public static void compile(ArrayList<String> sourceFilesToCompile,
                               ArrayList<String> thirdPartyJars,
                               String outputDir,
                               String targetVersion) {

        if (sourceFilesToCompile.isEmpty()) {
            System.out.println("No source files found.");
            return;
        }

        ArrayList<String> compilerArgs =
                generateCompilerArgs(sourceFilesToCompile,
                        thirdPartyJars,
                        outputDir,
                        targetVersion);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new RuntimeException(
                    "No compiler available. Ensure you are running from a 1.6+ JDK, "
                            + "and not a JRE.");
        }
        if (compiler.run(System.in, System.out, System.err,
                compilerArgs.toArray(new String[compilerArgs.size()]))
                != 0) {
            throw new RuntimeException(
                    "Error during compilation.\nSource: " + " files\nClasspath: ");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
    }

    private static ArrayList<String> generateCompilerArgs(ArrayList<String> sourceFilesToCompile,
                                                          ArrayList<String> thirdPartyJars, String outputDir, String targetVersion) {
        ArrayList<String> compilerArgs = new ArrayList();
        //if (true) compilerArgs.add("-verbose");
        compilerArgs.add("-d");
        compilerArgs.add(outputDir);
        compilerArgs.add("-g:source,lines");
        compilerArgs.add("-source");
        compilerArgs.add(targetVersion);
        compilerArgs.add("-target");
        compilerArgs.add(targetVersion);
        compilerArgs.addAll(sourceFilesToCompile);

        String classpath = System.getProperty("java.class.path");

        for (String s1 : thirdPartyJars) {
            classpath += isWindows ? (";") + s1 : (":") + s1;
        }

        compilerArgs.addAll(Arrays.asList("-classpath", classpath));
        return compilerArgs;
    }

    private static void collectFileNamesTo(ArrayList<String> to,
            String from, String which) {
        File directory = new File(from);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            // check suffix
            if (file.isFile() && file.getName().endsWith(which)) {
                to.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                collectFileNamesTo(to, file.getAbsolutePath(), which);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new CompileSrc().process();
    }
}
