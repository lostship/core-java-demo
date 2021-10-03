package com.example.corejava.io.l1;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTest {

    // Path相对化绝对路径、解析相对路径、复制目录
    public static void main(String[] args) throws IOException {
        Path source = Paths.get("");
        Path target = Paths.get("");
        Files.walk(source).forEach(p -> {
            try {
                Path q = target.resolve(source.relativize(p));
                if (Files.isDirectory(p)) {
                    Files.createDirectories(q);
                } else {
                    Files.copy(p, q);
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

}
