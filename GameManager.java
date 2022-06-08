import greenfoot.*;
import java.lang.Math;

public class GameManager extends Actor
{
    // wer ist am Zug?
    public int turn;
    
    //Liste an errechneten Koordinaten (Terrain)
    public Vector2[] positions = new Vector2[1280];
    public ground[] grounds = new ground[1280];
    
    //zufällige Parameter
    private int a, b ,c;
    private float d;
    
    public GameManager(int a, int b, int c, float d)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        
        //für jede X-Stelle wird die Y-Koordinate durch eine zufällige Sinus-Kurve erstellt
        for(int x = 0; x < 1280; x++)
        {
            positions[x] = new Vector2(x, (int)((Math.sin(Math.toRadians(d*(x + b))) * a) + c));
        }
    }
    
    public void NextTurn()
    {
        turn = 1-turn; // 0->1; 1->0
        for(Player p : getWorld().getObjects(Player.class))
        {
            // jeder Spieler wird benachrichtigt, wer nun am Zug ist
            if(p.id == turn){
                p.myTurn = true;   
            }else{
                p.myTurn = false;
            }
        }
        
        //um ein direktes schießen bei zu lang gedrückter Leertaste zu vermeiden, wird das Spiel für einen kurzen Moment angehalten
        Greenfoot.delay(10);
    }
    
    //auf anfrage wird der Funktionswert zu einer gegeben X-Stelle zurückgegeben
    public int getYPos(int x)
    {
        return positions[x].Y;
    }
    //auf anfrage wird der Steigungswinkel der Funktion an der Stelle x errechnet und weitergegeben
    public int getRot(int x)
    {
        double derivation = a * d * Math.cos(Math.toRadians(d*x + b*d)); // ableitung
        double atan = Math.atan(Math.toRadians(derivation)); //arcus tangens der Ableitung
        
        int angle = (int)(50 * atan); // konvertierung in eine ganze Zahl
        
        return angle;
    }
}
