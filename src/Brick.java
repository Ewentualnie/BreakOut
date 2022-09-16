import acm.graphics.GRect;

public class Brick extends GRect implements Constants {
    public int hits;
    public int points;

    public Brick(double x, double y, double width, int height, int row) {
        super(x, y, width, height);
        this.setFilled(true);
        setColor(row);
        setHits(row);
    }

    private void setHits(int row) {
        switch (row % 10) {
            case 0, 1 -> points = hits = 5;
            case 2, 3 -> points = hits = 4;
            case 4, 5 -> points = hits = 3;
            case 6, 7 -> points = hits = 2;
            case 8, 9 -> points = hits = 1;
        }
    }

    private void setColor(int row) {
        switch (row % 10) {
            case 0, 1 -> this.setColor(red);
            case 2, 3 -> this.setColor(orange);
            case 4, 5 -> this.setColor(yellow);
            case 6, 7 -> this.setColor(green);
            case 8, 9 -> this.setColor(cyan);

        }
    }
}
