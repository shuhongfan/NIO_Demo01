package com.shf.mynio.FileLock;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLockDemo01 {

    @SneakyThrows
    public static void main(String[] args) {
        String input = "server.port=8888";
        System.out.println("input:" + input);

        ByteBuffer buffer = ByteBuffer.wrap(input.getBytes());
        String filePath = "C:\\Users\\shuho\\IdeaProjects\\NIO_Demo01\\MyNIO\\src\\main\\resources\\application.properties";

        Path path = Paths.get(filePath);
        FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        channel.position(channel.size() - 1);

//        加锁
        FileLock lock = channel.lock(0L,Long.MAX_VALUE,true);
        System.out.println("是否共享锁：" + lock.isShared());

        channel.write(buffer);
        channel.close();

//        读文件
        readFile(filePath);
    }

    @SneakyThrows
    public static void readFile(String filePath) {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String tr = bufferedReader.readLine();
        System.out.println("读取出内容：");
        while (tr != null) {
            System.out.print(" " + tr);
            tr = bufferedReader.readLine();
        }
        fileReader.close();
        bufferedReader.close();
    }
}
