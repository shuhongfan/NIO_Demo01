package com.shf.mynio.buffer;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 */
public class BufferDemo02 {

    static private final int start = 0;
    static private final int size = 1024;

    /**
     * 内存映射文件IO
     */
    @Test
    @SneakyThrows
    public void b04() {
        RandomAccessFile accessFile = new RandomAccessFile("C:\\Users\\shuho\\MyData\\NIO_Demo01\\MyNIO\\src\\main\\resources\\application.properties", "rw");
        FileChannel fileChannel = accessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, size, size);

        mappedByteBuffer.put(0, (byte) 97);
        mappedByteBuffer.put(1023, (byte) 122);
        accessFile.close();
    }

    /**
     * 直接缓冲区
     */
    @Test
    @SneakyThrows
    public void b03() {
        String infile = "C:\\Users\\shuho\\MyData\\NIO_Demo01\\MyNIO\\src\\main\\resources\\application.properties";
        FileInputStream fis = new FileInputStream(infile);
        FileChannel fisChannel = fis.getChannel();

        String outfile = "C:\\Users\\shuho\\MyData\\NIO_Demo01\\MyNIO\\src\\main\\resources\\application.properties";
        FileInputStream fisOutPut = new FileInputStream(outfile);
        FileChannel fisOutPutChannel = fisOutPut.getChannel();

//        创建直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            buffer.clear();
            int r = fisChannel.read(buffer);
            if (r == -1) {
                break;
            }

            buffer.flip();
            fisOutPutChannel.write(buffer);
        }


    }

    /**
     * 只读缓冲区
     * 只读缓冲区非常简单，可以读取它们，但是不能向它们写入数据。可以通过调用缓冲区的asReadOnlyBuffer()方法，
     * 将任何常规缓冲区转换为只读缓冲区，这个方法返回一个与原缓冲区完全相同的缓冲区，并与原缓冲区共享数据，
     * 只不过它是只读的。如果原缓冲区的内容发生了变化，只读缓冲区的内容也随之发生变化:
     */
    @Test
    public void b02() {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

//        创建只读缓冲区
        ByteBuffer readonly = buffer.asReadOnlyBuffer();

        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b *= 10;
            buffer.put(i, b);
        }

        readonly.position(0);
        readonly.limit(buffer.capacity());

        while (readonly.remaining() > 0) {
            System.out.println(readonly.get());
        }
    }

    /**
     * 缓冲区分片 子缓冲区
     */
    @Test
    public void b01() {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

//        创建子缓冲区
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();

//        改变子缓冲区内容
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            b *= 10;
            slice.put(i, b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.remaining()>0) {
            System.out.println(buffer.get());
        }
    }
}
