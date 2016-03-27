package org;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Михаил on 22.03.2016.
 */
public class Server extends Thread{

    Socket socket;
    int number;
    byte data[]; //data
    int countOfBytes; //real size of data

    public void run() {
        try {
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
        }
    }


        Server(int num, Socket socket, int bufferSizeInKb){
            //Copy data
            this.socket = socket;
            this.number = num;
            this.data = new byte[1024*bufferSizeInKb];

            //start new thread
            setDaemon(true);
            setPriority(NORM_PRIORITY);
            start();
    }

    public void recieveData() {
        try {
            InputStream input = socket.getInputStream();
            countOfBytes = input.read(data);

        } catch (Exception e) {
            System.out.print("Recieving data error: "+e);
        }
    }

    public void sendData() {
        try {
            OutputStream output = socket.getOutputStream();

            output.write(data);
            output.flush(); // заставляем поток закончить передачу данных.
        }catch (Exception e) {
            System.out.print("Sending data error: "+e);
        }
    }

}
