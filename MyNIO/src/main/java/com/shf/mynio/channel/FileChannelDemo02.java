package com.shf.mynio.channel;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel 写操作
 */
public class FileChannelDemo02 {
    @SneakyThrows
    public static void main(String[] args) {
//        打开FileChannel
        RandomAccessFile accessFile = new RandomAccessFile("C:\\Users\\shuho\\MyData\\NIO_Demo01\\MyNIO\\src\\main\\resources\\application.properties", "rw");
        FileChannel channel = accessFile.getChannel();

//        创建buffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        String newData = "server.port=8888";
        buffer.clear();

//        写入内容
        buffer.put(newData.getBytes());

        buffer.flip();

//        FileChannel 完成最终实现
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }

//        关闭channel
        channel.close();

    }
}
