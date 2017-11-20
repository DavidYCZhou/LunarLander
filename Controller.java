import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller {
    GameModel gameModel;
    EditView ev;
    PlayView pv;
    double dragStartedX;
    double dragStartedY;
    MouseAdapter editViewClickMouseAdapter;
    MouseAdapter editViewMotionMouseAdapter;

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
    }

    public void addEditView(EditView v){
        this.ev = v;
    }
    public void addPlayView(PlayView v){
        this.pv = v;
    }

    public void updateAllViews(){
        gameModel.setChangedAndNotify();
    }
}
