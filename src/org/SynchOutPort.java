package org;

import java.net.Socket;

/**
 * Created by ������ on 24.05.2016.
 */
public class SynchOutPort extends OutPort {

    volatile byte data[] = new byte[Channel.BUFFER_SIZE]; //data
    volatile int countOfBytes; //real size of data
    public SynchOutPort(Channel channel, Socket socket) {
        super(channel, socket);
        e.execute(this);
    }


    @Override
    public void run() {
        while (channel.isActive()) {
            channel.getLock().lock();
            try {
                channel.getCondtion().await();
                countOfBytes = channel.countOfBytes;
                System.arraycopy(channel.data, 0, data, 0, countOfBytes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                channel.getLock().unlock();
            }
            write(data, countOfBytes);
        }
    }


}
