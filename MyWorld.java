import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class MyWorld extends World
{
    public player player0;
    public player player1;
    
    public GameManager gameManager;
    
    public MyWorld()
    {    
        super(600, 400, 1); 
        prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        player0 = new player(0);
        player1 = new player(1);
        gameManager = new GameManager();
        addObject(player0, 145,286);
        addObject(player1,461, 276);
        addObject(gameManager, 577, 16);
    }
}
