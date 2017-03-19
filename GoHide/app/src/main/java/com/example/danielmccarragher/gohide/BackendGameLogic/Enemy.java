package com.example.danielmccarragher.gohide.BackendGameLogic;

import java.util.Random;

/**
 * Created by levi on 18/03/17.
 */
public class Enemy extends GameObject{

  public Enemy(int posX, int posY)
  {
    super(posX, posY);
  }

  int health = 20;
  int armour = 60;

  int takeDamage(int attack)
  {
    armour -= attack  ;
    //check if armour has been pierced
    if (armour > 0)
    {
      health -= attack;
      //check if enemy died
      if (health <= 0)
      {
        return 20; //return xp
      }
    }
    return 0;

  }

  int attack(){
    int attackRoll = 0;
    Random diceRoller = new Random();
    for (int i = 0; i < 10; i++) {
      attackRoll = diceRoller.nextInt(60) + 1;

    }

    return attackRoll;
  }
  @Override
  public void Update()
  {

  }

  @Override
  public void Draw()
  {

  }
}
