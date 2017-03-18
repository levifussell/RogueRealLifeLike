#include "Hero.h"


Hero::Hero(int health, int armour, int attack, int money)
{
	_health = health;
	_armour = armour;
	_attack = attack;
	_money = money;
}

void Hero::findPlayer(vector<string> level)
{
	for (int y = 0; y < level.size(); y++)
	{
		for (int x = 0; x < level[y].size(); x++)
		{
			if (level[y][x] == '*')
			{
				_x_location = x;
				_y_location = y;
			}
		}
	}
}

void Hero::pickUp(char symbol)
{//the symbol of the collectable is picked up.
	string tempName;
	//it is them changes into it's name
	switch (symbol)
	{
	case '$':
		tempName = "Cash";
		break;
	case 'H':
		tempName = "Health";
		break;
	}
	list<Collectable>::iterator lit;

	//if one already exists then add one.
	for (lit = _collectables.begin(); lit != _collectables.end(); lit++)
	{
		if ((*lit).getName() == tempName)
		{
			(*lit).addOne();
			return;
		}

	}
	//if it doesn't exist them add the object
	_collectables.push_back(Collectable(tempName));
	if (tempName == "Cash")
	{
		_collectables.back().addValue(_money);
		_collectables.back().addOne();
	}
	else if (tempName == "Health")
	{
		_collectables.back().addOne();
	}
	
}

void Hero::printHero()
{
	printf("**Hero Stats**\n");
	printf("Health: %d\n", _health);
	printf("Wealth: %d\n", _money);
	printf("");
	printInventory();
	
}

void Hero::addExperience(int xp)
{
	_xp += xp; 
	//level up
	if (_xp >= 50)
	{
		printf("Leveled up!\n");
		_xp -= 50;
		_attack += 10;
		_armour += 5;
		_health += 5;
		_level += 1;
		system("PAUSE");
	}
}

int Hero::attack()
{
	static default_random_engine randomEngine(time(NULL));
	uniform_int_distribution<int> attackRoll(0, _attack);

	return attackRoll(randomEngine);
}

void Hero::printInventory()
{
	printf("\n");
	printf("**Inventory**\n");
	list<Collectable>::iterator lit;
	int i = 0;
	for (lit = _collectables.begin(); lit != _collectables.end(); lit++)
	{
		printf("%s x %d\n", (*lit).getName().c_str(), (*lit).getCount());
		i++;
	}
	cout << endl;
}

int Hero::takeDamage(int attack)
{
	attack -= _armour;
	//check if armour has been peirced
	if (_armour > 0)
	{
		_health -= attack;
		//check if it died
		if (_health <= 0)
		{
			return 1;
		}
	}
	return 0;

}



