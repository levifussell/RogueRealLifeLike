#include "Monster.h"



Monster::Monster(int health, int armour)
{
	_health = health;
	_armour = armour;
}

void Monster::findMonster(vector<string> level)
{
	for (int y = 0; y < level.size(); y++)
	{
		for (int x = 0; x < level[y].size(); x++)
		{
			if (level[y][x] == 'M')
			{
				_x_location = x;
				_y_location = y;
			}
		}
	}
}

void Monster::monsterPatrol(char next)
{
	
	while (next != '-')
	{

	}
}

int Monster::takeDamage(int attack)
{
	attack -= _armour;
	//check if armour has been peirced
	if (_armour > 0)
	{
		_health -= attack;
		//check if it died
		if (_health <= 0)
		{
			return _xpValue;
		}
	}
		return 0;
	
}

int Monster::attack()
{
	static default_random_engine randomEngine(time(NULL));
	uniform_int_distribution<int> attackRoll(0, _attack);

	return attackRoll(randomEngine);
}






