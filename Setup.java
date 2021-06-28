import model.Rect;
import util.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @since 27.05.2021
 * @author Ronny Klotz
 * Application procedure with drawing function
 */
public class Setup extends JPanel implements ActionListener {

    private Rect[][] grid;

    private boolean editMode = false;
    private Timer timer;
    private static Setup INSTANCE;

    /**
     * Contructor for setup and basic configuration
     */
    public Setup() {

        this.setPreferredSize(new Dimension(Constant.WIDTH,Constant.HEIGHT));
        this.setBackground(new Color(0,0,0));
        this.setFocusable(true);
        this.addKeyListener(new CustomKeyAdapater());
        this.addMouseListener(new CustomMouseAdapter());
        INSTANCE = this;

        timer = new Timer(Constant.DELAY, this);
        timer.start();

        grid = new Rect[Constant.COLS][Constant.ROWS];
        randomGrid();
    }

    /**
     * Function to get the graphic that allow you to draw objects
     * @param g Graphics of the super class
     */
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Actual draw function which will be used to draw the objects
     * @param g Graphics of the super class
     */
    public void draw (Graphics g) {
        for (int i = 0; i < Constant.COLS; i++) {

            g.setColor(new Color(0,0,0));
            g.drawLine(i * Constant.RESOLUTION, 0, i * Constant.RESOLUTION, Constant.HEIGHT);

            for (int j = 0; j < Constant.ROWS ; j++) {

                g.setColor(new Color(0,0,0));
                g.drawLine(0, j * Constant.RESOLUTION, Constant.WIDTH, j * Constant.RESOLUTION);
                int x = i * Constant.RESOLUTION;
                int y = j * Constant.RESOLUTION;

                if (grid[i][j].state == 1) {
                    g.setColor(grid[i][j].getColor());
                    g.fillRect(x+3,y+3,Constant.RESOLUTION-6, Constant.RESOLUTION-6);
                }

            }
        }
    }

    private void randomGrid () {
        for (int i = 0; i < Constant.COLS; i++) {
            for (int j = 0; j < Constant.ROWS ; j++) {
                grid[i][j] = new Rect(ThreadLocalRandom.current().nextInt(2));
            }
        }
    }

    private void calculate () {
        Rect[][] next = new Rect[Constant.COLS][Constant.ROWS];
        for (int i = 0; i < Constant.COLS; i++) {
            for (int j = 0; j < Constant.ROWS ; j++) {
                next[i][j] = new Rect(0);
            }
        }

        for (int i = 0; i < Constant.COLS; i++) {
            for (int j = 0; j < Constant.ROWS ; j++) {

                int state = grid[i][j].state;


                int neighbors = countNeighbors(grid, i, j);

                if (state == 0 && neighbors == 3) {
                    next[i][j].state = 1;
                } else if (state == 1 && (neighbors < 2 || neighbors > 3)) {
                    next[i][j].state = 0;
                } else {
                    next[i][j].state = state;
                    Color color = grid[i][j].lifeTime();
                    if (color != null && state == 1) {
                        next[i][j].color = color;
                    }
                }

            }
        }

        grid = next;
    }

    private int countNeighbors(Rect[][] grid, int x, int y) {
        int sum = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {

                int col = (x + i + Constant.COLS) % Constant.COLS;
                int row = (y + j + Constant.ROWS) % Constant.ROWS;

                sum += grid[col][row].state;

            }
        }

        sum -= grid[x][y].state;

        return sum;
    }

    /**
     * Function that will update the Application window in DELAY time (16ms = ~60FPS)
     * @param e ignored ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        calculate();
        paint(INSTANCE.getGraphics());
    }

    private class CustomKeyAdapater extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                randomGrid();
                if (editMode) {
                    paint(INSTANCE.getGraphics());
                }
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (editMode) {
                    timer.restart();
                    editMode = false;
                } else {
                    timer.stop();
                    editMode = true;
                    Graphics g = INSTANCE.getGraphics();
                    g.setColor(new Color(255,255,255));
                    g.drawString("EDIT MODE",10,10);
                }
            } else if (editMode && e.getKeyCode() == KeyEvent.VK_DELETE) {
                for (int i = 0; i < Constant.COLS; i++) {
                    for (int j = 0; j < Constant.ROWS ; j++) {
                        if (grid[i][j].state == 0) {
                            continue;
                        }

                        grid[i][j].state = 0;
                        grid[i][j].color = new Color(0,0,0);
                    }
                }
                paint(INSTANCE.getGraphics());
            }
        }
    }
    private class CustomMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (!editMode) {
                return;
            }
            Point point = e.getLocationOnScreen();
            int x = point.x;
            int y = point.y;
            if (x > Constant.WIDTH || x < 0) {
                return;
            }
            if (y > Constant.HEIGHT || y < 0) {
                return;
            }

            int i = x / Constant.RESOLUTION;
            int j = y / Constant.RESOLUTION;

            if (e.getButton() == 1) {
                grid[i][j].state = 1;
                grid[i][j].color = new Color(255,255,255);
            } else if (e.getButton() == 3) {
                grid[i][j].state = 0;
            }
            paint(INSTANCE.getGraphics());
        }

    }
}
