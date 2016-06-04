function [res]=writeValue(t,u1)
    SOCKET_write(3, string(u1));
    res=0;
endfunction
