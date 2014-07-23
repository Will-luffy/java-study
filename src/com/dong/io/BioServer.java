package com.dong.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dongshuwang on 14-7-21.
 */
public class BioServer extends Thread {

    private int port = 8888;

    private static int sequence = 0;

    public BioServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            ServerSocket ss = new ServerSocket(this.port);
            while(true) {
                socket = ss.accept();
                this.handleMessage(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        int receiveBytes;
        byte[] receiveBuffer = new byte[128];
        String msg = "";
        if((receiveBytes = in.read(receiveBuffer)) != -1) {
            msg = new String(receiveBuffer, 0, receiveBytes);
            String response = "welcome, u r client-"+ (++sequence);
            out.write(response.getBytes());
        }
        out.flush();
        System.out.println("receives msg: " + msg);
    }

    public static void main(String[] args) {
        BioServer server = new BioServer(9088);
        server.start();
    }
}
