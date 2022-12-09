package com.shf.mynio.channel;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelDemo03 {
    @SneakyThrows
    public static void main(String[] args) {
        RandomAccessFile accessFile = new RandomAccessFile("C:\\Users\\shuho\\MyData\\NIO_Demo01\\MyNIO\\src\\main\\resources\\application.properties", "rw");
        FileChannel fromChannel = accessFile.getChannel();

        RandomAccessFile accessFile1 = new RandomAccessFile("C:\\Users\\shuho\\MyData\\NIO_Demo01\\MyNIO\\src\\main\\resources\\application-dev.properties", "rw");
        FileChannel toChannel = accessFile1.getChannel();

//        formChannel 传输到 toChannel
        long position = 0;
        long size = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, size);

        accessFile.close();
        accessFile1.close();
        System.out.println("over");
    }
}
