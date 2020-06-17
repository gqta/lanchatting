/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PST91
 */
public class main {

    public static void main(String[] args) throws IOException {
        
        Socket uSocket = new Socket("localhost", 4001);
        
        BufferedReader is = new BufferedReader(new InputStreamReader(uSocket.getInputStream()));
        BufferedWriter os = new BufferedWriter(new OutputStreamWriter(uSocket.getOutputStream()));
        Scanner sc = new Scanner(System.in);
        
        
        
        
        Thread recieve = new Thread(){
            @Override
            public void run() {
                while(true){
                String line;
                    try {
                        line = is.readLine();
                        if(line != null) System.out.println(line);
                        sleep(500);
                    } catch (IOException ex) {
                        
                    } catch (InterruptedException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        
           };
        recieve.start();
        
        while(true){
            
            System.out.print(">");
            String send = sc.nextLine();
            os.write(send);
            os.newLine();
            os.flush();
            
            if(send.equalsIgnoreCase("quit")) break;
            
            
        }
        
        os.close();
        is.close();
        uSocket.close();
        
    }
}

