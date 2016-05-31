function [res]=sendBySocket(t, u1)
    //if (!exist(vb_one)) then
    //    SOCKET_open(1,"localhost",3128);
    //    vb_one="1"
    //end
    SOCKET_write(1, '1');
    res= socket_read(1);
    res=strtod(res(1));
endfunction
