package trumpler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.FontMetrics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{

    private final int WIDTH = 1000;
    private final int HEIGHT = 700;
    private final int D_SIZE = 40;
    private final int All_Spaces = (WIDTH * HEIGHT)/(int)(Math.pow(D_SIZE,2));

    private final int x[] = new int [All_Spaces];
    private final int y[] = new int [All_Spaces];
    private int ties;
    private int fakeNews_X;
    private int fakeNews_Y;
    private int dir = 3;
    private boolean game = true;
    private Image head;
    private Image fakeNews;
    private Image tieB;
    private Image tieM;
    private Image tieE;
    private Timer timer;

    public Board(){
        setBackground(Color.black);
        addKeyListener(new Input());
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loadImages();
        startGame();
    }

    private void loadImages(){
        ImageIcon imageLoader = new ImageIcon("head.png");
        head = imageLoader.getImage();
        imageLoader = new ImageIcon("fakenews.png");
        fakeNews = imageLoader.getImage();
        imageLoader = new ImageIcon("tieBegin.png");
        tieB = imageLoader.getImage();
        imageLoader = new ImageIcon("tieMid.png");
        tieM = imageLoader.getImage();
        imageLoader = new ImageIcon("tieEnd.png");
        tieE = imageLoader.getImage();
    }

    private void startGame(){
        ties = 3;
        for (int i = 0; i < ties; i++){
            x[i] = 200 - (i * 40);
            y[i] = 200;
        }
        findFakeNews();
        timer = new Timer(150, this);
        timer.start();
    }

    private void findFakeNews(){
        int loc = (int)(Math.random() * 20);
        fakeNews_X = loc * D_SIZE;
        loc = (int)(Math.random() * 20);
        fakeNews_Y = loc * D_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (game){
            checkFakeNews();
            checkCollision();
            move();
        }

        repaint();
    }

    private void checkFakeNews(){
           if((x[0] == fakeNews_X) && (y[0] == fakeNews_Y)){
               ties++;
               findFakeNews();
           } 
    }

    private void checkCollision(){
        for(int i = ties; i > 2; i--){
            if((x[0] == x[i]) && (y[0] == y[i]))
                game = false;
        }
        if ((y[0] >= HEIGHT) || (y[0] < 0) || (x[0] >= WIDTH) || (x[0] < 0))
            game = false;
        if (!game)
            timer.stop();
    }

    private void move(){
        for (int i = ties; i > 0; i--){
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        switch (dir){
            case 1: x[0] -= D_SIZE;
                    break;
            case 2: y[0] -= D_SIZE;
                    break;
            case 3: x[0] += D_SIZE;
                    break;
            case 4: y[0] += D_SIZE;
                    break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g){
        if(game){
            g.drawImage(fakeNews, fakeNews_X, fakeNews_Y, this);
            g.drawImage(head, x[0], y[0], this);
            g.drawImage(tieB, x[1], y[1], this);
            for (int i = 2; i < ties; i++)
                g.drawImage(tieM, x[i], y[i], this);
            g.drawImage(tieE,x[ties], y[ties], this);
            Toolkit.getDefaultToolkit().sync();
        } else {
            String say = "Game Over";
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics met = getFontMetrics(small);
    
            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(say, (WIDTH - met.stringWidth(say))/2, HEIGHT / 2);
        }
    }

    private class Input extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && (Math.abs(dir - 1) != 2))
                dir = 1;
            if ((key == KeyEvent.VK_UP) && (Math.abs(dir - 2) != 2))
                dir = 2;
            if ((key == KeyEvent.VK_RIGHT) && (Math.abs(dir - 3) != 2))
                dir = 3;
            if((key == KeyEvent.VK_DOWN) && (Math.abs(dir - 4) != 2))
                dir = 4;
            if((key == KeyEvent.VK_SPACE) && !game){
                startGame();
                game = true;
                dir = 3;
            }
                
        }
    }
}
