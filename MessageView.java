import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;


public class MessageView extends JPanel implements Observer {

    GameModel gameModel;
    // status messages for game
    JLabel fuel = new JLabel("fuel");
    JLabel speed = new JLabel("speed");
    JLabel message = new JLabel("message");

    public MessageView(GameModel model) {

        gameModel = model;
        model.addObserver(this);
        // want the background to be black
        setBackground(Color.BLACK);

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(fuel);
        add(speed);
        add(message);

        for (Component c: this.getComponents()) {
            c.setForeground(Color.WHITE);
            c.setPreferredSize(new Dimension(100, 20));
        }
    }


    @Override
    public void update(Observable o, Object arg) {

        double currentFuel = gameModel.ship.getFuel();
        double currentSpeed = gameModel.ship.getSpeed();
        if(currentFuel < 10) fuel.setForeground(Color.RED);
        if(currentSpeed < gameModel.ship.getSafeLandingSpeed()){
            speed.setForeground(Color.GREEN);
        }else{
            speed.setForeground(Color.white);
        }
        fuel.setText("fuel: " + currentFuel);
        speed.setText("speed: " + new DecimalFormat("#0.00").format(currentSpeed));
        if(gameModel.gameStatus == 1){
            message.setText("CRASH");
        }else if(gameModel.gameStatus == 2){
            message.setText("LANDED!");
        }else if(gameModel.gameStatus == 3){
            message.setText("Paused");
        }else{
            message.setText("");
        }

    }
}