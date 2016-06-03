package org;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ������ on 24.05.2016.
 */
public abstract class PortFactory {

    static Executor e = Executors.newCachedThreadPool();

    private static OutputStream getOutputStreamForSameSocket(Channel channel)  {
        try {
            return channel.inPort.socket.getOutputStream();
        } catch (IOException e1) {
            throw new IllegalStateException(e1);
        }
    }

    private static OutputStream getOutputSreamForExtSocket(Channel channel) {
        Socket socket;
        OutputStream outputStream = null;
        try {
            socket = new Socket(channel.getOutputAddress(), channel.getOutPortNum());
            outputStream = socket.getOutputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return outputStream;
    }

    private static Map<PortType, PortFactory> map = new HashMap<>();
    static {
        map.put(PortType.SYNCHRONOUS, new PortFactory() {
            @Override
            public InPort getInport(Channel channel, Socket socket) {
                return new SynchInPort(channel, socket);
            }

            @Override
            public OutPort getOutport(Channel channel) {
                return new SynchOutPort(channel, getOutputSreamForExtSocket(channel));
            }
        });
        map.put(PortType.ASYNCHRONOUS, new PortFactory() {
            @Override
            public InPort getInport(Channel channel, Socket socket) {
                return new AsynchInPort(channel, socket);
            }

            @Override
            public OutPort getOutport(Channel channel) {
                return new AsynchOutPort(channel, getOutputSreamForExtSocket(channel));
            }
        });
        map.put(PortType.DIRECT, new PortFactory() {
            @Override
            public InPort getInport(Channel channel, Socket socket) {
                return new InPort(channel, socket);
            }

            @Override
            public OutPort getOutport(Channel channel) {
                return new OutPort(channel, getOutputSreamForExtSocket(channel));
            }
        });
        map.put(PortType.BY_REQUEST, new PortFactory() {
            @Override
            public InPort getInport(Channel channel, Socket socket) {
                return new AsynchInPort(channel, socket);
            }

            @Override
            public OutPort getOutport(Channel channel) {
                return ByRequestOutPort.getPort(channel);
            }
        });
        map.put(PortType.ASYNCHRONOUS_ECHO, new PortFactory() {
            @Override
            public InPort getInport(Channel channel, Socket socket) {
                return new AsynchInPort(channel, socket);
            }

            @Override
            public OutPort getOutport(Channel channel) {
                return new AsynchOutPort(channel, getOutputStreamForSameSocket(channel));
            }
        });
        map.put(PortType.SYNCHRONOUS_ECHO, new PortFactory() {
            @Override
            public InPort getInport(Channel channel, Socket socket) {
                return new SynchInPort(channel, socket);
            }

            @Override
            public OutPort getOutport(Channel channel) {
                return new SynchOutPort(channel, getOutputStreamForSameSocket(channel));
            }
        });

        map.put(PortType.DIRECT_ECHO, new PortFactory() {
            @Override
            public InPort getInport(Channel channel, Socket socket) {
                return new InPort(channel, socket);
            }

            @Override
            public OutPort getOutport(Channel channel) {
                return new OutPort(channel, getOutputStreamForSameSocket(channel));
            }
        });
    }
    public static PortFactory getFactory(PortType channelType){
        return map.get(channelType);
    }

    public abstract InPort getInport(Channel channel, Socket socket);

    public abstract OutPort getOutport(Channel channel);
}
