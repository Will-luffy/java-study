package com.dong.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by dongshuwang on 14-7-25.
 */
public class AioClient {

    private AsynchronousSocketChannel client;

    public AioClient(String host, int port) throws IOException, InterruptedException, ExecutionException {
        this.client = AsynchronousSocketChannel.open();
        Future<?> future = client.connect(new InetSocketAddress(host, port));
        future.get();
    }

    public void write(byte b) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        byteBuffer.put(b);
        byteBuffer.flip();
        client.write(byteBuffer);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        AioClient client = new AioClient("localhost", 8899);
        client.write((byte)1);
    }
}
