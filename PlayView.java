import javax.swing.*;
import javax.vecmath.Point2d;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

// the actual game view
public class PlayView extends JPanel implements Observer {

    GameModel gameModel;
    Controller controller;
    Polygon terrain;
    Rectangle landingPad;
    Rectangle ship;
    Rectangle world;
    public PlayView(GameModel model, Controller c) {
        gameModel = model;
        controller = c;
        gameModel.addObserver(this);
        // needs to be focusable for keylistener
        setFocusable(true);

        // want the background to be black
        setBackground(Color.BLACK);

        addKeyListener(c.playViewKeyAdapter);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int playScale = gameModel.playScale;
        Point2d translate = gameModel.ship.position ;
        double translateX = - translate.x * playScale + getWidth() / 2;
        double translateY = - translate.y * playScale + getHeight() / 2;
        // draw world
        g2.setColor(Color.lightGray);
        world = new Rectangle((int)translateX, (int)translateY , 700 * playScale, 200 * playScale);
        g2.fillRect((int)translateX, (int)translateY , 700 * playScale, 200 * playScale);

        // draw terrain
        int[] terrainX = new int[22];
        int[] terrainY = new int[22];

        terrainX[0] = (int) translateX;
        terrainY[0] = (int) (200 * playScale + translateY);
        for(int i = 1; i < gameModel.items.size(); i ++){
            Peak it = (Peak) gameModel.items.get(i);
            terrainX[i] = (int) ((it.x + it.translateX + it.radius)*playScale + translateX);
            terrainY[i] = (int) ((it.y + it.translateY + it.radius)*playScale + translateY);
        }
        terrainX[21] =  (int) (700 * playScale + translateX);
        terrainY[21] =  (int) (200 * playScale + translateY);

        terrain = new Polygon(terrainX, terrainY, 22);
        g2.setColor(Color.DARK_GRAY);

        g2.fillPolygon(terrain);

        // draw landing pad
        LandingPad lp = (LandingPad) gameModel.items.get(0);
        landingPad = new Rectangle((int)((lp.x + lp.translateX)* playScale + translateX),
                (int)((lp.y + lp.translateY) * playScale + translateY) ,
                lp.width * playScale ,
                lp.height * playScale);
        if(gameModel.gameStatus == 2) {
            g2.setColor(Color.GREEN);
        }else{
            g2.setColor(lp.color);
        }
        g2.fillRect(landingPad.x, landingPad.y, landingPad.width, landingPad.height);

        // draw ship in the end
        g2.setColor(Color.BLUE);
        ship = new Rectangle(getWidth()/2 - 15, getHeight()/2 - 15, 30, 30);
        g2.fillRect(getWidth()/2 - 15, getHeight()/2 - 15 , 30, 30);

        // checking game status
        if(terrain.intersects(ship)) gameModel.crash();
        if(!world.contains(ship)) gameModel.crash();
        if(ship.intersects(landingPad)){
            if(gameModel.ship.getSpeed() <= gameModel.ship.getSafeLandingSpeed()){
                gameModel.land();
            }else{
                gameModel.crash();
            }

        }

    }

}
