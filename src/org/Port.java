package org;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ?????? on 22.03.2016.
 */
public abstract class Port implements Runnable{

    public static ExecutorService e = Executors.newCachedThreadPool();
    protected final Channel channel;

    protected boolean isActive = true;

    public void run() {
       /* try {
            //set Input stream from socket
            InputStream input = socket.getInputStream();
            //set Output stream from socket
            OutputStream output = socket.getOutputStream();

            //read from input to buffer. r = count of readed bytes.
            int r = input.read(data);

            //Make a string from buffer
            String outStr = new String(data, 0, r);
            System.out.print(outStr+"\n");
            //add some data

            //answer to client
            output.write(outStr.getBytes());
            output.flush();
            //close connection
            socket.close();
        } catch (Exception e) {
            System.out.print("init error: "+e);
        }*/
    }


    public Port(Channel channel){
        this.channel = channel;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}