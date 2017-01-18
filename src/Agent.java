import sun.applet.Main;

import java.util.*;

/**
 * Created by Gaëtan on 12/12/2016.
 */
public class Agent extends Thread {
    static Random rnd = new Random();
    int coord_X;
    int coord_Y;
    Grille grille;
    int objectif_X;
    int objectif_Y;
    int r;
    int g;
    int b;
    int count_turn;
    boolean need_move;
    boolean token_armoire; // true : free , false : taken

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
        count_turn = 0;
        token_armoire = true;
        //initialise un tiroir à son nom
        armoire.put(this, new ArrayList<Message>());
    }

    public Agent(int x, int y, Grille grille, int obj_X, int obj_Y) {
        this(x, y, grille, obj_X, obj_Y, 150, obj_X*50, obj_Y*50);
    }

    private synchronized boolean move(int newX, int newY){
        if(grille.isIn(newX,newY)){
            Case newCase = grille.getCase(newX, newY);
            Case oldCase = grille.getCase(coord_X, coord_Y);
            if (newCase.contenu == null && newCase.getToken()) {
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

    private boolean getTokenArmoire(){
        if(token_armoire){
            token_armoire= false;
            return true;
        }
        return false;
    }

    private boolean releaseTokenArmoire(){
        token_armoire = true;
        return true;
    }

    public synchronized void envoiMessage(Agent dest, String cont, Performatif perf) {
        while(!getTokenArmoire());
        ArrayList<Message> tiroir = armoire.get(dest);
        tiroir.add(new Message(this, dest, 0, cont, perf));
        debug("Sent a message");
        releaseTokenArmoire();
    }

    public synchronized List<Message> lectureMessages() {
        while(!getTokenArmoire());
        debug("Red new messages");
        List<Message> ret = new ArrayList<>(armoire.get(this));
        releaseTokenArmoire();
        return ret;
    }

    public synchronized void lireMessage(Message m) {
        while(!getTokenArmoire());
        m.lire();
        releaseTokenArmoire();
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
            List<Message> inbox = lectureMessages();
            for (Message mess : inbox) {
                if (mess != null && !mess.isRead) {
                    int messX = Integer.parseInt(mess.contenu.split(",")[0]);
                    int messY = Integer.parseInt(mess.contenu.split(",")[1]);
                    if (messX == coord_X && messY == coord_Y) {
                        //do stuff
                        need_move = true;
                    }
                    lireMessage(mess);
                }
            }

            if(need_move && nextX == coord_X && nextY == coord_Y){
                count_turn ++;
                if(count_turn < 3) {
                    ArrayList<Case> emptyNeigh = getEmptyNeighboors();
                    boolean moved = false;
                    Random r = new Random();
                    if(emptyNeigh.size() != 0 && r.nextInt(2) == 1) {
                        Case destination = emptyNeigh.get(r.nextInt(emptyNeigh.size()));
                        moved = move(destination.getCoord_X(), destination.getCoord_Y());
                    }
                    else {
                        int dir = r.nextInt(4);
                        switch (dir) {
                            case 0:
                                moved = move(coord_X + 1, coord_Y);
                                break;
                            case 1:
                                moved = move(coord_X - 1, coord_Y);
                                break;
                            case 2:
                                moved = move(coord_X, coord_Y + 1);
                                break;
                            case 3:
                                moved = move(coord_X, coord_Y - 1);
                                break;
                        }
                    }
                    if(moved)
                        count_turn = 0;
                }else{
                    count_turn = 0;
                    need_move = false;
                }
            }
            else
                if(coord_X != objectif_X || coord_Y != objectif_Y)
                    move(nextX, nextY);

            // si pas de message : bouge ou envoie messages

            // si message --> -
            try {
                Thread.sleep(10 + rnd.nextInt(90));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    synchronized void debug(String message) {
        /*MainWindow.DEBUG += "\n" + message;*/
    }

    public ArrayList<Case> getEmptyNeighboors(){
        ArrayList<Case> neighboors = new ArrayList<>();
        if(grille.getCase(coord_X+1,coord_Y) != null && grille.getCase(coord_X+1,coord_Y).getContenu() == null)
            neighboors.add(grille.getCase(coord_X+1,coord_Y));
        if(grille.getCase(coord_X-1,coord_Y) != null && grille.getCase(coord_X-1,coord_Y).getContenu() == null)
            neighboors.add(grille.getCase(coord_X-1,coord_Y));
        if(grille.getCase(coord_X,coord_Y+1) != null && grille.getCase(coord_X,coord_Y+1).getContenu() == null)
            neighboors.add(grille.getCase(coord_X,coord_Y+1));
        if(grille.getCase(coord_X,coord_Y-1) != null && grille.getCase(coord_X,coord_Y-1).getContenu() == null)
            neighboors.add(grille.getCase(coord_X,coord_Y-1));
        return neighboors;
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
