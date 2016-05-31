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
        Channel c = new Channel(adr,"ch1",3128,3129,adr, PortType.ECHO,20);
        c.run();
    }
}