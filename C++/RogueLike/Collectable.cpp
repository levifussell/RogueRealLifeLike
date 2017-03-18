#include "Collectable.h"



Collectable::Collectable(string tempName)
{
	_name = tempName;
}


void Collectable::addOne()
{
	_count++;
}
void Collectable::removeOne()
{
	if (_count > 0)
	{
		_count--;
	}
}

void Collectable::addValue(int &money)
{
	_value += 20;
	money += 20;
}


