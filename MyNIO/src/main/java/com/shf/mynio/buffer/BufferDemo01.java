package com.shf.mynio.buffer;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo01 {
    @SneakyThrows
    @Test
    public void buffer01() {
//        FileChannel
        RandomAccessFile accessFile = new RandomAccessFile("C:\\Users\\shuho\\MyData\\NIO_Demo01\\MyNIO\\src\\main\\resources\\application.properties", "rw");
        FileChannel channel = accessFile.getChannel();

//        创建buffer,大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);

//        读
        int bytesRead = channel.read(buffer);

        while (bytesRead != -1) {
//            read模式
            buffer.flip();

            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }

//            清空buffer
            buffer.clear();
            bytesRead = channel.read(buffer);
        }

        accessFile.close();
    }

    @Test
    public void buffer02() {
//        创建buffer
        IntBuffer buffer = IntBuffer.allocate(8);

//        buffer放
        for (int i = 0; i < buffer.capacity(); i++) {
            int j = 2 * (i + 1);
            buffer.put(j);
        }

//        重置缓冲区
        buffer.flip();

//        获取
        while (buffer.hasRemaining()) {
            int value = buffer.get();
            System.out.print(value+" ");
        }

    }
}
