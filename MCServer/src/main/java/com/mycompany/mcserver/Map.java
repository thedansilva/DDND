package com.mycompany.mcserver;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author dan
 */
public class Map {
    final String EMPTY = ".";
    final String[] WALL = {"|", "-"};
    String mapData[][]; // is a combination of the map data and the character's positions
    int CharacterData[]; // holds the x and y coordinates of the character.
    String mapLayout[][]; // comes from the map file given
    
    public Map() {
        this.mapData = new String [9][20];
        this.mapLayout = new String [9][20];
        this.CharacterData = new int[2];
        this.CharacterData[0] = 3; // x coordinate
        this.CharacterData[1] = 7; // y coordinate
        // THESE ARE 3 AND 7 AS PLACEHOLDER VALUES AND SHOULD EITHER
        // GET DECIDED BY THE USER BEFORE THE GAME STARTS OR, MUCH EASIER,
        // PREDETERMINED SPAWNS ON THE MAP. (CHAR DEPENDANT?)
        System.out.println(System.getProperty("user.dir"));

        try {
            File file = new File("Map1.txt");
            Scanner in = new Scanner(file);
            String mapLine = "";
            for(int x = 0; x < 9; x++) {
                System.out.println("DOING MAP STUFF");
                mapLine = in.nextLine();
                for(int y = 0; y < 20; y++) {
                    mapLayout[x][y] = mapLine.charAt(y) + ""; // convert char to string and set as map layout line
                    //mapLayout[x][y] = "S";
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("I CAN'T FIND IT");
        }
    }
    
    public void generateMap() {
        for (int x = 0; x < 9; x++) {
            for(int y = 0; y < 20; y++) {
                if(this.CharacterData[0] == x && this.CharacterData[1] == y) {
                    mapData[x][y] = "T";
                } else {
                    mapData[x][y] = mapLayout[x][y];
                }
            }
        }
    }
    
    // takes 4 arguments: 0 (up), 1 (left), 2 (down), and 3 (right). Checks to see if the next spot is a . or not.
    // try is used in case the area checked is out of the array, which means it is definitely not traversable.
    public boolean isUntraversable(int position) {
        try { 
            switch(position) {
                case 0:
                    if (!(mapLayout[this.getCharY() - 1][this.getCharX()].equals(".")))
                    return true;
                    break;
                case 1:
                    if (!(mapLayout[this.getCharY()][this.getCharX() - 1].equals(".")))
                    return true;
                    break;
                case 2:
                    if (!(mapLayout[this.getCharY() + 1][this.getCharX()].equals(".")))
                    return true;
                    break;
                case 3:
                    if (!(mapLayout[this.getCharY()][this.getCharX() + 1].equals(".")))
                    return true;
                    break;
            }
                return false;
            } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Out of bounds");
            return true;
        }
    }
    
    
    public void moveCharacter(int x) {
        switch(x) {
            // the following if statements have HARD boundaries set (-1, -1, 9, 20) and are representative of
            // the boundaries of the board. since the minimum coordinates are 0,0 and the max are 8,20 ,
            // anything less or more than that will be caught and ignored. 
            case 0: // up
                if(this.getCharY() - 1 <= -1 || isUntraversable(0)) { //|| !(mapLayout[CharacterData[0]][CharacterData[1] - 1].equals("."))) {
                    //out of bounds, do nothing
                } else 
                    this.CharacterData[0]--;
                break;
            case 1: // left
                if(this.getCharX() - 1 <= -1 || isUntraversable(1)) {
                    //out of bounds, do nothing
                } else 
                    this.CharacterData[1]--;
                break;
            case 2: //down
                if(this.getCharY() + 1 >= 9 || isUntraversable(2)) {
                    //out of bounds, do nothing
                } else 
                    this.CharacterData[0]++;
                break;
            case 3: // right
                if(this.getCharX() + 1 >= 20 || isUntraversable(3)) {
                    //out of bounds, do nothing
                } else 
                    this.CharacterData[1]++;
                break;
        }
    }
    
    public String getCoords() {
        return this.getCharX() + "," + this.getCharY();
    }
    
    public int getCharX() {
        return this.CharacterData[1];
    }
        
    public int getCharY() {
        return this.CharacterData[0];
    }
    public String returnMap() {
        String mapString = "";
        for (int x = 0; x < 9; x++) {
            for(int y = 0; y < 20; y++) {
                mapString += (this.mapData[x][y]);
            }
        }
        return "map;" + mapString;
    }
}
