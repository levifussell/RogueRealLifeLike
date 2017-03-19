package com.example.danielmccarragher.gohide.BackendGameLogic;

/**
 * Created by levi on 18/03/17.
 */
public class Enemy extends GameObject{

  public Enemy(int posX, int posY)
  {
    super(posX, posY);
  }

  int health = 80;
  int armour = 50;

  int takeDamage(int attack)
  {
    attack -= armour;
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
    int attackRoll = (int)Math.random() * 60;

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
