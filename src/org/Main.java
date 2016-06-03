package org;

/**
 * Created by ?????? on 13.03.2016.
 */

public class Main{

    public static final ChannelManager CHANNEL_MANAGER = new ChannelManager();

    public static void main(String[] args) {
        String adr = "localhost";
        createChannel(adr);
    }

    private static void createChannel(String adr) {
        int initChannelPort = 3128;
        int outPortNum = 3129;
        CHANNEL_MANAGER.addChannel(new Channel(adr, "echo", initChannelPort, outPortNum, adr, PortType.DIRECT_ECHO,20));
        CHANNEL_MANAGER.addChannel(new Channel(adr, "synch", 3130, 3131, adr, PortType.SYNCHRONOUS_ECHO, 20));
        CHANNEL_MANAGER.addChannel(new Channel(adr, "asynch", 3134, 3135, adr, PortType.ASYNCHRONOUS_ECHO, 20));
        CHANNEL_MANAGER.addChannel(new Channel(adr, "byrequest", 3136, 3137, adr, PortType.BY_REQUEST, 20));
    }
}