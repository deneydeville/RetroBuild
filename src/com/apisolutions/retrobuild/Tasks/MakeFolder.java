package com.apisolutions.retrobuild.Tasks;

import com.apisolutions.retrobuild.RetroBuild;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class MakeFolder implements Task {
    @Override
    public void process() throws IOException {

        Path directory = Paths.get(RetroBuild.RESULT_FOLDER);
        new File(RetroBuild.RESULT_FOLDER).mkdir();
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

        new File(RetroBuild.RESULT_FOLDER).mkdir();
    }
}
