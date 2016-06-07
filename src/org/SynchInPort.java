package org;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by ������ on 24.05.2016.
 */
public class SynchInPort extends InPort {
    volatile byte data[] = new byte[Channel.BUFFER_SIZE]; //data
    volatile int countOfBytes; //real size of data


    public SynchInPort(Channel channel, Socket socket) {
        super(channel, socket);
    }

    public void run() {

        try {
            InputStream input = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(reader);
            //Scanner scanner = new Scanner(input );
            while (isActive()) {


                countOfBytes = readData(data, input);
//                String value = scanner.nextLine();// + "\r\n\r\n";
                System.out.println(channel.getName() + " " + new String(data, 0, countOfBytes));
                channel.getLock().lock();
                try {
                    channel.countOfBytes = this.countOfBytes;
                    System.arraycopy(data, 0, channel.data, 0, this.countOfBytes);
                    channel.getCondtion().signalAll();
                } finally {
                    channel.getLock().unlock();
                }

//                String value = br.readLine();
////                String value = scanner.nextLine();// + "\r\n\r\n";
//                if (!value.isEmpty()) {
//                    //todo remake concatanations and getBytes, by manual parse the stream
//                    value = value + "\r\n";
//                    System.out.println(channel.getName() + " " + value);
//                    channel.getLock().lock();
//                    try {
//                        byte[] strBytes = value.getBytes();
//                        channel.countOfBytes = strBytes.length;
//                        System.arraycopy(strBytes, 0, channel.data, 0, strBytes.length);
//                        channel.getCondtion().signalAll();
//                    } finally {
//                        channel.getLock().unlock();
//                    }
//                }
        }
        input.close();
    }

    catch(
    Exception e
    )

    {
        System.out.print("init error: " + e);
    }

}


}

