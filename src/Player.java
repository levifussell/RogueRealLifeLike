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

  @Override
  public void Update()
  {

  }

  @Override
  public void Draw()
  {

  }
}
