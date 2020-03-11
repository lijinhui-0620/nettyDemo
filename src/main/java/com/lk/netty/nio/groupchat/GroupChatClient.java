package com.lk.netty.nio.groupchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auther: kim
 * @create: 2020-02-22 11:06
 * @description: 群聊客户端
 */
public class GroupChatClient {

    private String host = "127.0.0.1";
    private int port = 8888;
    private SocketChannel client;
    private Selector selector;
    private String username;
    private String charset = "utf-8";

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public GroupChatClient() throws IOException {

        selector = Selector.open();
        client = SocketChannel.open(new InetSocketAddress(getHost(), getPort()));
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        username = client.getLocalAddress().toString().substring(1);
        System.out.println(username + " is  ok");
    }

    public void sendInfo(String info) {
        info = getUsername() + " 说：" + info;

        try {
            getClient().write(ByteBuffer.wrap(info.getBytes(getCharset())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取
    public void readInfo() {
        try {
            Selector selector = getSelector();
            selector.select();
            System.out.println("获取到消息");
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isValid() && selectionKey.isReadable()) {
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    sc.read(buffer);
                    String msg = new String(buffer.array());
                    System.out.println(msg.trim());
                }
                iterator.remove();
            }

        } catch (IOException e) {
            e.printStackTrace();
            //TODO 服务器断开 重连 OR
            try {
                getClient().close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return host;
    }

    public void setHort(String hort) {
        this.host = hort;
    }

    public int getPort() {
        return port;
    }

    public void setPost(int post) {
        this.port = post;
    }

    public SocketChannel getClient() {
        return client;
    }

    public void setClient(SocketChannel client) {
        this.client = client;
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient chat = new GroupChatClient();

        new Thread(() -> {
            while (true) {
                chat.readInfo();
            }
        }).start();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            InputStreamReader input = null;
            try {
                input = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(input);
                while (true) {
                    String info = br.readLine();
                    chat.sendInfo(info);
                }
            } catch (Exception e) {
                System.out.println("获取标准输入失败: " + e.getMessage());
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
