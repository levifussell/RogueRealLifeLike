#pragma once
#include <string>
#include <list>
#include "Collectable.h"
#include <vector>
#include <iostream>
#include <random>
#include <ctime>
using namespace std;
class Hero
{
public:
	Hero(int health, int armour, int attack, int money);
	//getters
	int getMoney() { return _money; };
	int get_x_location() { return _x_location; };
	int get_y_location() { return _y_location; };
	int getAttack() { return _attack; };
	int getArmour() { return _armour; };
	int getHealth() { return _health; };
	//setters
	void set_x_location(int distance) { _x_location += distance; };
	void set_y_location(int distance) { _y_location += distance; };
	//functions
	void pickUp(char symbol);
	void printInventory();
	void findPlayer(vector<string> level);
	void printHero();
	void addExperience(int xp);
	int attack();
	int takeDamage(int attack);
private:
	string _name;
	list<Collectable> _collectables;
	int _money;
	int _x_location;
	int _y_location;
	int _health;
	int _armour;
	int _attack;
	int _xp;
	int _level;

	
	
};

