#pragma once
#include <string>
using namespace std;

class Collectable
{
public:
	Collectable(string tempName);
	void addOne();
	void removeOne();
	string getName() { return _name; };
	int getValue() { return _value; };
	int getCount() { return _count; };
	void addValue(int &money);
private:
	string _name;
	int _value = 0;
	int _count = 0;
};

