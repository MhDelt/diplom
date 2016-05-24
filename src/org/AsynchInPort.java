package org;

import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Михаил on 24.05.2016.
 */
public class AsynchInPort extends InPort {
    volatile byte data[] = new byte[Channel.BUFFER_SIZE]; //data
    volatile int countOfBytes; //real size of data

    public AsynchInPort(Channel channel, Socket socket) {
        super(channel, socket);
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            while (channel.isActive()) {

                countOfBytes = input.read(data);
                ReentrantReadWriteLock.WriteLock writeLock = channel.getReadWriteLock().writeLock();
                writeLock.lock();
                try {
                    channel.countOfBytes = countOfBytes;
                    System.arraycopy(data, 0, channel.data,0, countOfBytes);
                } finally {
                    writeLock.unlock();
                }
            }
            //input.close();
        } catch (Exception e) {
            System.out.print("init error: " + e);
        }
    }
}
