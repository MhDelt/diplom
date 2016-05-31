package sciTest;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by den on 31/05/2016.
 */
public class SocketContainer {
    public static Socket socket = null;
    public static boolean init() {
        try {
            socket = new Socket("localhost", 3128);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
