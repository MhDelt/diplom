function [res]=sendViaJava(x, t)
    sciBuffer = t;
    sciBuffer.write(x);
    res=sciBuffer.read();
endfunction
