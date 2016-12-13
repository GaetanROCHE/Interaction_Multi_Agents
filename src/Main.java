import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");

        MainWindow mw = new MainWindow();
        mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mw.setSize(360, 300);
        mw.setVisible(true);
    }
}
