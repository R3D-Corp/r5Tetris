import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Main {
    public static JFrame window;
    public static Game game;

    public static void main(String[] args) {
        window = new JFrame("rDev | Tetris");
        game = new Game();
        int[] GRID_SIZE = game.GetGridSize();
        int CELL_SIZE = game.GetCellSize();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(GRID_SIZE[0] * CELL_SIZE, GRID_SIZE[1] * CELL_SIZE);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.setAlwaysOnTop(true);

        window.setContentPane(game);

        window.setVisible(true);
        window.addKeyListener((KeyListener) new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evenement) {
                if (evenement.getKeyCode() == 82) {
                    game.rotateCurrentBlock();
                }
            }
        });
    }
}
