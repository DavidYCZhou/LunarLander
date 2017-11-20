import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.undo.*;
import javax.vecmath.*;

public class GameModel extends Observable {

    private UndoManager undoManager;
    // first one is landing pad
    ArrayList<Item> items;
    ArrayList<Item> backUp;
    int selectedItemIndex = -1;
    final int playScale = 3;
    final int originalScale = 1;
    // 0: playing, 1: crashed, 2: safe, 3: pause
    int gameStatus = 0;
    private boolean paused = false;

    public GameModel(int fps, int width, int height, int peaks) {
        undoManager = new UndoManager();
        ship = new Ship(60, width/2, 50);

        worldBounds = new Rectangle2D.Double(0, 0, width, height);

        // anonymous class to monitor ship updates
        ship.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                setChangedAndNotify();
            }
        });

        pause();
        items = new ArrayList<Item>();
        items.add(new LandingPad());

        // add peaks
        double offset = (width - 19 * 30)/18 + 30;
        double begin = -15;
        for(int i = 0; i < 19; i++){
            items.add(new Peak(begin, height/2));
            begin += offset;
        }
        items.add(new Peak(width - 15, height/2));
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

    public boolean canUndo() {
        return undoManager.canUndo();
    }

    public boolean canRedo() {
        return undoManager.canRedo();
    }

    public void undo() {
        if (canUndo())
            undoManager.undo();
    }

    public void redo() {
        if (canRedo())
            undoManager.redo();
    }

    public void rememberStartState() {
        backUp = deepCopyState(items);
    }

    public void addUndoableEdit() {
        if(backUp == null || items == null) return;
        UndoableEdit undoableEdit = new AbstractUndoableEdit(){
            final ArrayList<Item> oldItems = deepCopyState(backUp);
            final ArrayList<Item> newItems = deepCopyState(items);

            public void redo() throws CannotRedoException{
                super.redo();
                items = newItems;
                setChangedAndNotify();
            }

            public void undo() throws CannotUndoException{
                super.undo();
                items = oldItems;
                setChangedAndNotify();
            }

        };
        undoManager.addEdit(undoableEdit);
        setChangedAndNotify();
    }

    public ArrayList<Item> deepCopyState(ArrayList<Item> items) {
        if(items == null) return null;
        ArrayList<Item> result = new ArrayList<Item>();
        try{
            for(Item it: items){
                result.add((Item)it.clone());
            }
        }catch(CloneNotSupportedException e){
            System.out.println("Clone Error!");
        }
        return result;
    }

    public void pause() {
        paused = !paused;
        gameStatus = paused? 3: 0;
        ship.setPaused(paused);
    }

    public void crash() {
        gameStatus = 1;
        ship.setPaused(true);
        setChangedAndNotify();
    }

    public void land() {
        gameStatus = 2;
        ship.setPaused(true);
        setChangedAndNotify();
    }
}



