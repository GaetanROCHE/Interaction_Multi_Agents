import javax.swing.*;
import java.awt.*;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JTextArea textArea;
    private JPanel drawPanel;

    private final int HEIGHT = 5;
    private final int WIDTH = 5;

    private static Grille grille;

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);

    }

    public static void main(String[] args) {
        grille = new Grille(WIDTH, HEIGHT);

        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void paint(Graphics g) {
        grille = new Grille(WIDTH, HEIGHT);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Case currentCase = grille.getCase(i, j);
                if (currentCase.contenu != null)
                    g.setColor(new Color(currentCase.getR(), currentCase.getG(), currentCase.getB()));
                    g.fillRect(i*40, j*40, 40, 40);
            }
        }
    }
}
