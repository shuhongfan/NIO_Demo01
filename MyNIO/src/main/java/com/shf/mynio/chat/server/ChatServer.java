package com.shf.mynio.chat.server;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {
    /**
     * 服务器启动的方法
     */
    @SneakyThrows
    public void startServer() {
//        1.创建Selector选择器
        Selector selector = Selector.open();

//        2.创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

//        3. 为channel通道绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(8000));

//        4. 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);

//        5.循环,等等有新连接接入
//        把channel通道注册到Selector选择器上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已经成功启动了");


        for (; ; ) {
//            获取channel数量
            int readChannels = selector.select();
            if (readChannels == 0) {
                continue;
            }

//            获取可用的Channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

//            遍历集合
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
//                移除set集合当前SelectKey
                iterator.remove();

//              6.根据就绪状态,调用对应方法实现具体业务操作
//                6.1 如果accept状态
                if (selectionKey.isAcceptable()) {
                    acceptOperator(serverSocketChannel, selector);
                }

//                6.2 如果可读状态
                if (selectionKey.isReadable()) {
                    readOperator(selector, selectionKey);
                }
            }

        }

    }

    /**
     * 处理可读状态操作
     * @param selector
     * @param selectionKey
     */
    @SneakyThrows
    private void readOperator(Selector selector, SelectionKey selectionKey) {
//        1. 从selectionKey 获取到已经就绪的通道
        SocketChannel channel = (SocketChannel) selectionKey.channel();

//        2. 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

//        3. 循环读取客户端消息
        int read = channel.read(byteBuffer);
        String message = "";
        if (read > 0) {
//            切换读模式
            byteBuffer.flip();

//            读取内容
            message += Charset.forName("UTF-8").decode(byteBuffer);
        }

//        4.将channel再次注册到选择器上,监听可读状态
        channel.register(selector, SelectionKey.OP_READ);

//        5. 把客户端发送消息，广播到其他客户端
        if (message.length() > 0) {
//            广播给其他的客户端
            System.out.println(message);
            castOtherClient(message, selector, channel);
        }

    }

    /**
     * 广播给其他客户端
     * @param message
     * @param selector
     * @param channel
     */
    @SneakyThrows
    private void castOtherClient(String message, Selector selector, SocketChannel channel) {
//        1. 获取到所有已经接入客户端
        Set<SelectionKey> selectionKeySet = selector.keys();

//        2.循环所有channel 广播消息
        for (SelectionKey selectionKey : selectionKeySet) {//            获取里面每个channel
            SelectableChannel targetChannel = selectionKey.channel();
//            不需要给自己发送
            if (targetChannel instanceof SocketChannel && targetChannel != channel) {
                ((SocketChannel) targetChannel).write(Charset.forName("UTF-8").encode(message));
            }
        }

    }

    /**
     * 处理接入状态操作
     * @param serverSocketChannel
     * @param selector
     */
    @SneakyThrows
    private void acceptOperator(ServerSocketChannel serverSocketChannel, Selector selector) {
//        1. 接入状态，创建sockertChannel
        SocketChannel socketChannel = serverSocketChannel.accept();

//        2. 把socketChannel设置非阻塞模式
        socketChannel.configureBlocking(false);

//        3.把 channel注册到selector选择器上，监听可读状态
        socketChannel.register(selector,SelectionKey.OP_READ);

//        4. 客户端回复信息
        socketChannel.write(Charset.forName("UTF-8").encode("欢迎进入聊天室,请注意隐私安全"));
    }

    public static void main(String[] args) {
        new ChatServer().startServer();
    }
}
