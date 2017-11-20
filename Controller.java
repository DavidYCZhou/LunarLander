import javax.vecmath.Point2d;
import java.awt.event.*;

public class Controller {
    private GameModel gameModel;
    private double dragStartedX;
    private double dragStartedY;
    MouseAdapter editViewClickMouseAdapter;
    MouseAdapter editViewMotionMouseAdapter;

    KeyAdapter playViewKeyAdapter;

    Controller(GameModel gm){
        gameModel = gm;
        editViewClickMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameModel.selectItem(e.getX(), e.getY());
                if(gameModel.selectedItemIndex != -1){
                    gameModel.rememberStartState();
                    dragStartedX = e.getX();
                    dragStartedY = e.getY();
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    LandingPad l = (LandingPad) gameModel.items.get(0);
                    double offsetX = e.getX() - (l.x + l.width / 2);
                    double offsetY = e.getY() - (l.y + l.height / 2);
                    gameModel.rememberStartState();
                    l.translate(offsetX, offsetY);
                    gameModel.addUndoableEdit();
                    updateAllViews();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                gameModel.addUndoableEdit();
            }
        };

        editViewMotionMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                if(gameModel.selectedItemIndex == -1) return;
                // move selected item
                Item selected = gameModel.items.get(gameModel.selectedItemIndex);
                selected.translate(e.getX() - dragStartedX, e.getY() - dragStartedY);
                dragStartedX = e.getX();
                dragStartedY = e.getY();
                updateAllViews();
            }
        };

        playViewKeyAdapter = new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                char input = e.getKeyChar();
                switch (input){
                    case 'w':
                        if(gameModel.ship.getFuel() == 0) break;
                        gameModel.ship.thrustUp();
                        break;
                    case 'a':
                        if(gameModel.ship.getFuel() == 0) break;
                        gameModel.ship.thrustLeft();
                        break;
                    case 's':
                        if(gameModel.ship.getFuel() == 0) break;
                        gameModel.ship.thrustDown();
                        break;
                    case 'd':
                        if(gameModel.ship.getFuel() == 0) break;
                        gameModel.ship.thrustRight();
                        break;
                    case ' ':
                        if(gameModel.gameStatus == 2 ||
                                gameModel.gameStatus == 1){
                            // reset the game
                            gameModel.ship.reset(new Point2d(350, 50));
                            gameModel.gameStatus = 0;
                            gameModel.setChangedAndNotify();
                        }else{
                            gameModel.pause();
                        }
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        };



    }

    public void updateAllViews(){
        gameModel.setChangedAndNotify();
    }
}
