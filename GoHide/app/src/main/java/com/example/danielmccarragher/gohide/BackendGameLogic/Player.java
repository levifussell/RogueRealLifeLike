package com.example.danielmccarragher.gohide.BackendGameLogic;

import android.util.Log;

/**
 * Created by levi on 18/03/17.
 */
public class Player extends GameObject{

  int armour = 60;
  int health = 100;
  public Player(int posX, int posY)
  {
    super(posX, posY);

  }

  public char[][] getScope(int size)
  {
    return GridWorld.GET_AREA(this.posX - size, this.posY - size, size * 2 + 1, size * 2 + 1);
  }

  public String getScopeAsString(int size)
  {
    char[][] scope = getScope(size);

    String scopeString = "";

    for(int y = 0; y < scope.length; ++y)
    {
      for(int x = 0; x < scope[y].length; ++x)
      {
        scopeString += scope[y][x];
      }

      scopeString += "\n";
    }

    return scopeString;
  }

  public boolean canMove(int intendedPosX, int intendedPosY)
  {

    switch (GridWorld.GET_CHAR(intendedPosX,intendedPosY))
    {
      case '#':
        return false;
      case 'H':
      case GAME_CHARACTERS.EMPTY_SPACE:
        return true;
      case 'M':
        if(fightMonster(intendedPosX,intendedPosY)){
          return true;
        }else{
          return false;
        }

    }
    return false;
  }

  private boolean fightMonster(int monsterX,int monsterY){
    Enemy monster = (Enemy) GridWorld.GET_OBJ(monsterX,monsterY);
    int attack = attack();
    int attackResult = monster.takeDamage(attack);
    Log.i("HIT","You hit the monster with" + attack + " attack");
    if (attackResult == 0)
    {
      Log.i("Result","Monster died!");
      //hero.addExperience(attackResult);
      return true;
    }

    attack = monster.attack();
    attackResult = takeDamage(attack);

    if (attackResult != 0)
    {
      Log.i("Result","You died");
      return false;
    }
    return false;
  }

  int takeDamage(int attack)
  {
    attack -= armour;
    //check if armour has been pierced
    if (armour > 0)
    {
      health -= attack;
      //check if I have died
      if (health <= 0)
      {
        return 1;
      }
    }
    return 0;

  }

  int attack(){
    int attackRoll = (int)Math.random() * 99;

    return attackRoll;
  }
  public void moveUp(){
    if(canMove(posX,posY-1)) {
        GridWorld.SET_CHAR(posX,posY,'*');
        GridWorld.SET_CHAR(posX,posY-1,'H');
        posY -= 1;
    }
  }
  public void moveLeft(){
    if(canMove(posX-1,posY)) {
      GridWorld.SET_CHAR(posX,posY,'*');
      GridWorld.SET_CHAR(posX-1,posY,'H');
      posX -= 1;
    }
  }
  public void moveRight(){
    if(canMove(posX+1,posY)) {
      GridWorld.SET_CHAR(posX,posY,'*');
      GridWorld.SET_CHAR(posX+1,posY,'H');
      posX += 1;
    }
  }
  public void moveDown(){
    if(canMove(posX,posY+1)) {
      GridWorld.SET_CHAR(posX,posY,'*');
      GridWorld.SET_CHAR(posX,posY+1,'H');
      posY += 1;
    }
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
