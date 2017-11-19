import java.awt.*;

public class Item implements Cloneable {
    public Color color;
    public double x, y;

    public boolean hittest(double mx, double my){
        return false;
    }

    public void translate(double mx, double my){
        x += mx;
        y += my;
    }

    public void draw(Graphics2D g2) { }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
