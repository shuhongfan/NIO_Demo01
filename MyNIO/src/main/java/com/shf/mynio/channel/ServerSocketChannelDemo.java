package com.shf.mynio.channel;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {
    @SneakyThrows
    public static void main(String[] args) {
//        端口号
        int port = 8888;

//        buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello shf".getBytes());

//        ServerSockerChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();

//        绑定
        ssc.socket().bind(new InetSocketAddress(port));

//        设置非阻塞模式
        ssc.configureBlocking(false);

//        监听是否有新链接进入
        while (true) {
            System.out.println("Waiting for connection");
            SocketChannel sc = ssc.accept();
            if (sc == null) { // 没有链接传入
                System.out.println("null");
                Thread.sleep(2000);
            } else {
                System.out.println("InComing connection from:" + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
