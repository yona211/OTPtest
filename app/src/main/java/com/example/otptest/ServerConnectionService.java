package com.example.otptest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Random;

public class ServerConnectionService extends Service {

    Socket s;
    DataInputStream dis;
    InputStreamReader disR;
    DataOutputStream dos;
    String msgToSend = "";
    String serverReturns;

    boolean isRun;
    int lifeTime;

    private final IBinder binder = new LocalBinder();
    public class LocalBinder extends Binder {
        ServerConnectionService getService() {
            return ServerConnectionService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    class connectSocket implements Runnable {
        @Override
        public void run() {
            SocketAddress socketAddress = new InetSocketAddress("192.168.1.12", 5556);
            try {
                s.connect(socketAddress);
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(msgToSend);
                dis = new DataInputStream(s.getInputStream());
                disR = new InputStreamReader(dis);
                BufferedReader br = new BufferedReader(disR);
                serverReturns = new String(br.readLine());

                dis.close();
                s.close();

            } catch (IOException e) {
                Log.d("ServerConn", "catch run: " + e.getMessage());
            }
        }
    }

    /*class connectSocketPicture implements Runnable {

        @Override
        public void run() {
            Log.d("PICTURE", "5");
            String response = "s";
            String filename = "test1111.jpg";
            System.out.println("Requested File: " + filename);
            SocketAddress socketAddress = new InetSocketAddress("192.168.1.12", 5556);
            try {
                s.connect(socketAddress);
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(msgToSend);
                disR = new InputStreamReader(dis);
                dis = new DataInputStream(s.getInputStream());

                FileInputStream fis;
                BufferedInputStream bis;
                BufferedOutputStream out;
                byte[] buffer = new byte[8192];
                try {
                    out = new BufferedOutputStream(s.getOutputStream());
                    int count;
                    while ((count = bis.read(buffer)) > 0) {
                        out.write(buffer, 0, count);

                    }
                    out.close();
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dis.close();
                s.close();

            } catch (IOException e) {
                Log.d("ServerConn", "catch run: " + e.getMessage());
            }
        }
    }*/

    /**
     * This function is the actual way we can talk with the server.
     * It's make an object of the connectSocket class witch is a class that make the socket level connection
     * with the server, and send the 'msgToSend' and save what the server return's.
     * After that we wait 1S till the function return the 'serverReturns'.
     *
     *
     * @param msgToSend is a String that the function get with what do tou want to get from the server.
     * @return 'serverReturns', this is a string with all the information we want our server to send us.
     */
    public String getServerReturns(String msgToSend) {
        this.msgToSend = msgToSend;
        isRun = true;
        lifeTime = 2;
        s = new Socket();
        Runnable connect = new connectSocket();
        new Thread(connect).start();
        while (isRun && lifeTime > 0)
        {
            try {
                Thread.sleep(1000);
                lifeTime--;
            } catch (InterruptedException e) {
                Log.d("ServerConn", "catch while: " + e.getMessage());
            }
        }
        stopSelf();
        if (serverReturns != null) {
            return serverReturns;
        }
        return "Server return's nothing";
    }

    /**
     * Destroying the server sockets connection and the service.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if(s != null) {
                // TODO Check for better destroy options
                s.close();
                dis.close();
                Log.d("ServerConn", "onDestroy");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d("ServerConn", "catch destroy: " + e.getMessage());
        }
        s = null;
        isRun = false;

    }
}