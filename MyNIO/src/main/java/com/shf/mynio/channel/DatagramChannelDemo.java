package com.shf.mynio.channel;

import lombok.SneakyThrows;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DatagramChannelDemo {

    /**
     * 发送的实现
     */
    @Test
    @SneakyThrows
    public void sendDataGram() {
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1", 9999);

//        发送
        while (true) {
            ByteBuffer buffer = ByteBuffer.wrap("发送shf".getBytes(StandardCharsets.UTF_8));
            sendChannel.send(buffer, sendAddress);
            System.out.println("已经完成发送");
            Thread.sleep(1000);
        }

    }

    /**
     * 接收的实现
     */
    @Test
    @SneakyThrows
    public void receiverDatagram() {
//        打开datagramChannel
        DatagramChannel receiverChannel = DatagramChannel.open();
        InetSocketAddress receiveAddress = new InetSocketAddress(9999);

//        绑定
        receiverChannel.bind(receiveAddress);

//        buffer
        ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

//        接收
        while (true) {
            receiveBuffer.clear();
            SocketAddress socketAddress = receiverChannel.receive(receiveBuffer);
            receiveBuffer.flip();
            System.out.println(socketAddress.toString());
            System.out.println(StandardCharsets.UTF_8.decode(receiveBuffer));
        }
    }

    @SneakyThrows
    @Test
    public void testConnect() {
//        打开DatagramChannel
        DatagramChannel connChannel = DatagramChannel.open();

//        绑定
        connChannel.bind(new InetSocketAddress(9999));

//        连接
        connChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

//        write方法
        connChannel.write(ByteBuffer.wrap("发送shf".getBytes(StandardCharsets.UTF_8)));

//        buffer
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

        while (true) {
            readBuffer.clear();
            connChannel.read(readBuffer);
            readBuffer.flip();
            System.out.println(StandardCharsets.UTF_8.decode(readBuffer));
        }
    }

}
