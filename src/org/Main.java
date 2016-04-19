package org;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Михаил on 13.03.2016.
 */

public class Main extends JFrame{

    private static boolean isStarted = false;
    JButton startStop;
    JLabel label;


    public Main() {
        super("Virtual Wire Tool");
        setSize(800, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel(){
            {
                setLocation(240,20);
                setSize(50,50);
                setText("Down");
                setVisible(true);
            }
        };

        startStop = new JButton("Start") {
            {
                setSize(200, 50);
                setLocation(20, 20);
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        isStarted = !isStarted;
                        if (isStarted) {
                            label.setText("UP");
                            System.out.print("Server started\n");
                        }
                        else {
                            label.setText("Down");
                            System.out.print("Server stopped\n");
                        }
                    }
                });

            }
        };

        add(label);
        add(startStop);
    }

    static Executor e = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        Main m = new Main();
        m.setVisible(true);
        String adr = "localhost";
        ServerSocket srv;
        try {
            int i = 0;
            srv = new ServerSocket(3128, 0, InetAddress.getByName(adr));

                while (true) {
                    if (isStarted) {
                        e.execute(new Server(i, srv.accept(), 1));
                        //System.out.print(i+"\n");
                        i++;
                    }
            }
        }catch (Exception e) {
            System.out.print("Failed: "+e);
        }
    }
}
