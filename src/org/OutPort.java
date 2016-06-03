package org;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by ������ on 24.05.2016.
 */
public class OutPort extends Port {


    private final OutputStream output;

    public OutPort(Channel channel, OutputStream outputStream) {
        super(channel);
        this.output = outputStream;
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