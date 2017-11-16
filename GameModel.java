import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.undo.*;
import javax.vecmath.*;

public class GameModel extends Observable {

    // first one is landing pad
    public ArrayList<Item> items;
    public int selectedItemIndex = -1;

    public GameModel(int fps, int width, int height, int peaks) {
        ship = new Ship(60, width/2, 50);

        worldBounds = new Rectangle2D.Double(0, 0, width, height);

        // anonymous class to monitor ship updates
        ship.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                setChangedAndNotify();
            }
        });

        items = new ArrayList<Item>();
        items.add(new LandingPad());

        // add peaks
        double offset = (width + 30 - 20 * 30)/19 + 30;
        double begin = -15;
        for(int i = 0; i < 20; i++){
            items.add(new Peak((int)begin, height/2));
            begin += offset;
        }
    }

    // World
    // - - - - - - - - - - -
    public final Rectangle2D getWorldBounds() {
        return worldBounds;
    }

    Rectangle2D.Double worldBounds;


    // Ship
    // - - - - - - - - - - -

    public Ship ship;

    // Observerable
    // - - - - - - - - - - -

    // helper function to do both
    void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }


    public void selectItem(int x, int y) {
        selectedItemIndex = -1;
        for(int i = 0; i < items.size(); i ++){
            if(items.get(i).hittest(x, y)){
                selectedItemIndex = i;
                break;
            }
        }
    }
}



