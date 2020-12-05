/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mcserver;

/**
 *
 * @author dan
 */
public class Character {
    int strength;
    int dexterity;
    int constitution;
    int intelligence;
    int wisdom;
    int charisma; 
    int health;
    int x, y;
    String username; // the associated username for this character
    char icon; // to be displayed on the map
    
    public Character(int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int health, int x, int y, String username, char icon) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.health = health;
        this.x = x;
        this.y = y;
        this.username = username;
        this.icon = icon; 
    }
        
    public int getHealth() {
        return this.health;
    }
    
    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    public int getX() {
        return this.x;
    }
    
    public void setX(int newX) {
        this.x = newX;
    }
    
    public int getY() {
        return this.x;
    }
    
    public void setY(int newY) {
        this.y = newY;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String newname) {
        this.username = newname;
    }
    
    public char getIcon() {
        return this.icon;
    }
    
    public void setIcon(char newIcon) {
        this.icon = newIcon;
    }
}
