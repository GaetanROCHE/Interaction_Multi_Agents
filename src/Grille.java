import java.util.ArrayList;

/**
 * Created by Gaëtan on 12/12/2016.
 */
public class Grille {
    private final int taille_X;
    private final int taille_Y;
    static private Case[][] grille;

    public int getTaille_Y() {
        return taille_Y;
    }

    public int getTaille_X() {
        return taille_X;
    }

    public Grille(int x, int y){
        grille = new Case[x][y];
        for(int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                grille[i][j] = new Case(i,j);
            }
        }
        taille_X = x;
        taille_Y = y;
    }

    public Case getCase(int x, int y){
        if(this.isIn(x,y))
            return grille[x][y];
        return null;
    }

    public boolean isIn(int newX, int newY) {
        return newX < taille_X && newX >= 0 && newY < taille_Y && newY >= 0;
    }
}
