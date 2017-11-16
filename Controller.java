import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller {
    GameModel gameModel;
    MouseAdapter editViewMouseAdapter;

    Controller(GameModel gm){
        gameModel = gm;
        editViewMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseDragged(MouseEvent e){
                super.mouseDragged(e);
            }
        };
    }
}
