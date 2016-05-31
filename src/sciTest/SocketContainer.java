package sciTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by den on 31/05/2016.
 */
public class SocketContainer {

    public OutputStream out;
    public InputStream in;
    public Socket socket;
    double d = 0;
    byte buffer[] = new byte[22];

    public void init() throws UnknownHostException, IOException {
        socket = new Socket("localhost", 3128);
        out = socket.getOutputStream();
        in = socket.getInputStream();
    }

    public void close() throws IOException {
        socket.close();

    }

    public SocketContainer() {
        try {
            init();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.err.println("HelloWorld constructed!");
    }

    public OutputStream getOut() {
        return out;
    }

    public InputStream getIn() {
        return in;
    }

    public void write(double var) throws IOException {

        Double d = var;
        String str = d.toString();
        //out.write(str.length());
        out.write(str.getBytes());
        out.flush();
    }

    public double get() throws IOException {
        return d;
    }

    public double read2() throws IOException {
        while (in.available() == 0) {
        }

        byte buffer[] = new byte[in.available()];
        in.read(buffer);
        d = Double.parseDouble(new String(buffer));
        return d;

    }


    public void run() {
        while (!socket.isClosed()) {
            try {
                if (in.available() > 0) {
                    byte buffer[] = new byte[in.available()];
                    in.read(buffer);
                    d = Double.parseDouble(new String(buffer));
                }
            } catch (IOException e) {
            }
        }
    }
}
