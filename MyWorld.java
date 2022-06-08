import greenfoot.*;
import java.lang.Math;

public class MyWorld extends World
{    
    public MyWorld()
    {    
        super(1280, 720, 1); 
        prepare();
    }
    
    private void prepare()
    {
        //zufällig generierte Werte für die Terrain-Generation (sinus-kurve)
        int a = Greenfoot.getRandomNumber(40) + 80;
        int b = Greenfoot.getRandomNumber(500);
        float d = 0.25f;
        int c = 400;
        
        /*
         * - zuerst wird der Boden, welcher protentielle weiße Flächen abdecken soll der Welt hinzugefügt
         * - als Nächstes wird das Terrain generiert
         * - darauf wird der GameManager erstellt (und der Welt hinzugefügt)
         * - zum Schluss werden die Rohre und daraufhin die Spieler erstellt (und der Welt hinzugefügt)
         */
        
        Dirt dirt = new Dirt();
        addObject(dirt, 640, 670);
        
        for(int x = 0; x < 1280; x+=4) // es werden nur alle vier Pixel ground-tiles hinzugefügt, damit die erforderliche Rechenleistung kleiner ausfällt
        {
            Vector2 v =  new Vector2(x, (int)((Math.sin(Math.toRadians(d*(x + b))) * a) + c));
            ground g = new ground();
            addObject(g, x, v.Y + 150);
        }
        
        GameManager gm = new GameManager(a, b, c, d);
        addObject(gm,0,0);
                
        Barrel barrel0 = new Barrel();
        Player player0 = new Player(0, barrel0, new Vector2(130,540));

        Barrel barrel1 = new Barrel(); 
        Player player1 = new Player(1, barrel1, new Vector2(1150,540));
        
        addObject(player0,0,0);
        addObject(player1,0,0);
        
        addObject(barrel0, 0,0);
        addObject(barrel1, 0,0);
        
        //etwas verspäteter start ins spiel, damit keine Fehler durch zu langes drücken der Leertaste im Menü entstehen
        Greenfoot.delay(50);
    }
}
