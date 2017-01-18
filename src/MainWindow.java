import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JTextArea textArea;
    private JPanel drawPanel;
    private JPanel solPanel;

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

        /*agents.add(new Agent(4, 0, grille, 0, 0));
        agents.add(new Agent(3, 0, grille, 0, 1));
        agents.add(new Agent(2, 0, grille, 0, 2));
        agents.add(new Agent(1, 0, grille, 0, 3));
        agents.add(new Agent(0, 0, grille, 0, 4));

        agents.add(new Agent(4, 1, grille, 1, 0));
        agents.add(new Agent(3, 1, grille, 1, 1));
        agents.add(new Agent(2, 1, grille, 1, 2));
        agents.add(new Agent(1, 1, grille, 1, 3));
        agents.add(new Agent(0, 1, grille, 1, 4));

        agents.add(new Agent(4, 2, grille, 2, 0));
        agents.add(new Agent(3, 2, grille, 2, 1));
        agents.add(new Agent(2, 2, grille, 2, 2));
        agents.add(new Agent(1, 2, grille, 2, 3));
        agents.add(new Agent(0, 2, grille, 2, 4));

        agents.add(new Agent(4, 3, grille, 3, 0));
        agents.add(new Agent(3, 3, grille, 3, 1));
        agents.add(new Agent(2, 3, grille, 3, 2));
        agents.add(new Agent(1, 3, grille, 3, 3));
        agents.add(new Agent(0, 3, grille, 3, 4));
        */

        double PROBA = 1;
        Random rnd = new Random();
        List<Point> taken = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++){
                if (rnd.nextDouble() < PROBA) {
                    boolean loop;
                    int x;
                    int y;
                    do {
                        x = rnd.nextInt(5);
                        y = rnd.nextInt(5);
                        loop = false;
                        for (Point2D p : taken)
                            if (p.getX() == x && p.getY() == y)
                                loop = true;
                    } while(loop);
                    if(taken.size() < 24) {
                        taken.add(new Point(x, y));
                        agents.add(new Agent(x, y, grille, i, j));
                    }
                }
            }



        //agents.add(new Agent(4, 4, grille, 4, 4, 255, 255, 255));
        agents.forEach(Agent::start);

        MainWindow dialog = new MainWindow();
        dialog.pack();

        Timer timer = new javax.swing.Timer(10, new ActionListener() {
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

        int OFFSETX2 = 430;
        int OFFSETY2 = 73;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                g.setColor(new Color(150, i*50, j*50));
                g.fillRect(OFFSETX2 + i * 80, OFFSETY2 + j * 80, 79, 79);
            }
        }

        textArea.append(DEBUG);
        DEBUG = "";
    }
}
