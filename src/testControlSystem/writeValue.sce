function [res]=writeValue(handle, t,u1)
    SOCKET_write(handle, string(u1));
    res=0;
endfunction
