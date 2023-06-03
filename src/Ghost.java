import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;

/**
 * Represents all ghost objects
 */

public abstract class Ghost extends GameObject {
    protected double speed;
    protected String ghostFileName;
    protected double direction;
    protected Point position;
    protected Point initialPosition;
    protected int deleted;
    protected int once = 0;
    protected String playerFile = "res/pac.png";
    private final String frenzyGhostFileName;

    /**
     * Constructor for Ghost
     * @param initialPosition
     */
    public Ghost(Point initialPosition) {
        this.position = initialPosition;
        this.frenzyGhostFileName = "res/ghostFrenzy.png";
        this.initialPosition = initialPosition;
    }
    private int isCollisionWWall(){
        return super.isCollision(walls1, position, ghostFileName, super.wallFileName);
    }

    /**
     * Determines if player collides with ghost entity
     * @param player coordinates
     * @return int value of index which acts as boolean
     */
    public int isCollisionWPlayer(Point player) {
        Image object1 = new Image(playerFile);
        Image object2 = new Image(ghostFileName);
        double heightObject1 = object1.getHeight();
        double widthObject1 = object1.getWidth();
        double heightObject2 = object2.getHeight();
        double widthObject2 = object2.getWidth();

        double object1X1 = player.x;
        double object1X2 = object1X1 + widthObject2;
        double object1Y1 = player.y;
        double object1Y2 = object1Y1 + heightObject2;

        boolean right = object1X2 < position.x;
        boolean left = (position.x + widthObject1) < object1X1;
        boolean above = (position.y + heightObject1) < object1Y1;
        boolean below = object1Y2 < position.y;

        if (!(left || right || above || below)) {
            return 1;
        }

    return -1;
    }

    /**
     * Determines if ghost should be deleted during frenzy mode
     * @param player coordinates
     * @return boolean value
     */
    public boolean deleter(Point player) {
        if (((isFrenzy) && (isCollisionWPlayer(player) != -1)) || (deleted == 1)) {
            deleted = 1;
            return true;
        }
        return false;
    }

    /**
     * updates player score during frenzy mode
     * @param player coordinates
     * @param score
     * @return updated score value
     */
    public int updateScore(Point player, int score) {
        if ((isFrenzy) && (isCollisionWPlayer(player) != -1) && (once == 0)) {
            score += 30;
            once = 1;
        }
        return score;
    }

    /**
     * updates ghost movement based on collision with wall
     */
    public void update() {
        if (isCollisionWWall() != -1) {
            onCollision();
        }
        move();
    }
    protected abstract void onCollision();

    protected void move() {
        double x = position.x;
        double y = position.y;
        if (direction == 0) {
            y += getSpeed();
        }
        else if (direction == 2) {
            y -= getSpeed();
        }
        else  if (direction == 1) {
            x += getSpeed();
        }
        else if (direction == 3) {
            x -= getSpeed();
        }
        position = new Point(x, y);
    }

    /**
     * updates ghost speed during frenzy mode
     * @return updated speed value
     */
    public double getSpeed() {
        if (isFrenzy) {
            return speed - 0.5;
        }
        return speed;
    }

    /**
     * draws ghost
     */
    public void drawGhost() {
        Image image = isFrenzy ? new Image(frenzyGhostFileName) : new Image(ghostFileName);
        DrawOptions options = new DrawOptions();
        image.drawFromTopLeft(position.x, position.y, options.setRotation(0));
    }
}
