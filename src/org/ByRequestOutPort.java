package org;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.Channel.BUFFER_SIZE;

/**
 * Created by den on 31/05/2016.
 */
public class ByRequestOutPort extends OutPort {

    private InputStream inputStream;
    private ServerSocket socket;

    byte[] buffer = new byte[256];

    private static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    private static ByRequestOutPort instance = new ByRequestOutPort();
    public static ByRequestOutPort getPort(Channel channel) {

        Socket socket = null;
        OutputStream outputStream = null;
        try {
            socket = new Socket(channel.getOutputAddress(), channel.getOutPortNum());
            outputStream = socket.getOutputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        channelMap.put(channel.getName(), channel );
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
        super.write(data, countOfBytes);
    }

    @Override
    public void run() {

        while (isActive()) {
            try {
                Socket clientSocket = this.socket.accept();
                RequestListener requestListener = new RequestListener(clientSocket);
                e.execute(requestListener);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class RequestListener implements Runnable {


        private final Socket socket;
        private InputStream in;
        private OutputStream out;

        public RequestListener(Socket clientSocket) {
            this.socket = clientSocket;

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
            while (channel.isActive()) {
                try {
                    if (in.available() > 0) {
                        int count = in.read(buffer, 0, inputStream.available());
                        String str = new String(buffer, 0, count);
                        str = str.replace("\r","").replace("\n", "");
                        Channel channel = channelMap.get(str);
                        ReentrantReadWriteLock.ReadLock readLock = channel.getReadWriteLock().readLock();
                        readLock.lock();
                        int countOfBytes;
                        try {
                            countOfBytes = channel.countOfBytes;
                            System.arraycopy(channel.data, 0, data, 0, countOfBytes);
                        } finally {
                            readLock.unlock();
                        }
                        out.write(data, 0, countOfBytes);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
