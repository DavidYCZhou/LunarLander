import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

// the edit toolbar
public class ToolBarView extends JPanel implements Observer {

    GameModel gameModel;
    JButton undo = new JButton("Undo");
    JButton redo = new JButton("Redo");

    public ToolBarView(GameModel model) {
        gameModel = model;
        model.addObserver(this);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // prevent buttons from stealing focus
        undo.setFocusable(false);
        redo.setFocusable(false);

        add(undo);
        add(redo);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameModel.undo();
            }
        });
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameModel.redo();
            }
        });
        undo.setEnabled(gameModel.canUndo());
        redo.setEnabled(gameModel.canRedo());

    }


    @Override
    public void update(Observable o, Object arg) {
        undo.setEnabled(gameModel.canUndo());
        redo.setEnabled(gameModel.canRedo());
    }
}
