import bagel.util.Point;

/**
 * represents red ghosts in level 1
 */
public class Red extends Ghost {
    /**
     * constructor which initialises starting position, speed and direction, extending from Ghost superclass.
     * @param initialPosition
     */
    public Red(Point initialPosition) {
        super(initialPosition);

        super.speed = 1;
        super.ghostFileName = "res/ghostRed.png";
        super.direction = 1;
        super.deleted = 0;
    }

    /**
     * determines unique behaviour of red Ghost
     */
    @Override
    protected void onCollision() {
        super.direction = (super.direction + 2) % 4;
    }
}
