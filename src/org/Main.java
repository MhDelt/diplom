package org;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ?????? on 13.03.2016.
 */

public class Main{

    static Executor e = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        String adr = "localhost";
        createChannel(adr);
    }

    private static void createChannel(String adr) {
        int initChannelPort = 3128;
        int outPortNum = 3129;
        Channel c = new Channel(adr,"ch1", initChannelPort, outPortNum, adr, PortType.ECHO,20);
        c.run();
    }
}