package com.mycompany.ddnd;

import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastClient implements Runnable {
    static MulticastSocket socket;
    final static String command = "command;";
    final static String info = "info;";
    final static String echo = "echo;";
    //String username;
    //NewJFrame njframe;

    /*public MulticastClient(NewJFrame frame) {
        
        
    } */
    
    public void run() {
        try{
            System.out.println("Thread running.");
                while(true) {
                    DatagramPacket multiPacket;
                    InetAddress address = InetAddress.getByName("230.0.0.1");
                    socket = new MulticastSocket(4446);
                    socket.joinGroup(address);
                    byte[] buf = new byte[256];
                    multiPacket = new DatagramPacket(buf, buf.length);
                    System.out.println("Waiting for packet (thread).");
                    socket.receive(multiPacket);
                    System.out.println("Received packet (thread).");
                    String received = new String(multiPacket.getData(), 0, multiPacket.getLength());
                    //send received packet string to jframe
                    //NewJFrame frame = new NewJFrame();
                    //frame.outputLogListener(received);
                    System.out.println("BROADCAST: " + received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        DatagramPacket packet;
        InetAddress address = InetAddress.getByName("230.0.0.1");
        socket = new MulticastSocket(4446);
	socket.joinGroup(address);
        MulticastClient listener = new MulticastClient();
        Thread thread1 = new Thread(listener);
        thread1.start();
        String username;
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter your username:"); //get username from client and send to server
            username = in.nextLine() +";";
            String str = "username;"+username;
            byte[] cli = new byte[256];
            cli = str.getBytes();
            packet = new DatagramPacket(cli, cli.length, address, 4445);
            socket.send(packet);
            
            while(true) { //main input loop
                cli = new byte[256];
                String test = "";
                System.out.println("Enter a number for the corresponding option: 1-info / 2-command / 3-echo");
                String choice = in.nextLine();
                if (choice.equals("1")) {
                    
                } else if (choice.equals("2")) {

                } else if (choice.equals("3")) {
                    test = echo+"echotext";
                    cli = test.getBytes();
                    packet = new DatagramPacket(cli, cli.length, address, 4445);
                    socket.send(packet);
                } else {
                    System.out.println("Sorry, "+choice+" is not a valid option.");
                }

                /*test = username+in.nextLine();
                cli = test.getBytes();
                packet = new DatagramPacket(cli, cli.length, address, 4445);
                socket.send(packet);
                test = "";*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	socket.leaveGroup(address);
	socket.close();
    }
    
}