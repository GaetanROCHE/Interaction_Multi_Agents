import java.util.Enumeration;

/**
 * Created by Gaëtan on 13/12/2016.
 */
public class Message {
    Agent emetteur;
    Agent destinateur;
    int id;
    String contenu;
    Enumeration<Performatif> performatif;
    boolean isRead;

    public Message(Agent emetor, Agent destinator, int id, String contenu, Enumeration<Performatif> pregoratif) {
        this.emetteur = emetor;
        this.destinateur = destinator;
        this.id = id;
        this.contenu = contenu;
        this.performatif = pregoratif;
        this.isRead = false;
    }
}
