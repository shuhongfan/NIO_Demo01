package com.shf.mynio.channel;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
    @SneakyThrows
    public static void main(String[] args) {
//        创建SocketChannel
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));

//        设置阻塞模式还是非阻塞模式
        socketChannel.configureBlocking(false);

//        读操作
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        socketChannel.read(byteBuffer);
        socketChannel.close();
        System.out.println("read over");
    }
}
