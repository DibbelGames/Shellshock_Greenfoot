import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;

public class MyWorld extends World
{
    public Vector2[] positions = new Vector2[600];
    
    public MyWorld()
    {    
        super(600, 400, 1); 
        prepare();
    }

    private void prepare()
    {
        Tank tank = new Tank(this);
        addObject(tank,175,205);
        
        for(int x = 0; x < 600; x++)
        {
            positions[x] = new Vector2(x, (int)(Math.sin(Math.toRadians(x)) * 50) + 100);
        }
    }
    
    public int getYPos(int x)
    {
        return positions[x].Y;
    }
}
