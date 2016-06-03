package org;

import java.io.InputStream;
import java.net.Socket;

/**
 * Created by ������ on 24.05.2016.
 */
public class SynchInPort extends InPort {
    volatile byte data[] = new byte[Channel.BUFFER_SIZE]; //data
    volatile int countOfBytes; //real size of data
    public SynchInPort(Channel channel, Socket socket) {
        super(channel, socket);
    }

    public void run() {

        try {
            InputStream input = socket.getInputStream();
            while (isActive()) {

                countOfBytes = input.read(data);
                if (countOfBytes > 0) {
                    channel.getLock().lock();
                    try {
                        channel.countOfBytes = countOfBytes;
                        System.arraycopy(data, 0, channel.data, 0, countOfBytes);
                        channel.getCondtion().signalAll();
                    } finally {
                        channel.getLock().unlock();
                    }
                }
            }
            //input.close();
        } catch (Exception e) {
            System.out.print("init error: " + e);
        }

    }
}

