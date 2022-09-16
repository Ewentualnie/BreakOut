import java.awt.*;

public interface Constants {
    int APPLICATION_WIDTH = 400;
    int APPLICATION_HEIGHT = 600;
    /**
     * paddle constants
     */
    double paddleWidth = 60;
    double paddleHeight = 10;
    double paddleArc = 8;

    /**
     * brick constants
     */
    int brickHeight = 8;
    int brickSeparation = 4;
    int offsetY = 70;
    int rowCount = 10;
    int brickCount = 10;
    Color red = Color.RED;
    Color orange = Color.ORANGE;
    Color yellow = Color.YELLOW;
    Color green = Color.GREEN;
    Color cyan = Color.CYAN;

    /**
     * ball constants
     */
    int ballRadius = 10;
    double speedModification = 0.4;
}