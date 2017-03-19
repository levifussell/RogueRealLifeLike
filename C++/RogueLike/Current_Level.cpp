#include "Current_Level.h"

Current_Level::Current_Level()
{
}


void Current_Level::load_level(string level_file, Hero &hero)
{//this loads the level from a given file
	ifstream inputLevel;

	inputLevel.open(level_file);
	if (inputLevel.fail())
	{
		perror(level_file.c_str());
	}
	string input;
	while (getline(inputLevel, input))
	{
		_level.push_back(input);
	}

	//load the monsters and the Hero from the level
	for (int y = 0; y < _level.size(); y++)
	{
		for (int x = 0; x < _level[y].size(); x++)
		{
			switch (_level[y][x])
			{
			case '*':
				hero.set_x_location(x);
				hero.set_y_location(y);
				break;
			case 'M':
				_monsters.push_back(Monster(40, 50));
				_monsters.back().set_x_location(x);
				_monsters.back().set_y_location(y);
				break;
			}
		}
	}
}

void Current_Level::display_level()
{
	for (int x = 0; x < _level.size(); x++)
	{
		printf("%s\n", _level[x].c_str());
	}
}

void Current_Level::moveChar(char input, Hero &hero)
{
	int x_location = hero.get_x_location();
	int y_location = hero.get_y_location();
	int target_x;
	int target_y;
	switch (input)
	{
	case 'w':
		if (_canMove(y_location - 1, x_location,_level[y_location - 1][x_location], hero))
		{
			_level[y_location][x_location] = ' ';
			_level[y_location - 1][x_location] = '*';
			hero.set_y_location(-1);
		}
		break;
	case 's':
		if (_canMove(y_location + 1, x_location, _level[y_location + 1][x_location], hero))
		{
			_level[y_location][x_location] = ' ';
			_level[y_location + 1][x_location] = '*';
			hero.set_y_location(+1);
		}
		break;
	case 'a':
		if (_canMove(y_location, x_location - 1, _level[y_location][x_location - 1], hero))
		{
			_level[y_location][x_location] = ' ';
			_level[y_location][x_location - 1] = '*';
			hero.set_x_location(-1);
		}
		break;
	case 'd':
		if (_canMove(y_location, x_location + 1, _level[y_location][x_location + 1], hero))
		{
			_level[y_location][x_location] = ' ';
			_level[y_location][x_location + 1] = '*';
			hero.set_x_location(+1);
		}
		break;
	default:
		break;
	}

}

bool Current_Level::_canMove(int target_y, int target_x, char whatIsThere, Hero &hero)
{
	switch (whatIsThere)
	{
	case '|':
	case '-':
	case '_':
		return false;
		break;
	case 'H':
	case '$':
		hero.pickUp(whatIsThere);
		return true;
		break;
	case ' ':
		return true;
	case 'M':
		if (battleMonster(hero, target_x, target_y))
		{
			return true; //monster has been defeated, player can move to this square
		}
		else
		{
			return false;
		}
		
		break;
	}
}

bool Current_Level::battleMonster(Hero &hero,int target_x,int target_y)
{
	int attackroll;
	int attackResult;
	for (int i = 0; i < _monsters.size(); i++)
	{
		if (target_x == _monsters[i].get_x_location() && target_y == _monsters[i].get_y_location())
		{
			//battle!
			attackroll = hero.attack();
			attackResult = _monsters[i].takeDamage(attackroll);
			printf("You attacked the monster with: %d attack\n armour: %d", attackroll, hero.getArmour());
			if (attackResult == 0)
			{
				printf("Monster died!\n");
				system("PAUSE");
				hero.addExperience(attackResult);
				return true;
			}
			
				attackroll = _monsters[i].attack();
				attackResult = hero.takeDamage(attackroll);
				
				
				if (attackResult != 0)
				{
					printf("You died");
					return false;
				}
			
		}
	}
	return false;
}






