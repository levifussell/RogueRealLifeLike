#include <string>
#include <fstream>
#include <iostream>
#include <cstdio>
#include <conio.h>
#include <vector>
#include "Current_Level.h"
#include "Hero.h"
#include "Monster.h"
#include <windows.h>
using namespace std;
Current_Level current_Level;
void clearScreen();

int main()
{
	Hero hero(80,20,35,10);
	Monster monster(100,100);
	current_Level.load_level("level_1.txt", hero);//load level
	current_Level.display_level();
	hero.printHero();

	char input;

	while (true)
	{//main game loop
		input = _getch();
		clearScreen();
		current_Level.moveChar(input,hero);
		current_Level.display_level();
		hero.printHero();
	}
}

void clearScreen()
{
	HANDLE hOut;
	COORD Position;

	hOut = GetStdHandle(STD_OUTPUT_HANDLE);

	Position.X = 0;
	Position.Y = 0;
	SetConsoleCursorPosition(hOut, Position);
}

