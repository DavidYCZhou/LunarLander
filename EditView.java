import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

// the editable view of the terrain and landing pad
public class EditView extends JPanel implements Observer {

    GameModel gameModel;
    Controller controller;
    public EditView(GameModel model, Controller controller) {
        gameModel = model;
        model.addObserver(this);
        this.controller = controller;

        // want the background to be black
        setBackground(Color.black);

        addMouseListener(controller.editViewClickMouseAdapter);
        addMouseMotionListener(controller.editViewMotionMouseAdapter);

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
        g2.setColor(Color.lightGray);
        g2.fillRect(0, 0, (int)gameModel.worldBounds.width, (int)gameModel.worldBounds.height);

        // draw terrain
        int[] terrainX = new int[22];
        int[] terrainY = new int[22];
        // starting point
        terrainX[0] = 0;
        terrainY[0] = 200;
        for(int i = 1; i < gameModel.items.size(); i ++) {
            Peak it = (Peak) gameModel.items.get(i);
            terrainX[i] = (int)(it.x + it.translateX + it.radius);
            terrainY[i] = (int)(it.y + it.translateY + it.radius);
        }

        // ending point
        terrainX[21] = 700;
        terrainY[21] = 200;
        g2.setColor(Color.DARK_GRAY);
        g2.fillPolygon(terrainX, terrainY, 22);

        for(Item it: gameModel.items){
            it.draw(g2);
        }
    }
}
