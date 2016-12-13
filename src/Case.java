/**
 * Created by Gaëtan on 12/12/2016.
 */
public class Case {
    final int coord_X;
    final int coord_Y;
    boolean token;
    Agent contenu;

    public Case(int x, int y) {
        coord_X = x;
        coord_Y = y;
        token = true;
        contenu = null;
    }

    boolean getToken(){
        if(token){
            token = false;
            return true;
        }
        return false;
    }

    boolean releaseToken(){
        return true;
    }

    public boolean videCase(){
        return true;
    }

    public boolean rempliCase(Agent a){
        return true;
    }
}
