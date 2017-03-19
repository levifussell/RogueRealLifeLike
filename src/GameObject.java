/**
 * Created by levi on 18/03/17.
 */
public abstract class GameObject {

  public int posX;
  public int posY;

  public GameObject(int posX, int posY)
  {
    this.posX = posX;
    this.posY = posY;
  }

  public abstract void Update();
  public abstract void Draw();

  public int getPosX() { return this.posX; }
  public int getPosY() { return this.posY; }
}
