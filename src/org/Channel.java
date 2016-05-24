package org;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Михаил on 24.05.2016.
 */
public class Channel implements Runnable {


    public static final int BUFFER_SIZE = 1024;
    private final int inPortNum;
    private final int outPortNum;
    private final String outputAddress;
    private final ChannelType channelType;
    InPort inPort;
    List<OutPort> outPort = new ArrayList<>();
    private ServerSocket srv;
    private String name;

    volatile byte data[] = new byte[BUFFER_SIZE]; //data
    volatile int countOfBytes; //real size of data
    private boolean active = true;
    private ReentrantReadWriteLock readWriteLock;
    private ReentrantLock lock;
    private Condition condtion;

    public Channel(String virtBusAddress, String name, int inPortNum, int outPortNum, String outputAddres, ChannelType channelType, Integer rate) {
        this.name = name;
        this.outPortNum = outPortNum;
        this.inPortNum = inPortNum;
        this.outputAddress = outputAddres;
        this.channelType = channelType;
        try {

            srv = new ServerSocket(this.inPortNum, 0, InetAddress.getByName(virtBusAddress));
            System.out.print("Server started\n");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
    @Override
    public void run() {
        try {
            int i = 0;

            while (isActive()) {
                Socket socket = srv.accept();
                PortFactory factory = PortFactory.getFactory(channelType);
                inPort = factory.getInport(this, socket);
                if (outPort.isEmpty()) {
                    outPort.add(factory.getOutport(this));
                }

                System.out.print(i + "\n");
                i++;
            }
        }catch (Exception e) {
            System.out.print("Failed: " + e);
        }
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOutputAddress() {
        return outputAddress;
    }

    public int getOutPortNum() {
        return outPortNum;
    }

    public ReentrantReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    public void setReadWriteLock(ReentrantReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
    }


    public ReentrantLock getLock() {
        return lock;
    }

    public void setLock(ReentrantLock lock) {
        this.lock = lock;
    }


    public Condition getCondtion() {
        return condtion;
    }

    public void setCondtion(Condition condtion) {
        this.condtion = condtion;
    }
}
