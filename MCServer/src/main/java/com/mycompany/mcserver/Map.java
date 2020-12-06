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
    String mapLayout[][]; // comes from the map file given
    ArrayList<Character> characters;
    public Map() {
        this.mapData = new String [9][20];
        this.mapLayout = new String [9][20];
        this.characters = new ArrayList<Character>();
        //System.out.println(System.getProperty("user.dir"));
        //shows where the maps are located
        try {
            File file = new File("Map1.txt");
            System.out.println("File found");
            Scanner in = new Scanner(file);
            String mapLine = "";
            for(int x = 0; x < 9; x++) {
                //System.out.println("DOING MAP STUFF");
                mapLine = in.nextLine();
                for(int y = 0; y < 20; y++) {
                    mapLayout[x][y] = mapLine.charAt(y) + ""; // convert char to string and set as map layout line
                    //mapLayout[x][y] = "S";
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("I CAN'T FIND THE FILE");
        }
    }
    
    public void addCharacter(Character character) {
        characters.add(character);
    }
    
    public void generateMap() {
        boolean playerFound = false; // boolean to see if a player was spotted at this location.
        for (int x = 0; x < 9; x++) {
            for(int y = 0; y < 20; y++) {
                for(int z = 0; z < characters.size(); z++) {
                    if(!playerFound) {
                        if(characters.get(z).getX() == x && characters.get(z).getY() == y) {
                            playerFound = true; // disable finding new players for this location, almost like a post-function break; statement
                            System.out.println("Found player " + characters.get(z).getUsername() + " at position " + getCoords(characters.get(z).getUsername()));
                            mapData[x][y] = characters.get(z).getIcon() + "";
                        } else {
                            mapData[x][y] = mapLayout[x][y];
                        }
                    }
                }
                    playerFound = false; // reset the boolean for future spots
            }
        }
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
    
    public void printPlayers() {
        for(int z = 0; z < characters.size(); z++) {
                    System.out.println(characters.get(z).getUsername() + " " + getCoords(characters.get(z).getUsername()));
                }
    }
    
    public ArrayList<Character> getPlayers() {
        return characters;
    }
        
    // takes 4 arguments: 0 (up), 1 (left), 2 (down), and 3 (right). Checks to see if the next spot is a . or not.
    // try is used in case the area checked is out of the array, which means it is definitely not traversable.
    public boolean isUntraversable(String username, int position) {
        for(int z = 0; z < characters.size(); z++) { 
            if(characters.get(z).getUsername().equals(username)) {
                try { 
                    switch(position) {
                        case 0:
                            System.out.println("Above you is " + mapLayout[characters.get(z).getX() - 1][characters.get(z).getY()]);
                            if (!(mapLayout[characters.get(z).getX() - 1][characters.get(z).getY()].equals(".")))
                            return true;
                            break;
                        case 1:
                            System.out.println("To your left is " + mapLayout[characters.get(z).getX()][characters.get(z).getY() - 1]);
                            if (!(mapLayout[characters.get(z).getX()][characters.get(z).getY() - 1].equals(".")))
                            return true;
                            break;
                        case 2:
                            System.out.println("Below you is " + mapLayout[characters.get(z).getX() + 1][characters.get(z).getY()]);
                            if (!(mapLayout[characters.get(z).getX() + 1][characters.get(z).getY()].equals(".")))
                            return true;
                            break;
                        case 3:
                            System.out.println("To your right is " + mapLayout[characters.get(z).getX()][characters.get(z).getY() + 1]);
                            if (!(mapLayout[characters.get(z).getX()][characters.get(z).getY() + 1].equals(".")))
                            return true;
                            break;
                    }
                        return false;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Out of bounds");
                    return true;
                    }
                }
            }
        return true;
    }
    
    public String getCoords(String username) {
        for(int z = 0; z < characters.size(); z++) {   
            if(characters.get(z).getUsername().equals(username)) {
                return characters.get(z).getX() + " . " + characters.get(z).getY();
            }
        }
        return "y";
    }
    
    
    public void moveCharacter(String username, int x) {
        for(int z = 0; z < characters.size(); z++) {   
            if(characters.get(z).getUsername().equals(username)) {
            switch(x) {
                // the following if statements have HARD boundaries set (-1, -1, 9, 20) and are representative of
                // the boundaries of the board. since the minimum coordinates are 0,0 and the max are 8,20 ,
                // anything less or more than that will be caught and ignored. 
                case 0: // up
                    if(characters.get(z).getX() - 1 <= -1 || isUntraversable(characters.get(z).getUsername(), 0)) {
                        //out of bounds, do nothing
                    } else 
                        characters.get(z).setX(characters.get(z).getX() - 1);
                    break;
                case 1: // left
                    if(characters.get(z).getY() - 1 <= -1 || isUntraversable(characters.get(z).getUsername(), 1)) {
                        //out of bounds, do nothing
                    } else 
                       characters.get(z).setY(characters.get(z).getY() - 1);
                    break;
                case 2: //down
                    if(characters.get(z).getX() + 1 >= 9 || isUntraversable(characters.get(z).getUsername(), 2)) {
                        //out of bounds, do nothing
                    } else 
                        characters.get(z).setX(characters.get(z).getX() + 1);
                    break;
                case 3: // right
                    if(characters.get(z).getY() + 1 >= 20 || isUntraversable(characters.get(z).getUsername(), 3)) {
                        //out of bounds, do nothing
                    } else 
                        characters.get(z).setY(characters.get(z).getY() + 1);

                    break;
            }
            }
        }
    }


}
