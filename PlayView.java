import javax.swing.*;
import javax.vecmath.Point2d;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

// the actual game view
public class PlayView extends JPanel implements Observer {

    GameModel gameModel;
    public PlayView(GameModel model) {
        gameModel = model;
        gameModel.addObserver(this);
        // needs to be focusable for keylistener
        setFocusable(true);

        // want the background to be black
        setBackground(Color.BLACK);


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
        int originalScale = gameModel.originalScale;
        Point2d translate = gameModel.ship.position ;
        double translateX = - translate.x * playScale + getWidth() / 2;
        double translateY = - translate.y * playScale + getHeight() / 2;
        // draw world
        g2.setColor(Color.lightGray);
        g2.fillRect(0, 0, (int)gameModel.worldBounds.width * playScale, (int)gameModel.worldBounds.height * playScale);

        // draw terrain
        int[] terrainX = new int[22];
        int[] terrainY = new int[22];

        terrainX[0] = 0;
        terrainY[0] = (int) (200 * playScale + translateY);
        for(int i = 1; i < gameModel.items.size(); i ++){
            Peak it = (Peak) gameModel.items.get(i);
            terrainX[i] = (int) ((it.x + it.radius)*playScale + translateX);
            terrainY[i] = (int) ((it.y + it.radius)*playScale + translateY);
        }
        terrainX[21] =  (int) (700 * playScale + translateX);
        terrainY[21] =  (int) (200 * playScale + translateY);

        g2.setColor(Color.DARK_GRAY);
        g2.fillPolygon(terrainX, terrainY, 22);

        // draw landing pad
        LandingPad lp = (LandingPad) gameModel.items.get(0);
        lp.scale(playScale);
        lp.translate(translateX, translateY);
        lp.draw(g2);
        lp.scale(originalScale);

        // draw ship in the end
        g2.setColor(Color.BLUE);
        g2.fillRect(getWidth()/2 - 15, getHeight()/2 - 15 , 30, 30);
    }

}
