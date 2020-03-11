package com.lk.netty.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.time.Instant;
import java.util.Map;

/**
 * @auther: kim
 * @create: 2020-02-22 12:51
 * @description:
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();

        sc.connect(new InetSocketAddress("127.0.0.1", 8001));

        String filename = "nginx-1.17.8.tar.gz";
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        long startTime = Instant.now().toEpochMilli();
        //transferTo 方法 在windows平台 一次只能发送8M，大文件需要分段发送
        fileChannel.transferTo(0, fileChannel.size(), sc);
        System.out.println("发送的总字节数 =" + fileChannel.size() + "  耗时：" + (Instant.now().toEpochMilli() - startTime));
        sc.close();
    }
}
