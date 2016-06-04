//CHANNEL_MANAGER.addChannel(new Channel(adr, "control", 3140, 3141, adr, PortType.BY_REQUEST, 20));
//CHANNEL_MANAGER.addChannel(new Channel(adr, "feedback", 3142, 3143, adr, PortType.BY_REQUEST, 20));
handleFeedbackOut = 5;
handleRequest = 6;
chanelControl = 'control';
exec('writeValue.sce')
exec('valueByRequest.sce')
SOCKET_open(handleFeedbackOut, "localhost", 3142);
SOCKET_open(handleRequest, "localhost", 3100);