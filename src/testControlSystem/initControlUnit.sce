//CHANNEL_MANAGER.addChannel(new Channel(adr, "control", 3140, 3141, adr, PortType.BY_REQUEST, 20));
//CHANNEL_MANAGER.addChannel(new Channel(adr, "feedback", 3142, 3143, adr, PortType.BY_REQUEST, 20));
handleControlOut = 3;
handleRequest = 4;
channelFeedback = 'feedback';
exec('writeValue.sce')
exec('valueByRequest.sce')
SOCKET_open(handleControlOut, "localhost", 3140);
SOCKET_open(handleRequest, "localhost", 3100);