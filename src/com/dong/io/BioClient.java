package com.dong.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by dongshuwang on 14-7-21.
 */
public class BioClient {

    private String serverIp;
    private int port;

    public BioClient() {
    }

    public BioClient(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }
    public void send(byte[] data) {
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            socket = new Socket(this.serverIp, this.port);
            out = socket.getOutputStream();
            out.write(data);
            out.flush();

            in = socket.getInputStream();
            int receiveBytes = 0;
            byte[] buffer = new byte[128];
            if((receiveBytes = in.read(buffer)) != -1) {
                String msg = new String(buffer, 0, receiveBytes);
                System.out.println("receive msg: "+ msg);
            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int n = 1;
        StringBuffer data = new StringBuffer();
        Date start = new Date();
        for(int i = 0; i < n; i++) {
            data.delete(0, data.length());
            data.append("hello!");
            BioClient client = new BioClient("localhost", 9088);
            client.send(data.toString().getBytes());
        }
        Date end = new Date();
        long cost = end.getTime() - start.getTime();
        System.out.println(n + " requests cost " + cost + " ms.");
    }
}
