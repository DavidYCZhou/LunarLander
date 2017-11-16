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
        LandingPad lp = new LandingPad();

        // want the background to be black
        setBackground(Color.lightGray);



    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
