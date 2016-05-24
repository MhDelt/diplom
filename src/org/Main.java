package org;


import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ?????? on 13.03.2016.
 */

public class Main{

    private volatile ArrayList<Byte> variables;

    static Executor e = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        String adr = "localhost";
        createChannel(adr);
    }

    private static void createChannel(String adr) {

    }
}