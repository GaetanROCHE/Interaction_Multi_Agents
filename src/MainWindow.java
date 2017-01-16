import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JTextArea textArea;
    private JPanel drawPanel;

    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;

    public static String DEBUG;

    private static Grille grille;
    private static List<Agent> agents = new ArrayList<Agent>();

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);
    }

    public static void main(String[] args) {
        grille = new Grille(WIDTH, HEIGHT);

        agents.add(new Agent(0, 0, grille, 2, 2, 255, 0, 0));
        //agents.add(new Agent(1, 1, grille, 3, 3, 0, 255, 0));
        agents.add(new Agent(2, 2, grille, 0, 0, 0, 0, 255));
        agents.add(new Agent(1, 1, grille, 1, 1, 255, 255, 0));
        agents.add(new Agent(2, 1, grille, 0, 3, 0, 255, 255));
        agents.add(new Agent(3, 1, grille, 3, 0, 255, 0, 255));

        agents.add(new Agent(0, 1, grille, 4, 0, 200, 0, 255));
        agents.add(new Agent(0, 2, grille, 4, 1, 150, 0, 255));
        agents.add(new Agent(0, 3, grille, 4, 2, 100, 0, 255));
        agents.add(new Agent(0, 4, grille, 4, 4, 50, 0, 255));


        //agents.add(new Agent(4, 4, grille, 4, 4, 255, 255, 255));
        agents.forEach(Agent::start);

        MainWindow dialog = new MainWindow();
        dialog.pack();

        Timer timer = new javax.swing.Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dialog.repaint();
                agents.forEach(Agent::toString);
            }
        });
        timer.setRepeats(true);
        timer.start();

        dialog.setVisible(true);
        System.exit(0);
    }

    public void paint(Graphics g) {
        int OFFSETX = 16;
        int OFFSETY = 73;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Case currentCase = grille.getCase(i, j);
                if (currentCase.contenu != null) {
                    g.setColor(new Color(currentCase.contenu.getR(), currentCase.contenu.getG(), currentCase.contenu.getB()));
                    g.fillRect(OFFSETX + i * 80, OFFSETY + j * 80, 79, 79);
                }
                else {
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(OFFSETX + i * 80, OFFSETY + j * 80, 79, 79);
                }
            }
        }
        textArea.append(DEBUG);
        DEBUG = "";
    }
}
