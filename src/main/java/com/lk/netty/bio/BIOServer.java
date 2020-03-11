package com.lk.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @auther: kim
 * @create: 2020-02-17 11:06
 * @description:
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {
        //使用线程池
        //1.创建线程池
        //2.收到链接后，就创建一个线程与之交互

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 100, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());

        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务启动了，监听端口【6666】");
        while (true) {
            final Socket accept = serverSocket.accept();
            System.out.println("接收到客户端链接");
            threadPoolExecutor.execute(new Runnable() {
                public void run() {

                }
            });
        }
    }

    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭client连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
