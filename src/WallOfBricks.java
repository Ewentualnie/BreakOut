import java.util.ArrayList;

public class WallOfBricks extends ArrayList<Brick> implements Constants {
    private final double startX;
    private final double width;
    private int count = 0;
    int hits = 0;

    public WallOfBricks(double canvasWidth) {
        width = (canvasWidth - (brickCount - 1) * brickSeparation) / brickCount;
        startX = (canvasWidth - (brickCount * width + brickSeparation * (brickCount - 1))) / 2.0;
        createWall();
    }

    private void createWall() {
        double offsetX = brickSeparation + width;
        double offsetY = brickSeparation + brickHeight;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < brickCount; j++) {
                Brick brick = new Brick(startX + i * offsetX, Constants.offsetY + j * offsetY, width, brickHeight, j);
                this.add(brick);
                count++;
                this.hits += brick.hits;
            }
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
