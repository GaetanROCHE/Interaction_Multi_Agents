import java.util.Enumeration;

/**
 * Created by Gaëtan on 13/12/2016.
 */
public class Message {
    Agent emetor;
    Agent destinator;
    int id;
    String contenu;
    Enumeration<Performatif> pregoratif;
    boolean read;

    public Message(Agent emetor, Agent destinator, int id, String contenu, Enumeration<Performatif> pregoratif) {
        this.emetor = emetor;
        this.destinator = destinator;
        this.id = id;
        this.contenu = contenu;
        this.pregoratif = pregoratif;
        this.read = false;
    }
}
