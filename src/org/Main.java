package org;


import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by Михаил on 13.03.2016.
 */

public class Main{

    public static void main(String[] args) {
        String adr = "localhost";
        ServerSocket srv;
        try {
            int i = 0;
            srv = new ServerSocket(3128, 0, InetAddress.getByName(adr));

            System.out.print("Server started\n");

                while (true) {
                new Server(i,srv.accept(),64);
                //System.out.print(i+"\n");
                i++;
            }
        }catch (Exception e) {
            System.out.print("Failed: "+e);
        }
    }
}
