import greenfoot.*;

public class MenuWorld extends World
{
    public MenuWorld()
    {    
        super(1280, 720, 1); 
        prepare();
    }

    private void prepare()
    {
        Menu menu = new Menu();
        addObject(menu,0,0);
    }
}
