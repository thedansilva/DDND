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
    int intelligence;
    int health;
    int defense;
    int resistance; // basically magical defense
    int x, y;
    int potionCount;
    String username; // the associated username for this character
    char icon; // to be displayed on the map
    String charClass;
            
    public Character(int x, int y, String username, char icon, String charClass) {
        /*
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.health = health;
        */
        this.x = x;
        this.y = y;
        this.username = username;
        this.icon = icon; 
        this.charClass = charClass;
        
       switch(charClass) {
           case "cleric": 
               this.strength = 11;
               this.dexterity = 15;
               this.intelligence = 17;
               this.health = 35;
               this.potionCount = 3;
               this.defense = 9;
               this.resistance = 14;
               break;
           case "barbarian": 
               this.strength = 19;
               this.dexterity = 14;
               this.intelligence = 9;
               this.health = 60;
               this.potionCount = 1;
               this.defense = 18;
               this.resistance = 5;
               break;
           case "mage": 
               this.strength = 9;
               this.dexterity = 17;
               this.intelligence = 19;
               this.health = 45;
               this.potionCount = 2;
               this.defense = 9;
               this.resistance = 18;
               break;            
           case "rogue": 
               this.strength = 17;
               this.dexterity = 18;
               this.intelligence = 14;
               this.health = 40;
               this.potionCount = 2;
               this.defense = 15;
               this.resistance = 10;
               break;               
       }
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
        return this.y;
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
