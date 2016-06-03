function [res]=echoBySynchPort(t,u1)
    SOCKET_write(2, string(u1));
    res= SOCKET_read(2);
    res=strtod(res(1));
endfunction
