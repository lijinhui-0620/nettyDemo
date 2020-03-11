package com.lk.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @auther: kim
 * @create: 2020-02-21 10:18
 * @description:
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        //1.创建ServerSocketSchannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2.配置成非阻塞
        serverSocketChannel.configureBlocking(false);

        //3.绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));

        //4.创建Selector
        Selector selector = Selector.open();

        //5.把serverSocketChannel注册到selector上，且事件是 op_accept
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动，监听端口【8888】");
        //循环等待客户端连接
        while (true) {

            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                final SocketChannel client;
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    client = channel.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                } else if (key.isValid() && key.isReadable()) {
                    client = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    client.read(buffer);
                    buffer.flip();
                    System.out.println(client + ":" + new String(buffer.array()));
                }
            }
            selectionKeys.clear();
        }

    }
}
