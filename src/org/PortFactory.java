package org;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Михаил on 24.05.2016.
 */
public abstract class PortFactory {

    static Executor e = Executors.newCachedThreadPool();
    static PortFactory echo = new PortFactory() {
        @Override
        public InPort getInport(Channel channel, Socket socket) {
            InPort inPort = new InPort(channel, socket);
            return inPort;
        }

        @Override
        public OutPort getOutport(Channel channel) {
            return new OutPort(channel, channel.inPort.socket);
        }
    };

    static PortFactory direct = new PortFactory() {
        @Override
        public InPort getInport(Channel channel, Socket socket) {

            InPort inPort = new InPort(channel, socket);
            return inPort;
        }

        @Override
        public OutPort getOutport(Channel channel) {
            Socket socket = null;
            try {
                socket = new Socket(channel.getOutputAddress(), channel.getOutPortNum());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return new OutPort(channel, socket);
        }
    };

    static PortFactory synch = new PortFactory() {
        @Override
        public InPort getInport(Channel channel, Socket socket) {
            return null;
        }

        @Override
        public OutPort getOutport(Channel channel) {
            return null;
        }
    };
    static PortFactory asynch = new PortFactory() {
        @Override
        public InPort getInport(Channel channel, Socket socket) {
            return null;
        }

        @Override
        public OutPort getOutport(Channel channel) {
            return null;
        }
    };

    private static Map<ChannelType, PortFactory> map = new HashMap<>();
    static {
        map.put(ChannelType.ASYNCHRONOUS, asynch);
        map.put(ChannelType.SYNCHRONOUS, synch);
        map.put(ChannelType.DIRECT, direct);
        map.put(ChannelType.ECHO,echo);
    }
    public static PortFactory getFactory(ChannelType channelType){
        return map.get(channelType);
    }

    public abstract InPort getInport(Channel channel, Socket socket);

    public abstract OutPort getOutport(Channel channel);
}
