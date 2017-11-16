import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller {
    GameModel gameModel;
    EditView ev;
    PlayView pv;
    MouseAdapter editViewClickMouseAdapter;
    MouseAdapter editViewMotionMouseAdapter;

    Controller(GameModel gm){
        gameModel = gm;
        editViewClickMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameModel.selectItem(e.getX(), e.getY());
            }
        };
        editViewMotionMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                if(gameModel.selectedItemIndex == -1) return;
                // move selected item
                gameModel.items.get(gameModel.selectedItemIndex).translate(e.getX(), e.getY());
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
        ev.repaint();
        pv.repaint();
    }
}
