import java.awt.*;
import java.util.Random;
import java.lang.Math;

public class Peak extends Item {
    int radius = 15;

    public Peak(int x, int y){
        this.x = x;
        Random rand = new Random();
        this.y = y + (rand.nextInt(80) + 1);
    }

    @Override
    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.drawOval(x, y, radius * 2, radius * 2);
    }
    @Override
    public boolean hittest(double mx, double my){
        int centerX = x + radius;
        int centerY = y + radius;
        return distance2D(mx, centerX, my, centerY) <= radius;
    }

    @Override
    public void translate(double mx, double my){
        y += my;
    }

    private double distance2D(double p1x, double p2x, double p1y, double p2y){
        return Math.sqrt(Math.pow(p1x - p2x, 2) + Math.pow(p1y - p2y, 2));
    }

}
