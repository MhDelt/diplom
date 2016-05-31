package org;

import java.io.InputStream;
import java.net.Socket;

/**
 * Created by ������ on 24.05.2016.
 */
public class InPort extends Port {
    Socket socket;
    boolean isActive = true;

    InPort(Channel channel, Socket socket) {
        super(channel);
        this.socket = socket;
        e.execute(this);
    }

    public void run() {

        try {
            InputStream input = socket.getInputStream();
            while (this.isActive) {
                channel.countOfBytes = input.read(channel.data);
                if(channel.countOfBytes >0) {
                    System.out.println(channel.data[0]);
                    for (OutPort outPort : channel.outPort) {
                        outPort.write(channel.data, channel.countOfBytes);
                    }
                }
            }
            //input.close();
        } catch (Exception e) {
            System.out.print("init error: " + e);
        }

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
