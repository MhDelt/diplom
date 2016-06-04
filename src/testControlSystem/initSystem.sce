//CHANNEL_MANAGER.addChannel(new Channel(adr, "control", 3140, 3141, adr, PortType.BY_REQUEST, 20));
//CHANNEL_MANAGER.addChannel(new Channel(adr, "feedback", 3142, 3143, adr, PortType.BY_REQUEST, 20));
handleControlOut = 4;
handleFeedbackOut = 5;
handleRequest = 6;
channelFeedback = 'feedback';
channelControl = 'control';
exec('writeValue.sce')
exec('valueByRequest.sce')
SOCKET_open(handleControlOut, "localhost", 3140);
SOCKET_open(handleFeedbackOut, "localhost", 3142);
SOCKET_open(handleRequest, "localhost", 3100);


writeValue(handleControlOut, 1, 11)
valueByRequest(handleRequest, channelControl, 0, 0)
writeValue(handleFeedbackOut, 1, 12)
valueByRequest(handleRequest, channelFeedback, 0, 0)