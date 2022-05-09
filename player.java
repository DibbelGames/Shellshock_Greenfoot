import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class player extends Actor
{    
    private int moveSpeed = 4;
    private int rotation;
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
        if(Greenfoot.isKeyDown("a") && myTurn)
            move(-moveSpeed);
        else if(Greenfoot.isKeyDown("d") && myTurn)
            move(moveSpeed);
        
        if(Greenfoot.isKeyDown("left") && myTurn)
            rotation--;
        else if(Greenfoot.isKeyDown("right") && myTurn)
            rotation++;
        
        getWorld().showText("rot: " + rotation, getX(), getY() + 100);
            
        if(Greenfoot.isKeyDown("space") && myTurn)
        {
            bullet b = new bullet();
            b.setRotation(rotation);
            getWorld().addObject(b, getX(), getY());
            
            myTurn = false;
        }
    }
}
