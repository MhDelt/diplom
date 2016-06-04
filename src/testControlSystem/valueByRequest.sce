function [res]=valueByRequest(handle, chanelName, t,u1)
    SOCKET_write(handle, chanelName);
    res= SOCKET_read(handle);
    res=strtod(res(1));
endfunction
