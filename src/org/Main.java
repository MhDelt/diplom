package org;


import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Михаил on 13.03.2016.
 */

public class Main{

    static Executor e = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        String adr = "localhost";
        ServerSocket srv;
        try {
            int i = 0;
            srv = new ServerSocket(3128, 0, InetAddress.getByName(adr));

            System.out.print("Server started\n");

                while (true) {
                e.execute(new Server(i,srv.accept(),1));
                //System.out.print(i+"\n");
                i++;
            }
        }catch (Exception e) {
            System.out.print("Failed: "+e);
        }
    }
}
