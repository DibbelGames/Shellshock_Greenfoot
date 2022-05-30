import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Tank extends Actor
{
    public Vector2 position;
    
    MyWorld world;
    
    public Tank(MyWorld world)
    {
        this.world = world;
        position = new Vector2(40, 300);
    }
    
    public void act()
    {
        if(Greenfoot.isKeyDown("a"))
        {
            position.X--;
        }
        if(Greenfoot.isKeyDown("d"))
        {
            position.X++;
        }
        
        position.Y = world.getYPos(position.X);
        this.setLocation(position.X, position.Y);
        getWorld().showText("pos: " + position.X + ", " + position.Y, 60, 30);
    }
}
