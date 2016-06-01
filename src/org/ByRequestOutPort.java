package org;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by den on 31/05/2016.
 */
public class ByRequestOutPort extends OutPort {

    private InputStream inputStream;
    private Socket socket;

    byte[] buffer = new byte[256];

    private static ConcurrentHashMap<String, Pair<Channel, SynchOutPort>> channelMap = new ConcurrentHashMap<>();

    private static ByRequestOutPort instance = new ByRequestOutPort();
    public static ByRequestOutPort getPort(Channel channel) {

        Socket socket = null;
        try {
            socket = new Socket(channel.getOutputAddress(), channel.getOutPortNum());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        channelMap.put(channel.getName(), new Pair<Channel, SynchOutPort>(channel, new SynchOutPort(channel, socket)));
        return instance;
    }

    private ByRequestOutPort() {
        super(null, null);
        try {
            this.socket = new Socket(channel.getOutputAddress(), Channel.REQUEST_PORT);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            this.inputStream = socket.getInputStream ();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        e.execute(this);
    }

    @Override
    public void write(byte[] data, int countOfBytes) {
        super.write(data, countOfBytes);
    }

    @Override
    public void run() {
        while (this.isActive && channel.isActive()) {
            try {
                if(inputStream.available() > 0) {
                    int count = inputStream.read(buffer, 0, inputStream.available());
                    String str = new String(buffer,0, count);
                    Pair<Channel, SynchOutPort> pair = channelMap.get(str);
                    channel.getLock().lock();
                    try{
                        channel.getCondtion().signalAll();
                    }finally {
                        channel.getLock().unlock();
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
