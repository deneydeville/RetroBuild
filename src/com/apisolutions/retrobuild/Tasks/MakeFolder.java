package com.apisolutions.retrobuild.tasks;

import com.apisolutions.retrobuild.builds.BuildConfig;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class MakeFolder extends Task {
    public MakeFolder(BuildConfig buildConfig) {
        super(buildConfig);
    }

    @Override
    public void process() throws IOException {
        System.out.println("Preparing result folder");
        Path directory = Paths.get(buildConfig.getResultFolder());
        new File(buildConfig.getResultFolder()).mkdir();
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });

        new File(buildConfig.getResultFolder()).mkdir();
        System.out.println("Done result folder");
    }
}
