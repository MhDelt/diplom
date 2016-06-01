package org;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by ������ on 24.05.2016.
 */
public class OutPort extends Port {

    protected volatile boolean isActive = true;
    private final Socket socket;
    private final OutputStream output;

    public OutPort(Channel channel, Socket socket) {
        super(channel);
        this.socket = socket;
        OutputStream temp = null;
        try {
             temp = (socket != null)?this.socket.getOutputStream(): null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        output = temp;
    }



    public void write(byte[] data, int countOfBytes) {
        try {
            output.write(data, 0, countOfBytes);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}