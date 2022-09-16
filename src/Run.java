import acm.graphics.GObject;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.event.MouseEvent;

public class Run extends WindowProgram implements Constants {

    Paddle paddle;
    int bricksCount;
    int points;

    public void run() {
        waitForClick();
        addMouseListeners();
        paddle = new Paddle(100, getHeight() - 100, paddleWidth, paddleHeight, paddleArc);
        add(paddle);
        WallOfBricks wall = new WallOfBricks(getWidth());
        drawWall(wall);
        bricksCount = wall.getCount();
        points = 0;
    }

    private void drawWall(WallOfBricks wall) {
        for (Brick brick : wall) {
            add(brick);
        }
    }

    private Thread gameObject(double x, double y) {
        Ball ball = new Ball(x, y);
        add(ball);
        return new Thread(() -> animation(ball));
    }

    private void animation(Ball ball) {
        while (ball.isAlive()) {
            ball.move(ball.getVectorX(), ball.getVectorY());
            borderReflections(ball);
//            paddleReflections(ball);
            destroyBrick(ball);
            pause(10);
        }
    }

    private void destroyBrick(Ball ball) {
        double[] point = getCollidingObject(ball);
        GObject object = getElementAt(point[0], point[1]);
        if (object instanceof Brick) {
            setReflectionFromBrick(ball, point);
            ((Brick) object).hits -= ball.power;
            if (((Brick) object).hits <= 0) {
                remove(object);
                bricksCount--;
                points += ((Brick) object).points;
            }
        }


    }

    private void setReflectionFromBrick(Ball ball, double[] point) {
        if (isDiagonalCollision(ball, point)) {
            ball.changeVectorX();
            ball.changeVectorY();
        } else if ((isUpperRightVector(ball) && isLeftSectorCollision(ball, point) && !xVectorHigher(ball)) ||
                (isBottomLeftVector(ball) && isUpperSectorCollision(ball, point) && xVectorHigher(ball))) {
            System.out.println("first type");
            ball.xDecreaseYInrease();
        } else if ((isUpperLeftVector(ball) && isRightSectorCollision(ball, point) && !xVectorHigher(ball)) ||
                (isBottomRightVector(ball) && isUpperSectorCollision(ball, point) && xVectorHigher(ball))) {
            ball.xIncreaseYDecrease();
            System.out.println("second type");
        } else if ((isBottomLeftVector(ball) && isRightSectorCollision(ball, point) && !xVectorHigher(ball)) ||
                (isUpperRightVector(ball) && isBottomSectorCollision(ball, point) && xVectorHigher(ball))) {
            ball.xIncreaseYDecrease();
            System.out.println("third type");
        } else if ((isBottomRightVector(ball) && isLeftSectorCollision(ball, point) && !xVectorHigher(ball)) ||
                (isUpperLeftVector(ball) && isBottomSectorCollision(ball, point) && xVectorHigher(ball))) {
            ball.xIncreaseYDecrease();
            System.out.println("forth type");
        } else if (isUpperSectorCollision(ball, point) || isBottomSectorCollision(ball, point)) {
            ball.changeVectorY();
        } else if (isLeftSectorCollision(ball, point) || isRightSectorCollision(ball, point)) {
            ball.changeVectorX();
        }
    }

    private boolean xVectorHigher(Ball ball) {
        return Math.abs(ball.getVectorX()) > Math.abs(ball.getVectorY());
    }

    private boolean isDiagonalCollision(Ball ball, double[] point) {
        return (isUpperLeftVector(ball) && isLeftSectorCollision(ball, point) && isUpperSectorCollision(ball, point)) ||
                (isUpperRightVector(ball) && isRightSectorCollision(ball, point) && isUpperSectorCollision(ball, point)) ||
                (isBottomLeftVector(ball) && isLeftSectorCollision(ball, point) && isBottomSectorCollision(ball, point)) ||
                (isBottomRightVector(ball) && isRightSectorCollision(ball, point) && isBottomSectorCollision(ball, point));
    }

    private boolean isUpperSectorCollision(Ball ball, double[] point) {
        return point[1] <= ball.getCenter()[1] - ballRadius / 2.;
    }

    private boolean isBottomSectorCollision(Ball ball, double[] point) {
        return point[1] >= ball.getCenter()[1] + ballRadius / 2.;
    }

    private boolean isLeftSectorCollision(Ball ball, double[] point) {
        return point[0] <= ball.getCenter()[0] - ballRadius / 2.;
    }

    private boolean isRightSectorCollision(Ball ball, double[] point) {
        return point[0] >= ball.getCenter()[0] + ballRadius / 2.;
    }

    private boolean isBottomRightVector(Ball ball) {
        return ball.getVectorX() > 0 && ball.getVectorY() > 0;
    }

    private boolean isBottomLeftVector(Ball ball) {
        return ball.getVectorX() < 0 && ball.getVectorY() > 0;
    }

    private boolean isUpperRightVector(Ball ball) {
        return ball.getVectorX() > 0 && ball.getVectorY() < 0;
    }

    private boolean isUpperLeftVector(Ball ball) {
        return ball.getVectorX() < 0 && ball.getVectorY() < 0;
    }

    private double[] getCollidingObject(Ball ball) {

        double[] pointOfCollision = new double[2];
        int endCount = 360;

        for (int angle = 0; angle < endCount; angle++) {

            double radiance = Math.toRadians(angle);
            double getPointX = (ball.getX() + ballRadius) + ballRadius * Math.cos(radiance);
            double getPointY = (ball.getY() + ballRadius) + ballRadius * Math.sin(radiance);

            if (getElementAt(getPointX, getPointY) instanceof Brick) {
                pointOfCollision[0] = getPointX;
                pointOfCollision[1] = getPointY;

            }
        }
        return pointOfCollision;
    }

    public void borderReflections(Ball ball) {
        if (ball.getCenter()[0] - ball.ballRadius <= 0 || ball.getCenter()[0] + ball.ballRadius >= getWidth()) {
            ball.changeVectorX();
        }
        if (ball.getCenter()[1] - ball.ballRadius <= 0) {
            ball.changeVectorY();
        }
        if (ball.getCenter()[1] - ball.ballRadius >= getHeight()) {
            ball.delete();
        }
    }

    private void paddleReflections(Ball ball) {
        for (int lengthX = 0; lengthX < paddle.getWidth(); lengthX++) {
            double pointX = paddle.getX() + lengthX;

            if (getElementAt(pointX, paddle.getY()) == ball) {
                ball.setLocation(ball.getCenter()[0] - ball.ballRadius, paddle.getY() - ball.ballRadius * 2);
                ball.changeVectorY();
            }
        }
        for (int lengthY = 1; lengthY < paddle.getHeight(); lengthY++) {
            double pointY = paddle.getY() + lengthY;

            if (getElementAt(paddle.getX(), pointY) == ball) {

                while (getElementAt(paddle.getX(), pointY) == ball) {
                    ball.setLocation(paddle.getX() - ball.ballRadius * 2, ball.getCenter()[1] - ball.ballRadius);
                }
                ball.changeVectorX();
            }
            if (getElementAt(paddle.getX() + paddle.getWidth(), pointY) == ball) {

                while (getElementAt(paddle.getX() + paddle.getWidth(), pointY) == ball) {
                    ball.setLocation(paddle.getX() + paddle.getWidth() + ball.ballRadius / 2.0, ball.getCenter()[1] - ball.ballRadius);
                }
                ball.changeVectorX();
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        Thread task = gameObject(e.getX(), e.getY());
        task.start();
    }

    public void mouseMoved(MouseEvent e) {
        if (e.getX() <= paddle.getWidth() / 2.0) {
            paddle.setPaddle(0);
        } else if (e.getX() > getWidth() - paddle.getWidth() / 2.0) {
            paddle.setPaddle(getWidth() - paddle.getWidth());
        } else {
            paddle.setPaddle(e.getX() - paddle.getWidth() / 2.0);
        }
    }
}
