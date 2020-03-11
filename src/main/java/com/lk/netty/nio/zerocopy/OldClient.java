package com.lk.netty.nio.zerocopy;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.time.Instant;

/**
 * @auther: kim
 * @create: 2020-02-22 13:02
 * @description:
 */
public class OldClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(8001));


        String filename = "nginx-1.17.8.tar.gz";
        FileInputStream fileInputStream = new FileInputStream(filename);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;
        long startTime = Instant.now().toEpochMilli();
        while ((readCount = fileInputStream.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }

        System.out.println("发送的总字节数 =" + total + "  耗时：" + (Instant.now().toEpochMilli() - startTime));

        dataOutputStream.close();
        fileInputStream.close();
        socket.close();
    }
}
