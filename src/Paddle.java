import acm.graphics.GRoundRect;

import java.awt.*;

public class Paddle extends GRoundRect {

    public Paddle(double x, double y, double width, double height, double arc) {
        super(x, y, width, height, arc);
        this.setFilled(true);
        this.setColor(Color.GRAY);
    }

    public void setPaddle(double x) {
        this.setLocation(x, this.getY());
    }
}
