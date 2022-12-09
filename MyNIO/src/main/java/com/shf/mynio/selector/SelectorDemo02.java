package com.shf.mynio.selector;

import lombok.SneakyThrows;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 第一步：创建ServerSocketChannel 通道，绑定监听端口
 * 第二步：设置通道是非阻塞的
 * 第三步：创建Selector选择器
 * 第四步：把Channel注册到Selector选择器上，监听连接事件
 * 第五步：调用Selector的select方法（循环调用），监测通道的就绪状况
 * 第六步：调用SelectKeys方法获取就绪channel集合
 * 第七步：遍历就绪channel集合，判断就绪事件类型，实现具体的业务操作
 * 第八步：根据业务，是否需要再次注册监听事件，实现具体的业务操作
 */
public class SelectorDemo02 {

    /**
     * 客户端
     */
    @Test
    @SneakyThrows
    public void clientDemo() {
//        1.获取通道，绑定主机和端口号
        SocketChannel socketChannel =
                SocketChannel.open(new InetSocketAddress("127.0.0.1",8080));

//        2.切换到非阻塞模式
        socketChannel.configureBlocking(false);

//        3.创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

//        4.写入buffer数据
        byteBuffer.put("hello world".getBytes());

//        5.切换模式
        byteBuffer.flip();

//        6.写入通道
        socketChannel.write(byteBuffer);

//        7.关闭
        byteBuffer.clear();
    }

    /**
     * 服务端代码
     */
    @Test
    @SneakyThrows
    public void serverDemo() {
//        1. 获取服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

//        2.切换非阻塞模式
        serverSocketChannel.configureBlocking(false);

//        3.创建buffer
        ByteBuffer serverByteBuffer = ByteBuffer.allocate(1024);

//        4. 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8080));

//        5. 获取selector选择器
        Selector selector = Selector.open();

//        6. 通道注册到选择器，进行监听
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

//        7. 选择器进行轮询，进行后续操作
        while (selector.select()>0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
//            遍历
            Iterator<SelectionKey> selectionKeysIterator = selectionKeys.iterator();

            while (selectionKeysIterator.hasNext()) {
//                获取已经就绪的操作
                SelectionKey selectionKey = selectionKeysIterator.next();
//                判断是什么操作
                if (selectionKey.isAcceptable()) {
//                    获取链接
                    SocketChannel accept = serverSocketChannel.accept();

//                    切换到非阻塞模式
                    accept.configureBlocking(false);

//                    注册
                    accept.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

//                    读取数据
                    int length = 0;
                    while ((length=channel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, length));
                        byteBuffer.clear();
                    }
                }
            }
            selectionKeysIterator.remove();

        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        //        1.获取通道，绑定主机和端口号
        SocketChannel socketChannel =
                SocketChannel.open(new InetSocketAddress("127.0.0.1",8080));

//        2.切换到非阻塞模式
        socketChannel.configureBlocking(false);

//        3.创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();

//        4.写入buffer数据
            byteBuffer.put((new Date().toString()+"---->"+str).getBytes());

//        5.切换模式
            byteBuffer.flip();

//        6.写入通道
            socketChannel.write(byteBuffer);

//        7.关闭
            byteBuffer.clear();
        }
    }
}
