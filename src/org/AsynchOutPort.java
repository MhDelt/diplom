package org;

import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by ������ on 24.05.2016.
 */
public class AsynchOutPort extends OutPort {


    volatile byte data[] = new byte[Channel.BUFFER_SIZE]; //data
    volatile int countOfBytes; //real size of data

    public AsynchOutPort(Channel channel, OutputStream outputStream) {
        super(channel, outputStream);
        e.execute(this);
    }

    @Override
    public void run() {
        while (isActive()) {
            ReentrantReadWriteLock.ReadLock readLock = channel.getReadWriteLock().readLock();
            readLock.lock();
            try {

                countOfBytes = channel.countOfBytes;
                System.arraycopy(channel.data, 0, data, 0, countOfBytes);

            } finally {
                readLock.unlock();
            }
            write(data, countOfBytes);
        }
    }


}
