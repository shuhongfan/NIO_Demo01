package com.shf.mynio.selector;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo01 {
    @SneakyThrows
    public static void main(String[] args) {
//        创建selector
        Selector selector = Selector.open();

//        通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

//        非阻塞
        serverSocketChannel.configureBlocking(false);

//        绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9999));

//        将通道注册到选择器
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

//        查询已经就绪通道操作
        Set<SelectionKey> selectionKeys = selector.selectedKeys();

//        遍历集合
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
//            判断key就绪状态操作
            if (key.isAcceptable()) {
            } else if (key.isConnectable()) {
            } else if (key.isReadable()){
            } else if (key.isWritable()) {
            }
        }
        iterator.remove();

    }
}
