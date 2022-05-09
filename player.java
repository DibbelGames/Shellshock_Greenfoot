import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class player extends Actor
{    
    private int moveSpeed = 4;
    public int id;
    public boolean myTurn;
    
    public player(int id)
    {
        this.id = id;
        if(id == 0)
            myTurn = true;
    }
    
    //man ist dieser code dreckig x)
    
    public void act()
    {
        if(Greenfoot.isKeyDown("left"))
        {
            move(-moveSpeed);
        }
        else if(Greenfoot.isKeyDown("right"))
        {
            move(moveSpeed);
        }
        
        if(Greenfoot.isKeyDown("space") && myTurn)
        {
            bullet b = new bullet();
            getWorld().addObject(b, getX(), getY());
            
            myTurn = false;
        }
    }
}
