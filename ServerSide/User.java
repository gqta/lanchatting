/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

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


public class User{
    private int uID;
    private Socket uSocket;
    private BufferedReader uIS;
    private BufferedWriter uOS;
    private String nickname;
    public static String[] animal = {"Rabit", "Tiger", "Lion", "Panda"};
    public static String[] adj = {"Quick", "Tall", "Thin", "Fat", "Big"};

    public User() {
    }

    public User(int uID, Socket uSocket) {
        try {
            this.uID = uID;
            this.uSocket = uSocket;
            this.uIS = new BufferedReader(new InputStreamReader(this.uSocket.getInputStream()));
            this.uOS = new BufferedWriter(new OutputStreamWriter(this.uSocket.getOutputStream()));
            this.nickname = adj[getRandom(adj.length)] +" "+ animal[getRandom(animal.length)];
        } catch (IOException ex) {
            System.err.println("Fail to create");
        }
    }

    public String getNickname() {
        return nickname;
    }
    
    
    
    private int getRandom(int a){
        return (int) Math.floor(Math.random() *a);
    }
    public int getuID() {
        return uID;
    }

    public BufferedReader getuIS() {
        return uIS;
    }

    public BufferedWriter getuOS() {
        return uOS;
    }

    public Socket getuSocket() {
        return uSocket;
    }
    
    public String recieve(){
        try {
            return uIS.readLine();
        } catch (IOException ex) {
            return null;
        }
    }
    
    
    public void send(String mgs){
        try {
            uOS.write(mgs);
            uOS.newLine();
            uOS.flush();
            
        } catch (IOException ex) {
            System.err.println("Fail to send!!!");
        }
    }
    
    
}
