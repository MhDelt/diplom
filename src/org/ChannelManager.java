package org;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by den on 03/06/2016.
 */
public class ChannelManager {

    List<Channel> channels = new ArrayList<>();
    static Executor e = Executors.newCachedThreadPool();

    public void addChannel(Channel channel){
        channels.add(channel);
        e.execute(channel);
    }

    public void shotDown(){
        channels.stream().forEach(channel-> channel.setActive(false));

    }
}
