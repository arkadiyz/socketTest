package com.example.vadnu.gpsapp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.util.Enumeration;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by vadnu on 5/10/2019.
 */

public class MessageSender extends AsyncTask<String,Void,Void> {
    private Socket mSocket;
    private PrintWriter pw;
    private static final int NORMAL_CLOSURE_STAUS =1000 ;
    private TextView outputFromSocket;
    private OkHttpClient client;
    Activity activity;
    Request request;
    WebSocket ws;
    private final class EchoWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("Helo its Vadim");
            webSocket.send("Whats up man");
            webSocket.close(NORMAL_CLOSURE_STAUS,"GOODBYE");
        }


        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output("Receiving: "+text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("Receiving: "+bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STAUS,null);
            output("Closing :"+code +" / "+reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error: "+t.getMessage());
        }


    }

    private void start() {
        if(ws==null){
//            request = new Request.Builder().url("ws://192.168.137.1:9000/").build();
//            request = new Request.Builder().url("ws://10.10.10.176:80/").build();
            request = new Request.Builder().url("wss://echo.websocket.org").build();
            EchoWebSocketListener listener = new EchoWebSocketListener();
            ws = client.newWebSocket(request, listener);
        }

        ws.send("dfsdfdss");
        client.dispatcher().executorService().shutdown();
    }

    public MessageSender(TextView outputFromSo,Activity activity){
        outputFromSocket=outputFromSo;
        client = new OkHttpClient();
        this.activity=activity;
    }
    @Override
    protected Void doInBackground(String... strings) {

        try {
            start();

//            String message=strings[0];
//            if(mSocket==null){
//                mSocket=new Socket("192.168.1.17",9000);
//                mSocket.setKeepAlive(true);
//            }
//
//
//
//            pw=new PrintWriter(mSocket.getOutputStream());
//            pw.write(message);
//            pw.flush();
//
//
//
////            pw.close();
//
//
//            InputStreamReader in=new InputStreamReader(mSocket.getInputStream());
//            BufferedReader bufferedReader=new BufferedReader(in);
//            String str=bufferedReader.readLine();
//            if(str!=null){
//                System.out.println("Server sent :"+str);
//            }
////            mSocket.close();
////            mSocket.getInputStream();



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendMessage(String message){
        try {
            Thread thread=new Thread();
            thread.run();
            pw=new PrintWriter(mSocket.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void output(final String txt) {
        this.activity.runOnUiThread( new Runnable() {
            @Override
            public void run() {
                outputFromSocket.setText(outputFromSocket.getText().toString() + "\n\n" + txt);
            }
        });
       ;
    }


}
