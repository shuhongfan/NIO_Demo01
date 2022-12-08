package com.atguigu.channel;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//FileChanne写操作
public class FileChannelDemo2 {

    public static void main(String[] args) throws Exception {
        // 打开FileChannel
        RandomAccessFile aFile = new RandomAccessFile("d:\\atguigu\\001.txt","rw");
        FileChannel channel = aFile.getChannel();

        //创建buffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        String newData = "data atguigu";
        buffer.clear();

        //写入内容
        buffer.put(newData.getBytes());

        buffer.flip();

        //FileChannel完成最终实现
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }

        //关闭
        channel.close();
    }
}
