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
    
    public Character(int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int health, int x, int y, String username) {
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
}
