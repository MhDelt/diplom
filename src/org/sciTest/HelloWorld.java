import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

public class HelloWorld extends Thread{
    
    OutputStream out;
    InputStream in;
    Socket socket;
    double d =0;
    
    public void init() throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", 7777);
        out  = socket.getOutputStream();
        in = socket.getInputStream();
    }

   public HelloWorld() {
       try {
            init();
       } catch (IOException e) {
                 System.out.println(e.getMessage());
       }
      System.err.println("HelloWorld constructed!");
  }

   public OutputStream getOut() {
       return out;
   }
   public InputStream getIn(){
       return in;
   }
  
   public void write(double var) throws IOException {
        
        Double d = var;
        
        out.write(d.toString().getBytes());
        out.flush();
    }
    
    public double get() throws IOException {
        return d;
    }
    
    public double read() throws IOException {
        byte buffer[] = new byte[in.available()];
        in.read(buffer);
        d=Double.parseDouble(new String(buffer));
        return d;
    }
    
    
    public void run(){
        while(true) {
            try {
                if (in.available()>0) {
                    byte buffer[] = new byte[in.available()];
                    in.read(buffer);
                    d=Double.parseDouble(new String(buffer));
                }
            } catch (IOException e) {
            }
        }
    }
}
