import javax.swing.*;
import java.awt.*;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JTextArea textArea;
    private JPanel drawPanel;

    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;

    private static Grille grille;

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);

    }

    public static void main(String[] args) {
        grille = new Grille(WIDTH, HEIGHT);
        //Agent agt = new Agent(0, 0, grille, 1, 1, 0, 0, 0);

        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void paint(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(40, 40, 40, 40);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Case currentCase = grille.getCase(i, j);
                if (currentCase.contenu != null) {
                    g.setColor(new Color(currentCase.contenu.getR(), currentCase.contenu.getG(), currentCase.contenu.getB()));
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(i * 40, j * 40, 40, 40);
                }
                else {
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(i*40, j*40, 40, 40);
                }
            }
        }
    }
}
