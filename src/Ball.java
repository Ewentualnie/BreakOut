import acm.graphics.GOval;

import java.awt.*;
import java.util.random.RandomGenerator;

public class Ball extends GOval implements Constants {
    private double vectorX;
    private double vectorY;
    public int power;
    private boolean alive = true;

    public Ball(double x, double y) {
        super(x, y, ballRadius * 2, ballRadius * 2);
        this.setFilled(true);
        setColor();
        setDirection();
    }

    public double getVectorX() {
        return vectorX;
    }

    public double getVectorY() {
        return vectorY;
    }

    public void setVectorX(double vectorX) {
        this.vectorX = vectorX;
    }

    public void setVectorY(double vectorY) {
        this.vectorY = vectorY;
    }

    public void xIncreaseYDecrease() {
        vectorX = vectorX > 0 ? vectorX + speedModification : vectorX - speedModification;
        vectorY = vectorY > 0 ? vectorY - speedModification : vectorY + speedModification;
    }

    public void xDecreaseYInrease() {
        vectorX = vectorX > 0 ? vectorX - speedModification : vectorX + speedModification;
        vectorY = vectorY > 0 ? vectorY + speedModification : vectorY - speedModification;
    }

    private void setDirection() {
        RandomGenerator randomGen = RandomGenerator.getDefault();
//        vectorX = randomGen.nextDouble(0.3, 0.9);
//        if (randomGen.nextBoolean()) {
//            vectorX = -vectorX;
//        }
        vectorX = 5.;
        vectorY = randomGen.nextDouble(0.9, 1.2);
    }

    public double[] getCenter() {
        return new double[]{this.getX() + ballRadius, this.getY() + ballRadius};
    }

    public void changeVectorX() {
        this.vectorX = -vectorX;
    }

    public void changeVectorY() {
        this.vectorY = -vectorY;
    }

    public void setColor() {
        int ball = (int) (Math.random() * 3);
//        int ball = 1;
        switch (ball) {
            case 0 -> {
                this.setColor(Color.GREEN);
                power = 1;
            }
            case 1 -> {
                this.setColor(Color.YELLOW);
                power = 2;
            }
            case 2 -> {
                this.setColor(Color.RED);
                power = 3;
            }
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void delete() {
        alive = false;
    }
}