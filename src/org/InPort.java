package org;

import java.io.InputStream;
import java.net.Socket;

/**
 * Created by ������ on 24.05.2016.
 */
public class InPort extends Port {
    Socket socket;


    InPort(Channel channel, Socket socket) {
        super(channel);
        this.socket = socket;
        e.execute(this);
    }

    public void run() {

        try {
            InputStream input = socket.getInputStream();
            while (isActive()) {
                channel.countOfBytes = input.read(channel.data);
                if(channel.countOfBytes >0) {
                    System.out.println(new String(channel.data, 0, channel.countOfBytes));
                    for (OutPort outPort : channel.outPorts) {
                        outPort.write(channel.data, channel.countOfBytes);
                    }
                }
            }
            //input.close();
        } catch (Exception e) {
            System.out.print("init error: " + e);
        }

    }


}
