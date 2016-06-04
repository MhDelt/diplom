function [res]=valueByRequest(t,u1)
    SOCKET_write(4, 'byrequest');
    res= SOCKET_read(4);
    res=strtod(res(1));
endfunction
