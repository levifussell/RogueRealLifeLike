#pragma once
#include "Collectable.h"
#include <vector>
#include <iostream>
#include <ctime>
#include <random>
using namespace std;
class Monster
{
public:
	Monster(int health, int armour);
	//getters
	int getHealth() { return _health; };
	int get_x_location() { return _x_location; };
	int get_y_location() { return _y_location; };
	//setters
	void set_x_location(int distance) { _x_location = distance; };
	void set_y_location(int distance) { _y_location = distance; };
	//functions
	void monsterPatrol(char next);
	int attack();
	int takeDamage(int attack);
	void findMonster(vector<string> level);
	void turnLeft(vector<string> &level, int x_location, int y_location, Monster &monster);
	void turnRight(vector<string> &level, int x_location, int y_location, Monster &monster);
	void up(vector<string> &level, int x_location, int y_location, Monster &monster);
	void down(vector<string> &level, int x_location, int y_location, Monster &monster);
	void special(vector<string> &level, int x_location, int y_location, Monster &monster);
private:
	int _x_location = 0;
	int _y_location = 0;
	int _health;
	int _armour;
	int _attack = 30;
	int _xpValue;
};

