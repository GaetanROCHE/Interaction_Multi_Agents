/**
 * Created by Gaëtan on 12/12/2016.
 */
public class Agent extends Thread {
    int coord_X;
    int coord_Y;
    Grille grille;
    int objectif_X;
    int objectif_Y;

    public Agent(int x, int y, Grille g, int obj_X, int obj_Y){
        coord_X = x;
        coord_Y = y;
        if(!grille.getCase(x, y).getToken())
            System.out.println("erreur de placement de l'agent");
        grille = g;
        objectif_X = obj_X;
        objectif_Y = obj_Y;
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
        if (coord_X - objectif_X > 0 && move('b')){}
        else if (coord_X - objectif_X < 0 && move('h')){}
        else if (coord_Y - objectif_Y > 0 && move('g')){}
        else if (coord_Y - objectif_Y < 0 && move('d')){}
    }

}
