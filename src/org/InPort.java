package org;

import java.io.IOException;
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
                channel.countOfBytes = readData(channel.data, input);
                    System.out.println(new String(channel.data, 0, channel.countOfBytes));
                    for (OutPort outPort : channel.outPorts) {
                        outPort.write(channel.data, channel.countOfBytes);
                    }

            }
            //input.close();
        } catch (Exception e) {
            System.out.print("init error: " + e);
        }

    }


    public static int readData(byte[] data, InputStream input) throws IOException {
        int index = 0;
        do {
            data[index] = (byte) input.read();
            if (data[index] == -1) {
                return 0;
            }
            if (data[index++] == (byte) '\r') {
                data[index++] = (byte) input.read();// \n
                //special behavior for scilab
                input.read();// \r
                input.read();// \n
                return index;
            }
        } while (true);
    }
}
