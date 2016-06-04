package org;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.Channel.BUFFER_SIZE;

/**
 * Created by den on 31/05/2016.
 */
public class ByRequestOutPort extends OutPort {


    private ServerSocket socket;

    byte[] buffer = new byte[256];

    private static ConcurrentHashMap<String, Pair<Channel, RequestListener>> channelMap = new ConcurrentHashMap<>();

    private static ByRequestOutPort instance = new ByRequestOutPort();
    public static ByRequestOutPort getPort(Channel channel) {
        Pair<Channel, RequestListener> prevPair = channelMap.put(channel.getName(), new Pair<>(channel, null));
        if (prevPair != null && prevPair.getValue() != null) {
            prevPair.getValue().setActive(false);
        }
        return instance;
    }

    private ByRequestOutPort() {
        super(null, null);
        try {
            this.socket = new ServerSocket(Channel.REQUEST_PORT);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        e.execute(this);
    }

    @Override
    public void write(byte[] data, int countOfBytes) {
        //super.write(data, countOfBytes);
    }

    @Override
    public void run() {

        while (isActive()) {
            try {
                Socket clientSocket = this.socket.accept();
                RequestListener requestListener = new RequestListener(clientSocket);
                e.execute(requestListener);

                System.out.println("New listener started");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class RequestListener implements Runnable {


        private boolean isActive = true;
        private InputStream in;
        private OutputStream out;

        public RequestListener(Socket clientSocket) {


            try {
                this.in = clientSocket.getInputStream();
                this.out = clientSocket.getOutputStream();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        public void setSocket(Socket clientSocket) {
            try {
                this.in = clientSocket.getInputStream();
                this.out = clientSocket.getOutputStream();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


        @Override
        public void run() {
            byte data[] = new byte[BUFFER_SIZE];
            Channel channel = null;
            while (isActive) {
                try {
                    if (in.available() > 0) {
                        int count = in.read(buffer, 0, in.available());
                        String str = new String(buffer, 0, count);
                        str = str.replace("\r", "").replace("\n", "");
                        System.out.println("Request channel " + str);
                        Pair<Channel, RequestListener> listenerPair = channelMap.get(str);
                        if (listenerPair.getValue() == null) {
                            channelMap.put(listenerPair.getKey().getName(), new Pair<>(listenerPair.getKey(), this));
                        }
                        Channel channelFromMap = listenerPair.getKey();
                        channel = channelFromMap;

                        ReentrantReadWriteLock.ReadLock readLock = channelFromMap.getReadWriteLock().readLock();
                        readLock.lock();
                        int countOfBytes;
                        try {
                            countOfBytes = channelFromMap.countOfBytes;
                            System.arraycopy(channelFromMap.data, 0, data, 0, countOfBytes);
                        } finally {
                            readLock.unlock();
                        }
                        System.out.println("read for channel" + channel.getName() + " data " + new String(data,0, countOfBytes));
                        out.write(data, 0, countOfBytes);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }
    }
}
