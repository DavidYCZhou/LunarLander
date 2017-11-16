import java.awt.*;

public class Item {
    public Color color;
    public int x, y;
    public boolean isSelected = false;

    public void select(){
        isSelected = true;
    }
    public void unselect(){
        isSelected = false;
    }

    public boolean hittest(double mx, double my){
        return false;
    }

    public void translate(double mx, double my){
        x += mx;
        y += my;
    }

    public void draw(Graphics2D g2) { }
}
