function [res]=sendBySocket(x, t)
    if (!exist(vb_one) then
        SOCKET_open(1,"localhost",7777);
        vb_one="1"
    end
      SOCKET_write(1, string(1));
    res= socket_read(1);
    res=strtod(res(1));
endfunction
