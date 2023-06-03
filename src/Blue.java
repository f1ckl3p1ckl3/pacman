import bagel.util.Point;

/**
 * represents blue ghost
 */
public class Blue extends Ghost {
    /**
     * constructor to initialise starting position, speed, direction, extending from Ghost superclass
     * @param initialPosition
     */
    public Blue(Point initialPosition) {
        super(initialPosition);

        super.ghostFileName = "res/ghostBlue.png";
        super.direction = 0;
        super.speed = 2;
        super.deleted = 0;
    }

    /**
     * Determines the unique behaviour of blue ghost on collision
     */
    @Override
    protected void onCollision() {
        super.direction = (super.direction + 2) % 4;
    }
}
