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
        this.controller = controller;

        // want the background to be light gray
        setBackground(Color.lightGray);
        addMouseListener(controller.editViewClickMouseAdapter);
        addMouseListener(controller.editViewMotionMouseAdapter);

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // draw landing pad
        LandingPad landingPad = (LandingPad) gameModel.items.get(0);
        landingPad.draw(g2);

        // draw terrain

    }
}
