package com.shf.mynio.files;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesDemo {
    @SneakyThrows
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\shuho\\IdeaProjects\\NIO_Demo01\\MyNIO\\src\\main\\resources");

        Path directory = Files.createDirectory(path);
    }
}
