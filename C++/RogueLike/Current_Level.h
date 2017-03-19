#pragma once
#include <string>
#include <vector>
#include <fstream>
#include <iostream>
#include "Monster.h"
#include "Hero.h"
using namespace std;
class Current_Level
{
public:
	Current_Level();
	void load_level(string level_file, Hero &hero);
	void display_level();
	void moveChar(char input, Hero &hero);
	bool battleMonster(Hero &hero, int target_x, int target_y);

private:
	bool _canMove(int target_x, int target_y,char whatIsThere, Hero &hero);
	vector<Monster> _monsters;
	vector<string> _level;
};

