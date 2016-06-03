exec('echoBySocket.sce')
//CHANNEL_MANAGER.addChannel(new Channel(adr,"echo", initChannelPort, outPortNum, adr, PortType.DIRECT_ECHO,20));
SOCKET_open(1,"localhost",3128);


//CHANNEL_MANAGER.addChannel(new Channel(adr, "synch", 3130, 3131, adr, PortType.SYNCHRONOUS_ECHO, 20));
SOCKET_open(2,"localhost",3130);


//        CHANNEL_MANAGER.addChannel(new Channel(adr, "asynch", 3134, 3135, adr, PortType.ASYNCHRONOUS_ECHO, 20));
SOCKET_open(4,"localhost",3134);


//        CHANNEL_MANAGER.addChannel(new Channel(adr, "byrequest", 3136, 3137, adr, PortType.BY_REQUEST, 20));
SOCKET_open(5,"localhost",3136);//for send
SOCKET_open(6,"localhost",3100);//for request





