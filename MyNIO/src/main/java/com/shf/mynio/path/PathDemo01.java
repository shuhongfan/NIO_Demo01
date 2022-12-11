package com.shf.mynio.path;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo01 {
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\shuho\\IdeaProjects\\NIO_Demo01\\MyNIO\\src\\main\\resources\\application.properties");

        path = path.normalize();

    }
}
