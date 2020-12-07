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
    boolean gameStarted;

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

        if (type == 1) {
            try {
                new MulticastServerThread(2).start();
            } catch (IOException e) {
            };
            DatagramPacket packet, pkt;
            //String[] players = new String[5];
            ArrayList<String> players = new ArrayList<String>();
            //int playerCount = 0;
            try {
                byte[] empty;

                gameStarted = false;
                // gameStarted is false until a logged in user sends "start" 
                // to signal that everyone is in the lobby and they want to start 
                // the game

                while (!gameStarted) {
                    String[] str = new String[3];
                    empty = new byte[256];
                    packet = new DatagramPacket(empty, empty.length);
                    System.out.println("waiting");
                    socket.receive(packet);
                    try {
                        map.printPlayers();
                        String received = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("Received: " + received);
                        str = received.replaceAll(" ", ";").split(";");
                        System.out.println("str[0] = " + str[0] + " / str[1] = " + str[1]);

                        //decide what to do with packet
                        //start with adding username to players array
                        if (str[0].equals("username")) {
                            if (players.size() >= 5 || str[1].equals("")) { //check if game is full
                                System.out.println("Game is full.");
                                pkt = buildPacket("full; Game is full; max of 5 players");
                                socket.send(pkt);
                            } else {
                                boolean taken = false;
                                for (int i = 0; i < players.size(); i++) { //check for taken name
                                    if (str[1].equals(players.get(i))) {
                                        System.out.println("Player name: " + players.get(i) + " taken.");
                                        pkt = buildPacket("taken; player name already taken;" + players.get(i));
                                        socket.send(pkt);
                                        i = players.size();
                                        taken = true;
                                    }
                                }
                                if (taken == false) {
                                    System.out.println("Player name: " + str[1] + " has joined.");
                                    players.add(str[1]);
                                    pkt = buildPacket("login;player has logged in;" + players.get(players.size() - 1));
                                    socket.send(pkt);
                                    //generate map
                                    char icon = str[1].charAt(0);
                                    int[] safeCoords = map.getSafeCoords();

                                    // available classes for use:
                                    // Cleric: bad physical, good spells (6), 3 potions, medium HP
                                    // Barbarian: amazing physical, no spells, 1 potion, big HP
                                    // Mage: great spells (7), bad physical attack (dagger), 2 potions, low HP.
                                    // Rogue: infinite ranged attacks but mediocre health and 1 potion. 
                                    String charClass = str[2];
                                    map.addCharacter(new Character(safeCoords[0], safeCoords[1], str[1], icon, charClass));
                                    //playersArray = map.getPlayers();
                                    map.generateMap();
                                    pkt = buildPacket(map.returnMap());
                                    socket.send(pkt);
                                    pkt = buildPacket(map.getAllStats());
                                    socket.send(pkt);
                                }
                            }

                            System.out.println(map.getCoords(str[1]));
                            map.generateMap();
                            pkt = buildPacket(map.returnMap());
                            socket.send(pkt);
                        }
                        if (str[0].equals("start") && (!str[1].equals(""))) {
                            System.out.println("Found logged in player requesting start. Starting game.");
                            gameStarted = true;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("array error caught");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // GAME HAS STARTED PAST THIS POINT
            try {
                byte[] empty;
                ArrayList<Character> order = map.getPlayers();
                Collections.shuffle(order);
                socket.send(buildPacket("output;GAME STARTING\n"));
                socket.send(buildPacket("output;Order:\n"));
                for (int z = 0; z < order.size(); z++) {
                    socket.send(buildPacket("output;" + order.get(z).getUsername() + "\n")); // send order list to users
                }

                int playersCount = order.size();
                int currentPlayer = 0;
                int moveRange = 0;
                int movesLeft = 0;
                int checkX, checkY; // used to check if the user's movement actually moved them on the map
                String currentPlayerUsername = "";

                while (map.playersAlive() > 1) {
                    currentPlayerUsername = order.get(currentPlayer).getUsername();
                    moveRange = order.get(currentPlayer).getMoveRange();
                    movesLeft = order.get(currentPlayer).getMoveRange();
                    while (currentPlayerUsername.equals(order.get(currentPlayer).getUsername())) { // while it is still the current player's turn
                        System.out.println("It is currently " + currentPlayerUsername + "'s turn.");
                        String[] str = new String[3];
                        empty = new byte[256];
                        packet = new DatagramPacket(empty, empty.length);
                        System.out.println("waiting");
                        socket.receive(packet);
                        try {
                            map.printPlayers();
                            String received = new String(packet.getData(), 0, packet.getLength());
                            System.out.println("Received: " + received);
                            str = received.replaceAll(" ", ";").split(";");
                            System.out.println("str[0] = " + str[0] + " / str[1] = " + str[1]);
                            if (str[1].equals(currentPlayerUsername)) {
                                switch (str[0]) { // switch for the input that the user put in, be it command, attack, spell, potion, or wait
                                    case "command":
                                        checkX = order.get(currentPlayer).getX();
                                        checkY = order.get(currentPlayer).getY();
                                        System.out.println("Got command.");
                                        if (movesLeft > 0) {
                                            switch (str[2]) {
                                                case "up":
                                                    map.moveCharacter(str[1], 0);
                                                    pkt = buildPacket("output;" + str[1] + " tried to move up.\n");
                                                    socket.send(pkt);
                                                    break;
                                                case "left":
                                                    map.moveCharacter(str[1], 1);
                                                    pkt = buildPacket("output;" + str[1] + " tried to move left.\n");
                                                    socket.send(pkt);
                                                    break;
                                                case "down":
                                                    map.moveCharacter(str[1], 2);
                                                    pkt = buildPacket("output;" + str[1] + " tried to move down.\n");
                                                    socket.send(pkt);
                                                    break;
                                                case "right":
                                                    map.moveCharacter(str[1], 3);
                                                    pkt = buildPacket("output;" + str[1] + " tried to move right.\n");
                                                    socket.send(pkt);
                                                    break;
                                            }
                                            if (!(checkX == order.get(currentPlayer).getX() && checkY == order.get(currentPlayer).getY())) {
                                                // if the coordinates of the player are no longer the initial coordinates we checked, checkX and checkY,
                                                // this means the player truly moved and was not blocked by obstacles or map boundaries. subtract from total moves left.
                                                movesLeft--;
                                            }
                                        } else {
                                            pkt = buildPacket("output;" + str[1] + " has no more moves left.\n");
                                            socket.send(pkt);
                                        }
                                        break;
                                    case "attack":
                                        String attackresult;
                                        String[] arst = new String[4];
                                        switch (str[2]) {
                                            case "up":
                                                attackresult = map.findOpponent(order.get(currentPlayer), 0, 0);
                                                arst = attackresult.split(";");
                                                pkt = buildPacket("output;" + str[1] + " attacked upward.\n");
                                                socket.send(pkt);
                                                break;
                                            case "left":
                                                attackresult = map.findOpponent(order.get(currentPlayer), 0, 1);
                                                arst = attackresult.split(";");
                                                pkt = buildPacket("output;" + str[1] + " attacked to the left.\n");
                                                socket.send(pkt);
                                                break;
                                            case "down":
                                                attackresult = map.findOpponent(order.get(currentPlayer), 0, 2);
                                                arst = attackresult.split(";");
                                                pkt = buildPacket("output;" + str[1] + " attacked downward.\n");
                                                socket.send(pkt);
                                                break;
                                            case "right":
                                                attackresult = map.findOpponent(order.get(currentPlayer), 0, 3);
                                                arst = attackresult.split(";");
                                                pkt = buildPacket("output;" + str[1] + " attacked to right.\n");
                                                socket.send(pkt);
                                                break;
                                        }
                                        break;
                                    case "spell":
                                        switch (str[2]) {
                                            case "up":
                                                attackresult = map.findOpponent(order.get(currentPlayer), 1, 0);
                                                arst = attackresult.split(";");
                                                pkt = buildPacket("output;" + str[1] + " attacked with a spell upward.\n");
                                                socket.send(pkt);
                                                break;
                                            case "left":
                                                attackresult = map.findOpponent(order.get(currentPlayer), 1, 1);
                                                arst = attackresult.split(";");
                                                pkt = buildPacket("output;" + str[1] + " attacked with a spell to the left.\n");
                                                socket.send(pkt);
                                                break;
                                            case "down":
                                                attackresult = map.findOpponent(order.get(currentPlayer), 1, 2);
                                                arst = attackresult.split(";");
                                                pkt = buildPacket("output;" + str[1] + " attacked with a spell downward.\n");
                                                socket.send(pkt);
                                                break;
                                            case "right":
                                                attackresult = map.findOpponent(order.get(currentPlayer), 1, 3);
                                                arst = attackresult.split(";");
                                                pkt = buildPacket("output;" + str[1] + " attacked with a spell to right.\n");
                                                socket.send(pkt);
                                                break;
                                        }
                                        break;
                                    case "potion":
                                        // heal user idk
                                        break;
                                    case "wait":
                                        pkt = buildPacket("output;" + str[1] + " waits.\n");
                                        socket.send(pkt);
                                        if(currentPlayer + 1 >= playersCount) {
                                            currentPlayer = 0; //cycle back to first player
                                        } else {
                                            currentPlayer++; // go to next player
                                        }
                                        break;
                                }
                                System.out.println(map.getCoords(str[1]));
                                map.generateMap();
                                pkt = buildPacket(map.returnMap());
                                socket.send(pkt);
                            } else {
                                System.out.println("Player " + str[1] + " tried to take an action while it is currently " + currentPlayerUsername + "'s turn.");
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("array error caught");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type == 2) {
            //heartbeat monitor. determines when a player is considered offline/dead client.
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
            while (true) {
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
