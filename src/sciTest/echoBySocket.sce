function [res]=echoBySocket(t,u1)
    //if (!exist(vb_one)) then
    //    SOCKET_open(1,"localhost",3128);
    //    vb_one="1"
    //end
    SOCKET_write(1, string(u1));
    res= SOCKET_read(1);
    res=strtod(res(1));
endfunction
