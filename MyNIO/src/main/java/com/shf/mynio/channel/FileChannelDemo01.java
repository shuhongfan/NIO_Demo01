package com.shf.mynio.channel;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo01 {

//    FileChannel读取数据到buffer中
    @SneakyThrows
    public static void main(String[] args) {
        RandomAccessFile accessFile = new RandomAccessFile("C:\\Users\\shuho\\MyData\\NIO_Demo01\\MyNIO\\src\\main\\resources\\application.properties", "rw");
        FileChannel channel = accessFile.getChannel();

//        创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int byteRead = channel.read(buffer);
        while (byteRead != -1) {
            System.out.println("读取了：" + byteRead);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            buffer.clear();
            byteRead = channel.read(buffer);
        }
        accessFile.close();
        System.out.println("结束");

    }
}
