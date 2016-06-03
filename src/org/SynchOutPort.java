package org;

import java.io.OutputStream;

/**
 * Created by ������ on 24.05.2016.
 */
public class SynchOutPort extends OutPort {

    volatile byte data[] = new byte[Channel.BUFFER_SIZE]; //data
    volatile int countOfBytes; //real size of data
    public SynchOutPort(Channel channel, OutputStream outputStream) {
        super(channel, outputStream);
        e.execute(this);
    }


    @Override
    public void run() {
        while (isActive()) {
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
