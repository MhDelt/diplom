package org;

import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Михаил on 24.05.2016.
 */
public class InPort extends Port {
    Socket socket;

    InPort(Channel channel, Socket socket) {
        super(channel);
        this.socket = socket;
    }




    public void run() {

        try {
            InputStream input = socket.getInputStream();
            while (channel.isActive()) {
                channel.countOfBytes = input.read(channel.data);
                for (OutPort outPort: channel.outPort) {
                    outPort.write(channel.data, channel.countOfBytes);
                }
            }
            //input.close();
        } catch (Exception e) {
            System.out.print("init error: " + e);
        }

    }
}
