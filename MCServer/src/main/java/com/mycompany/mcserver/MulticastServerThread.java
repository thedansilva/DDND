package com.mycompany.mcserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastServerThread extends Thread {
    MulticastSocket socket = null;
    BufferedReader in = null;
    InetAddress group;
    int type;
    ArrayList<Character> playersArray;
    
    public MulticastServerThread(int type) throws IOException {
        super("MulticastServerThread");
        this.type = type;
        socket = new MulticastSocket(4445);
        group = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(group);
    }
    public void run() {
        Map map = new Map();
        playersArray = new ArrayList<Character>();
        
        if(type == 1) {
            try{new MulticastServerThread(2).start();}catch(IOException e){};
            DatagramPacket packet, pkt;
            String[] players = new String[5];
            int playerCount = 0;
            try {
                byte[] empty;
                while(true) {
                    String[] str = new String[3];
                    empty = new byte[256];
                    packet = new DatagramPacket(empty, empty.length);
                    System.out.println("waiting");
                    socket.receive(packet);
                    map.printPlayers();
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Received: "+received);
                    str = received.split(";");
                    System.out.println("str[0] = "+str[0]+" / str[1] = "+str[1]);

                    //decide what to do with packet
                    //start with adding username to players array
                    if (str[0].equals("username")) {
                        if(playerCount >= 5) { //check if game is full
                            System.out.println("Game is full.");
                            pkt = buildPacket("full; Game is full; max of 5 players");
                            socket.send(pkt);
                        } else {
                            boolean taken = false;
                            for(int i=0; i<players.length; i++) { //check for taken name
                                if(str[1].equals(players[i])) {
                                    System.out.println("Player name: "+players[i]+" taken.");
                                    pkt = buildPacket("taken; player name already taken;"+players[i]);
                                    socket.send(pkt);
                                    i = players.length;
                                    taken = true;
                                }
                            } 
                            if(taken == false){
                                System.out.println("Player name: "+str[1]+" has joined.");
                                players[playerCount] = str[1];
                                pkt = buildPacket("login; player has logged in;"+players[playerCount]);
                                socket.send(pkt);
                                playerCount++;
                                pkt = buildPacket("output;New player: "+players[playerCount-1]+" has joined.");
                                socket.send(pkt);
                                //generate map
                                char icon = str[1].charAt(0);
                                map.addCharacter(new Character(14, 14, 14, 14, 14, 14, 20, 3, 3, str[1], icon));
                                //playersArray = map.getPlayers();
                                map.generateMap();
                                pkt = buildPacket(map.returnMap());
                                socket.send(pkt);
                            }
                        }
                        
                        
                        /*boolean taken = false;
                        for(int z = 0; z < playersArray.size(); z++) {
                            System.out.println("Player " + (z + 1) + " name: " + playersArray.get(z).getUsername());
                            if (str[1].equals(playersArray.get(z).getUsername())) {
                                taken = true;
                            }
                        }
                        if (playerCount >= 5 || taken) {
                        } else {
                            players[playerCount] = str[1];
                            System.out.println("Player "+(playerCount+1)+": "+players[playerCount]+" joined.");
                            playerCount++;
                            char icon = str[1].charAt(0);
                            map.addCharacter(new Character(14, 14, 14, 14, 14, 14, 20, 3, 3, str[1], icon));
                            playersArray = map.getPlayers();
                            map.generateMap();
                            pkt = buildPacket(map.returnMap());
                            socket.send(pkt);
                        }*/
                    } else if (str[0].equals("info")) {

                    } else if (str[0].equals("command")) {
                        System.out.println("Got command.");
                        switch(str[2]) {
                            case "up":
                                map.moveCharacter(str[1], 0);
                                pkt = buildPacket("output;"+str[1]+" moved up.");
                                socket.send(pkt);
                                break;
                            case "left":
                                map.moveCharacter(str[1], 1);
                                pkt = buildPacket("output;"+str[1]+" moved left.");
                                socket.send(pkt);
                                break;
                            case "down":
                                map.moveCharacter(str[1], 2);
                                pkt = buildPacket("output;"+str[1]+" moved down.");
                                socket.send(pkt);
                                break;
                            case "right":
                                map.moveCharacter(str[1], 3);
                                pkt = buildPacket("output;"+str[1]+" moved right.");
                                socket.send(pkt);
                                break;
                        }
                        System.out.println(map.getCoords(str[1]));
                        map.generateMap();
                        pkt = buildPacket(map.returnMap());
                        socket.send(pkt);
                    } else if (str[0].equals("echo")) {
                        pkt = buildPacket("hello from server");
                        socket.send(pkt);
                    }
                    /*byte[] buf = new byte[256];
                    String dString = "hello from server";
                    buf = dString.getBytes();
                    packet = new DatagramPacket(buf, buf.length, group, 4446);
                    System.out.println("Broadcasting");
                    socket.send(packet);*/
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type == 2) {
            //Output output = new Output();
            try {
                Output output = new Output();
                //output.sendText();
            } /*catch(InterruptedException e) {

            }*/ catch(IOException e){};
            //try{output.sendText();}catch(InterruptedException e){};
        }
	   socket.close();
    }

    public DatagramPacket buildPacket(String input) {
        DatagramPacket pkt;
        byte[] buf = new byte[256];
        buf = input.getBytes();
        pkt = new DatagramPacket(buf, buf.length, group, 4446);
        return pkt;
    }
}

class Output {
    MulticastSocket socket;
    InetAddress group;

    Output() throws IOException {
        this.socket = new MulticastSocket(4447);
        this.group = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(group);
    }

    void sendText() throws InterruptedException {
        try {
            while(true){
                byte[] empty = new byte[256];
                String txt = "This came from output class.";
                empty = txt.getBytes();
                DatagramPacket packet = new DatagramPacket(empty, empty.length, group, 4446);
                System.out.println("Sending txt");
                socket.send(packet);
                Thread.sleep(3000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}