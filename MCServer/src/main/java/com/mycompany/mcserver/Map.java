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
    String mapData[][];
    int CharacterData[]; // holds the x and y coordinates of the character.
    
    public Map() {
        this.mapData = new String [9][20];
        this.CharacterData = new int[2];
        this.CharacterData[0] = 0; // x coordinate
        this.CharacterData[1] = 0; // y coordinate
    }
    
    public void generateMap() {
        for (int x = 0; x < 9; x++) {
            for(int y = 0; y < 20; y++) {
                if(this.CharacterData[0] == x && this.CharacterData[1] == y) {
                    mapData[x][y] = "@";
                } else {
                    mapData[x][y] = ".";
                }
            }
        }
    }
    
    public void moveCharacter(int x) {
        switch(x) {
            case 0: // up
                    this.CharacterData[0]--;
                break;
            case 1: // left
                    this.CharacterData[1]--;
                break;
            case 2: //down
                    this.CharacterData[0]++;
                break;
            case 3: // right
                    this.CharacterData[1]++;
                break;
        }
    }
    
    public String getCoords() {
        return this.CharacterData[1] + "," + this.CharacterData[0];
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
