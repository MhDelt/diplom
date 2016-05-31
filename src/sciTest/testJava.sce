//jc=mgetl(fullfile('C:\Users\demo3\Documents\sci_works\', 'HelloWorld.java'));
jc=mgetl(fullfile('', 'HelloWorld.java'));
HelloWorld=jcompile("HelloWorld",jc);

obj = HelloWorld.new();

obj.init()
