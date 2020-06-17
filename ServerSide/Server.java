package ServerSide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PST91
 */
public class Server extends Thread {

    private int PORT;
    private ServerSocket sSocket;
    private List<User> userList;
    private Queue<QueueMessage> inbox;
    private int numberOfUser = 0;
    private List<Thread> multiThread;
    private String[] animal ;
    

    public Server() {
    }

    public Server(int PORT) {
        this.PORT = PORT;
        try {
            sSocket = new ServerSocket(PORT);
            userList = new ArrayList<>();
            inbox = new LinkedList<>();
            multiThread = new ArrayList<>();
            System.out.println("Waiting for user connect........");
        } catch (IOException ex) {
            System.err.println("<!> Fail to start server!!!!");
        }
    }

    public void addAndRun(User user) {
        multiThread.add(new Thread() {
            @Override
            public void run() {
                while (true) {
                    String mgs = user.recieve();

                    while (mgs != null) {
                        inbox.offer(new QueueMessage(user.getuID(), mgs));
                        mgs = user.recieve();
                    }
                    try {
                        sleep(50);
                    } catch (InterruptedException ex) {
                        System.err.println("Your server is already run!!!!");
                    }
                }

            }

        });
        
        multiThread.get(multiThread.size()-1).start();
    }

    @Override
    public void run() {
        Thread accept = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket listener = sSocket.accept();

                        User nUser = new User(numberOfUser++, listener);

                        userList.add(nUser);
                        
                        addAndRun(nUser);

                        System.out.println(AnsiCode.ANSI_GREEN + listener.getInetAddress() + "connected" + AnsiCode.ANSI_RESET);

                        nUser.send(AnsiCode.ANSI_GREEN + "Welcome "+nUser.getNickname()+" to chat system!!!" + AnsiCode.ANSI_RESET);

                    } catch (IOException ex) {
                        System.err.println("Fail to connect!!!");
                    }
                }
            }

        };


        Thread send = new Thread() {
            @Override
            public void run() {
                while (true) {
                    QueueMessage qMgs = inbox.poll();

                    if (qMgs != null) {
                        System.out.println(qMgs.getMgs());
                        for (User user : userList) {
                            if(user.getuID() != qMgs.getuID()){
                                user.send(AnsiCode.ANSI_GREEN + userList.get(qMgs.getuID()).getNickname()+AnsiCode.ANSI_RESET+": "+qMgs.getMgs());
                            }
                        }
                    }
                    try {
                        sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        };

        accept.start();
        send.start();

    }

    public static void main(String[] args) throws IOException {
        Server sv = new Server(4001);

        sv.start();
    }
}
