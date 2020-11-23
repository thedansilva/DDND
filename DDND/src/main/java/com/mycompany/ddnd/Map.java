/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddnd;

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
    String CharacterData[][];
    public Map() {
        this.mapData = new String [20][9];
        this.CharacterData = new String[20][9];
    }
    
    public void generateMap() {
        for (int x = 0; x < 9; x++) {
            for(int y = 0; y < 20; y++) {
                mapData[x][y] = ".";
            }
        }
    }
    
    public void moveCharacter(int x) {
        switch(x) {
            case 0: // up
                break;
            case 1: // left
                break;
            case 2: //down
                break;
            case 3: // right
                break;
        }
    }
    public String returnMap() {
        return "hi";
    }
}
