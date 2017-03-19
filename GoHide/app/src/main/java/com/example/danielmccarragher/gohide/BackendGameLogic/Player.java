package com.example.danielmccarragher.gohide.BackendGameLogic;

import android.util.Log;

/**
 * Created by levi on 18/03/17.
 */
public class Player extends GameObject{

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
        return false;
    }
    return false;
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
