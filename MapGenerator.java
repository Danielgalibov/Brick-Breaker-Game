import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

//mapGenerator is used to create the layout of the bricks and is just a means of organization and encapsulation
public class MapGenerator {
    public int map[][]; //this is just the 2D array used to make the brick
    public int brickWidth;
    public int brickHeight;
//the number of bricks is decided in the GamePlay.Java file, each "brick" in the 2D "map" array receives value 1
    //the value 1 can be changed in the future to make the bricks smaller on the first hit, instead of just being destroyed with one hit
    public MapGenerator(int row, int col){
        map = new int[row][col];
        for (int i=0; i< map.length; i++){
            for(int j=0; j<map[0].length; j++){
                map[i][j] = 1;
            }
        }

        brickWidth = 550/col;
        brickHeight = 150/row;
    }

    public void draw(Graphics2D g){
        for( int i=0; i< map.length; i++){
            for (int j=0; j< map[0].length; j++){
                if (map[i][j] > 0){
                    g.setColor(Color.white);
                    g.fillRect(j*brickWidth + 80, i* brickHeight +50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j*brickWidth + 80, i* brickHeight +50, brickWidth, brickHeight);

                }
            }
        }
    }
//the SetBrickValue function is used in the GamePlay file to set the value in the 2D array of the brick to zero when the "ball" intersects with a "brick"
    public void setBrickValue(int value, int row, int col){
        map[row][col] = value;
    }
}
