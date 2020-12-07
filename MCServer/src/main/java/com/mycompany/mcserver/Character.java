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
    int atkrange;
    int splrange;
    int splslots;
    int moverange;
    String username; // the associated username for this character
    char icon; // to be displayed on the map
    boolean clientAlive;
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
        this.clientAlive = true;
        switch (charClass) {
            case "cleric":
                this.strength = 11;
                this.dexterity = 15;
                this.intelligence = 17;
                this.health = 45;
                this.potionCount = 4;
                this.defense = 9;
                this.resistance = 14;
                this.atkrange = 1;
                this.splrange = 4;
                this.splslots = 4;
                this.moverange = 5;
                break;
            case "barbarian":
                this.strength = 19;
                this.dexterity = 14;
                this.intelligence = 9;
                this.health = 60;
                this.potionCount = 1;
                this.defense = 18;
                this.resistance = 5;
                this.atkrange = 1;
                this.splrange = 0;
                this.splslots = 0;
                this.moverange = 4;
                break;
            case "mage":
                this.strength = 9;
                this.dexterity = 17;
                this.intelligence = 19;
                this.health = 45;
                this.potionCount = 2;
                this.defense = 9;
                this.resistance = 18;
                this.atkrange = 1;
                this.splrange = 6;
                this.splslots = 6;
                this.moverange = 4;
                break;
            default:
                this.setCharClass("rogue");
            case "rogue":
                this.strength = 17;
                this.dexterity = 18;
                this.intelligence = 14;
                this.health = 40;
                this.potionCount = 2;
                this.defense = 15;
                this.resistance = 10;
                this.atkrange = 5;
                this.splrange = 0;
                this.splslots = 0;
                this.moverange = 5;
                break;
        }
        //this.moverange = 999;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public int getHealth() {
        return this.health;
    }

    public int getDefense() {
        return this.defense;
    }

    public int getResistance() {
        return this.resistance;
    }

    public int getPotionCount() {
        return this.potionCount;
    }

    public void setPotionCount(int newCount) {
        this.potionCount = newCount;
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

    public String getCharClass() {
        return this.charClass;
    }

    public void setCharClass(String newClass) {
        this.charClass = newClass;
    }
    
    public boolean getLiveStatus() {
        return this.clientAlive;
    }

    public void setLiveStatus(boolean newStatus) {
        this.clientAlive = newStatus;
    }
    
    public int getMoveRange() {
        return this.moverange;
    }
}
