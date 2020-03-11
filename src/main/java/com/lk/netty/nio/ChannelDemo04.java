package com.lk.netty.nio;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @auther: kim
 * @create: 2020-02-20 10:01
 * @description:
 */
public class ChannelDemo04 {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("e:\\file01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(4, (byte) 'D');

        randomAccessFile.close();
    }
}
