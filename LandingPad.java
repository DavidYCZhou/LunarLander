import java.awt.*;

public class LandingPad extends Item{

    int width = 40;
    int height = 10;

    public LandingPad(){
        // init at 330 100
        this.x = 330;
        this.y = 100;
        this.color = Color.RED;
    }
    @Override
    public void translate(double mx, double my){
        double newX = x + mx + translateX;
        double newY = y + my + translateY;
        if(newX + width >= 700  || newY + height >= 200 || newX <= 0 || newY <= 0) return;
        translateX += mx;
        translateY += my;

    }

    @Override
    public void draw(Graphics2D g2){
        g2.setColor(color);
        g2.fillRect((int) (x * scale + translateX), (int) ((y * scale)  + translateY), width * scale, height * scale);
    }

    @Override
    public boolean hittest(double mx, double my){
        double offsetx = mx - x - translateX;
        double offsety = my - y - translateY;
        return offsetx <= 40 && offsetx >= 0 && offsety <= 10 && offsety >= 0;
    }

}
