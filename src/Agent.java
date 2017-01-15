import sun.applet.Main;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;

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
    boolean need_move;

    static HashMap<Agent, ArrayList<Message>> armoire = new HashMap<>();

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
        need_move = false;

        //initialise un tiroir à son nom
        armoire.put(this, new ArrayList<Message>());
    }

    private boolean move(int newX, int newY){
        if(grille.isIn(newX,newY)){
            Case newCase = grille.getCase(newX, newY);
            Case oldCase = grille.getCase(coord_X, coord_Y);
            if (newCase.getToken()) {
                //System.out.println("Taking token of case : " + newCase.getCoord_X() + "," + newCase.getCoord_Y());
                oldCase.videCase();
                oldCase.releaseToken();
                coord_X = newX;
                coord_Y = newY;
                newCase.rempliCase(this);
                need_move = false;
                return true;
            }
            else{
                try {
                    this.envoiMessage(newCase.getContenu(), newCase.getCoord_X() + "," + newCase.getCoord_Y(), Performatif.REQUEST);
                }catch(Exception ignored){}
            }
        }
        return false;
    }

    public synchronized void envoiMessage(Agent dest, String cont, Performatif perf) {
        ArrayList<Message> tiroir = armoire.get(dest);
        tiroir.add(new Message(this, dest, 0, cont, perf));
        debug("Sent a message");
    }

    public synchronized ArrayList<Message> lectureMessages() {
        debug("Red new messages");
        return armoire.get(this);
    }

    @Override
    public void run(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        while(running) {
            int nextX = coord_X;
            int nextY = coord_Y;
            if(Math.pow(coord_X - objectif_X, 2) > Math.pow(coord_Y - objectif_Y, 2)){
                if (coord_X - objectif_X > 0 ){//on essaie d'aller à gauche
                    if(grille.getCase(nextX - 1, nextY).getContenu() == null){
                        nextX--;
                    }
                }
                else if (coord_X != objectif_X){
                    if(grille.getCase(nextX + 1, nextY).getContenu() == null){
                        nextX++;
                    }
                }
            }
            else{
                if (nextX == coord_X && coord_Y - objectif_Y > 0 ){//on essaie d'aller en haut
                    if(grille.getCase(nextX, nextY - 1).getContenu() == null){
                        nextY--;
                    }
                }
                else if (coord_X != objectif_X){
                    if(grille.getCase(nextX, nextY + 1).getContenu() == null){
                        nextY++;
                    }
                }
            }
            // lecture messages
            ArrayList<Message> inbox = lectureMessages();
            for (Message mess : inbox ) {
                if (!mess.isRead) {
                    int messX = Integer.parseInt(mess.contenu.split(",")[0]);
                    int messY = Integer.parseInt(mess.contenu.split(",")[1]);
                    if (messX == coord_X && messY == coord_Y) {
                        //do stuff
                        need_move = true;
                    }
                }
            }

            if(need_move && nextX == coord_X && nextY == coord_Y){
                Random r = new Random();
                int dir = r.nextInt(4);
                switch(dir){
                    case 0 :
                        move(coord_X + 1,coord_Y);
                        break;
                    case 1 :
                        move(coord_X - 1,coord_Y);
                        break;
                    case 2 :
                        move(coord_X,coord_Y + 1);
                        break;
                    case 3 :
                        move(coord_X,coord_Y - 1);
                        break;
                }
            }
            else
                if(coord_X != objectif_X || coord_Y != objectif_Y)
                    move(nextX, nextY);

            // si pas de message : bouge ou envoie messages

            // si message --> -
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    synchronized void debug(String message) {
        MainWindow.DEBUG += "\n" + message;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() { return b; }

    public String toString() {
        return "pos : (" + coord_X + "," + coord_Y + ")";
    }
}
