import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gaëtan on 12/12/2016.
 */
public class Agent extends Thread {
    int coord_X;
    int coord_Y;
    Grille grille;
    int objectif_X;
    int objectif_Y;
    int r;
    int g;
    int b;
    HashMap<Agent, ArrayList<Message>> messages;
    private boolean running = true;

    public Agent(int x, int y, Grille grille, int obj_X, int obj_Y, int red, int green, int blue){
        coord_X = x;
        coord_Y = y;
        if(!grille.getCase(x, y).getToken())
            System.out.println("erreur de placement de l'agent");
        else
            grille.getCase(x,y).rempliCase(this);
        this.grille = grille;
        objectif_X = obj_X;
        objectif_Y = obj_Y;
        this.r = red;
        this.g = green;
        this.b = blue;
        messages = new HashMap<>();
    }

    private boolean move(char c){
        int newX = coord_X, newY = coord_Y;
        switch(c) {
            case 'h':
                newY++;
                break;
            case 'b':
                newY--;
                break;
            case 'g':
                newX--;
                break;
            case 'd':
                newX++;
                break;
            default:
                System.out.println("error unacceptable move");
                break;
        }
        if(grille.isIn(newX,newY)){
            Case newCase = grille.getCase(newX, newY);
            Case oldCase = grille.getCase(coord_X, coord_Y);
            if (newCase.getToken()) {
                oldCase.videCase();
                oldCase.releaseToken();
                coord_X = newX;
                coord_Y = newY;
                newCase.rempliCase(this);
                return true;
            }
        }
        return false;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        while(running) {
            System.out.println("bouge");
            if (coord_Y - objectif_Y > 0 && move('b')) {
                System.out.println("deplace en bas");
            } else if (coord_Y - objectif_Y < 0 && move('h')) {
                System.out.println("deplace en haut");
            } else if (coord_X - objectif_X > 0 && move('g')) {
                System.out.println("deplace a gauche");
            } else if (coord_X - objectif_X < 0 && move('d')) {
                System.out.println("deplace a droite");
            } else {
                System.out.println("deplace pas");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public String toString() {
        return "pos : (" + coord_X + "," + coord_Y + ")";
    }
}
