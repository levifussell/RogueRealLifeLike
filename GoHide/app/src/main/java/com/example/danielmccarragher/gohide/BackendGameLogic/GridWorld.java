package com.example.danielmccarragher.gohide.BackendGameLogic;



import java.util.ArrayList;

/**
 * Created by levi on 18/03/17.
 */
public class GridWorld {

  public static final int SIZE_X = 3;
  public static final int SIZE_Y = 3;
  public static char[][] GRID;
  public static ArrayList<GameObject> players;
  public static ArrayList<GameObject> enemies;

  public static void LOAD()
  {
    players = new ArrayList<GameObject>();
    enemies = new ArrayList<GameObject>();

    GRID = new char[SIZE_Y][SIZE_X];

    CLEAR();
  }

  public static void LOAD_LEVEL(String levelData)
  {
    for(int i = 0; i < levelData.length(); ++i)
    {
      int x = i % SIZE_X;
      int y = (int)((float)i / (float)SIZE_X);
      char c = levelData.charAt(i);
      GRID[y][x] = c;
      ADD_OBJ(x, y, c);
    }
  }

  public static void CLEAR()
  {
    System.out.println(GRID.length);
    for(int y = 0; y < GRID.length; ++y)
    {
      for(int x = 0; x < GRID[y].length; ++x)
      {
        GRID[y][x] = GAME_CHARACTERS.EMPTY_SPACE;
      }
    }
  }

  public static char GET_CHAR(int x, int y)
  {
    //check bounds
    if(x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y)
      return GAME_CHARACTERS.NULL_SPACE;

    return GRID[y][x];
  }

  public static void SET_CHAR(int x, int y, char placeHolder){
      GRID[y][x] = placeHolder;
  }


  public static GameObject GET_OBJ(int x, int y)
  {
    return GET_OBJ(x, y, GET_CHAR(x, y));
  }

  private static GameObject GET_OBJ(int x, int y, char character)
  {
    switch (character)
    {
      case GAME_CHARACTERS.PLAYER:
        return FIND_OBJ(x, y, players);
      case GAME_CHARACTERS.ENEMY:
        return FIND_OBJ(x, y, enemies);
      default:
        return null;
    }
  }

  private static GameObject FIND_OBJ(int x, int y, ArrayList<GameObject> objs)
  {
    for(int i = 0; i < objs.size(); ++i)
    {
      if(objs.get(i).getPosX() == x && objs.get(i).getPosY() == y)
      {
        return objs.get(i);
      }
    }

    return null;
  }

  public static GameObject ADD_OBJ(int x, int y, char character)
  {
    switch (character)
    {
      case GAME_CHARACTERS.PLAYER:
        Player p = new Player(x, y);
        players.add(p);
        return p;
      case GAME_CHARACTERS.ENEMY:
        Enemy e = new Enemy(x, y);
        enemies.add(e);
        return e;
      default:
        return null;
    }
  }

  public static char[][] GET_AREA(int startX, int startY, int width, int height)
  {
    char[][] area = new char[height][width];

    //first we make all the data in the area NULL_SPACE
    for(int i = 0; i < area.length; ++i)
    {
      for(int j = 0; j < area[i].length; ++j)
      {
        area[i][j] = GAME_CHARACTERS.NULL_SPACE;
      }
    }

    //now we add any data in the grid that overlaps with the bounds
    for(int y = startY; y < startY + height; ++y)
    {
      for(int x = startX; x < startX + width; ++x)
      {
        area[y - startY][x - startX] = GET_CHAR(x, y);
      }
    }

    return area;
  }

  public static void DEBUG_DRAW()
  {
    String gridOut = "";

    for(int y = 0; y < GRID.length; ++y)
    {
      for(int x = 0; x < GRID[y].length; ++x)
      {
        gridOut += GRID[y][x];
      }

      gridOut += "\n";
    }

    System.out.println(gridOut);

    System.out.println("\nDEBUG DATA:\n" + "\tNUM PLAYERS: " + players.size() + "\n\tNUM ENEMIES: " + enemies.size());

    System.out.println("\nPLAYER1 SCOPE: \n" + ((Player)players.get(0)).getScopeAsString(1));
  }


}
