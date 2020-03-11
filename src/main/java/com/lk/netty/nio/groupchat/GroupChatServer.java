package com.lk.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @auther: kim
 * @create: 2020-02-22 10:34
 * @description: 群聊服务器
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private int port = 8888;

    private Map<String, SocketChannel> clients = new HashMap<>(64);

    public GroupChatServer() throws IOException {
        selector = Selector.open();
        listenChannel = ServerSocketChannel.open();
        listenChannel.configureBlocking(false);
        listenChannel.socket().bind(new InetSocketAddress(getPort()));
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    //监听
    public void listen() {
        Selector selector = getSelector();
        try {
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isValid() && key.isAcceptable()) {
                        accept(key);
                    } else if (key.isValid() && key.isReadable()) {
                        read(key);
                    }

                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    //可读事件
    private void read(SelectionKey key) {
        SocketChannel sc = null;
        try {
            sc = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = sc.read(buffer);
            if (count > 0) {
                buffer.flip();
                String msg = new String(buffer.array());
                System.out.println("from 客户端：" + msg);
                sendInfoToOtherClient(sc, msg);
            }
        } catch (IOException e) {
            try {
                System.out.println(sc.getRemoteAddress() + " 离线了");
                key.cancel();
                sc.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void sendInfoToOtherClient(SocketChannel self, String msg) throws IOException {
//        String entryKey = null;
//        //转发给其他的客户端  第一种
//        for (Map.Entry<String, SocketChannel> entry : clients.entrySet()) {
//            if (self == entry.getValue()) {
//                entryKey = entry.getKey();
//            }
//        }
//        for (Map.Entry<String, SocketChannel> entry : clients.entrySet()) {
//
//            String prefix = entryKey;
//            if (prefix.equals(entry.getKey())) {
//                continue;
//            }
//            ByteBuffer writeBuffer = ByteBuffer.wrap(msg.getBytes("utf-8"));;
//            System.out.println(prefix + msg);
//            entry.getValue().write(writeBuffer);
//        }
        for (SelectionKey key : getSelector().keys()) {
            SelectableChannel channel = key.channel();
            if (channel instanceof SocketChannel && channel != self) {
                SocketChannel target = (SocketChannel) channel;
                System.out.println("转发给用户【" + target.getRemoteAddress() + "】" + msg);
                ByteBuffer writeBuffer = ByteBuffer.wrap(msg.getBytes("utf-8"));
                target.write(writeBuffer);
            }
        }
    }

    //连接事件
    private void accept(SelectionKey key) throws IOException {
        SocketChannel socketChannel = getListenChannel().accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(getSelector(), SelectionKey.OP_READ);
        System.out.println(socketChannel.getRemoteAddress() + " 上线了");
        String name = "【" + UUID.randomUUID().toString() + "】";
        clients.put(name, socketChannel);
    }

    public static void main(String[] args) throws IOException {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public ServerSocketChannel getListenChannel() {
        return listenChannel;
    }

    public void setListenChannel(ServerSocketChannel listenChannel) {
        this.listenChannel = listenChannel;
    }

    public int getPort() {
        return port;
    }
}
