import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//JPanel is a GUI component that functions as a container to hold other components
//***Gameplay uses certain methods from JPanel and will be incorporated as a gui component in the final frame
//GamePlay inherits everything from JPanel and is used to hold components of the game such as ball, paddle, bricks, etc.
//ActionListener is implemented so that when an action event such as a key press occurs we can perform another event as a result
public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 320;
    private int ballposX = 120;
    private int ballposY = 350;

    //ball velocity
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    public GamePlay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);//since we are implementing the key lister interface we can just use keyword this
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);


        //Drawing map
        map.draw((Graphics2D) g);

        //Border
        g.setColor(Color.orange);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //Score
        g.setColor(Color.cyan);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score,59,30);

        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //the ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 20, 20);


        if (totalBricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won", 260, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        if(ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over", 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        g.dispose();

    }

    //below is the logic when the ball and paddle/brick intersect
    //ball is represented as a small rectangle

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play){
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }
            //map is the name of the 2D array in the MapGenerator object, and its also the name of the MapGenerator Instance in GamePlay.java
           A:  for(int i=0; i< map.map.length; i++){
                for(int j=0; j<map.map[0].length; j++){
                    //creates the sizes of the bricks
                    if(map.map[i][j] > 0){
                        int brickX = j* map.brickWidth +80;
                        int brickY = i* map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score +=5;
                        //if the ball insects with the paddle
                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            //gets the ball moving
            ballposX += ballXdir;
            ballposY += ballYdir;
            //if the ball hits the "wall"/boundaries
            if(ballposX < 0){ //left wall
                ballXdir = -ballXdir;
            }
            if(ballposY < 0){ // top/north wall
                ballYdir = -ballYdir;
            }
            if(ballposX > 670){ // right wall
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            } else{
                moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            //reset everything
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }
    private void moveRight() {
        play = true;
        playerX+=20;
    }
    private void moveLeft() {
        play = true;
        playerX -=20;
    }

}




