import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JTextArea textArea;
    private JPanel drawPanel;

    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;

    private static Grille grille;
    private static List<Agent> agents = new ArrayList<Agent>();

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);

    }

    public static void main(String[] args) {
        grille = new Grille(WIDTH, HEIGHT);

        agents.add(new Agent(0, 0, grille, 1, 1, 0, 0, 0));
        agents.add(new Agent(1, 1, grille, 2, 2, 0, 0, 0));
        agents.add(new Agent(2, 2, grille, 3, 3, 0, 0, 0));
        agents.add(new Agent(5, 5, grille, 4, 4, 0, 0, 0));

        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void paint(Graphics g) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Case currentCase = grille.getCase(i, j);
                if (currentCase.contenu != null) {
                    g.setColor(new Color(currentCase.contenu.getR(), currentCase.contenu.getG(), currentCase.contenu.getB()));
                    g.fillRect(i * 40, j * 40, 39, 39);
                }
                else {
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(i * 40, j * 40, 39, 39);
                }
            }
        }
    }
}
