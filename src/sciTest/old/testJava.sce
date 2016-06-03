//jc=mgetl(fullfile('C:\Users\demo3\Documents\sci_works\', 'HelloWorld.java'));
jc=mgetl(fullfile('', 'SocketContainer.java'));
SocketContainer=jcompile("SocketContainer",jc);

obj = SocketContainer.new()

//obj.init()
