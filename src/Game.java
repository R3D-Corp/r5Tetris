import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel {
    private final int CELL_SIZE = 24;
    private final int[][] grid = new int[15][30];

    private Component currentBlock = null;

    public TimePast time;
    public Block block;

    public enum Patterns {
        L,
    }

    private int[][] getNewPattern(Patterns currentPatern, int currentRotation) {
        int[][] results = new int[][] {};

        switch (currentPatern) {
            case L:
                if (currentRotation == 1) {
                    results = new int[][] { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 2 } };
                } else if (currentRotation == 2) {
                    results = new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 2, -1 } };
                } else if (currentRotation == 3) {
                    results = new int[][] { { 0, 0 }, { 0, -1 }, { 0, -2 }, { 0, -2 } };
                } else if (currentRotation == 4) {
                    results = new int[][] { { 0, 0 }, { -1, 0 }, { -2, 0 }, { -2, 1 } };
                }
                break;
            default:
                results = new int[0][0];
                break;
        }
        return results;
    }

    private class Block {
        private int x;
        private int y;
        private Color BLOCK_COLOR;
        private Color BLOCK_BORDER_COLOR;

        public Block(int x, int y, int[] color, int[] colorBorder) {
            super();
            this.x = x;
            this.y = y;
            if (color.length >= 3) {
                this.BLOCK_COLOR = new Color(color[0], color[1], color[2]);
            }
            if (colorBorder.length >= 3) {
                this.BLOCK_BORDER_COLOR = new Color(colorBorder[0], colorBorder[1], colorBorder[2]);
            }
        }

        public void draw(Graphics2D g, boolean createBorder) {
            g.setColor(BLOCK_COLOR);
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            if (createBorder) {
                g.setColor(BLOCK_BORDER_COLOR);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }

    }

    public List<Component> components = new ArrayList<>();

    public class Component {
        private Color color;
        private int currentRotation;
        private int[][] blocks;
        private int x;
        private int y;
        private boolean touched = false;
        private Patterns pattern;

        public Component(int x, int y, Color color, int currentRotation, int[][] blocks, Patterns pattern) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.currentRotation = currentRotation;
            this.blocks = blocks;
            this.pattern = pattern;

        }

        public int[][] getBlocks() {
            return blocks;
        }

        public Color getColor() {
            return color;
        }

        public int[] getCoords() {
            int[] coords = { x, y };
            return coords;
        }

        public int getRotation() {
            return currentRotation;
        }

        public void setRotation(int rotation) {
            this.currentRotation = rotation;
            this.blocks = getNewPattern(pattern, currentRotation);

        }

        public void setCoords(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Game() {
        super();
        this.setBackground(new Color(51, 51, 255));
        Color color = new Color(255, 0, 0); // Rouge
        Patterns pattern = Patterns.L;
        Component testComponent = new Component(5, 1, color, 1, new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 0, 2 } },
                pattern);
        components.add(testComponent);
        currentBlock = testComponent;
        System.out.println(currentBlock);
        time = new TimePast();
        Thread timePast = new Thread(time);
        timePast.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Grid //
        for (int gridX = 1; gridX < grid.length; gridX++) {
            for (int gridY = 1; gridY < grid[gridX].length; gridY++) {
                int[] backgroundColor = new int[] { 255, 255, 255 };
                int[] borderColor = new int[] { 0, 0, 0 };
                block = new Block(gridX * CELL_SIZE, gridY * CELL_SIZE, backgroundColor, borderColor);
                block.draw(g2, false);
            }
        }
        // Components //
        for (Component component : components) {
            int[][] blocks = component.getBlocks();
            int[] backgroundColor = new int[] { 255, 0, 0 };
            int[] borderColor = new int[] { 0, 0, 0 };

            int[] coords = component.getCoords();

            for (int i = 0; i < blocks.length; i++) {
                int currentX = coords[0] + blocks[i][0];
                int currentY = coords[1] + blocks[i][1];
                block = new Block(currentX * CELL_SIZE, currentY * CELL_SIZE, backgroundColor, borderColor);
                block.draw(g2, true);
            }
        }
    }

    public int GetCellSize() {
        return CELL_SIZE;
    }

    public int[] GetGridSize() {
        int[] gridSize = new int[] { grid.length, grid[0].length };
        return gridSize;
    }

    public void rotateCurrentBlock() {
        int newRotation = currentBlock.getRotation() + 1;
        if (newRotation == 5) {
            newRotation = 1;
        }
        currentBlock.setRotation(newRotation);
    }

    public void downCurrentBlock() {
        int[] coords = currentBlock.getCoords();
        currentBlock.setCoords(coords[0], coords[1] + 1);
    }

}
