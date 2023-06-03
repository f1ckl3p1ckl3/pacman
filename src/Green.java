import bagel.util.Point;
import java.util.Random;

/**
 * represents green ghost
 */
public class Green extends Ghost {
    /**
     * constructor which initialises starting position, speed, direction of green ghost, extending from Ghost superclass
     * @param initialPosition
     */
    public Green(Point initialPosition) {
        super(initialPosition);

        super.ghostFileName = "res/ghostGreen.png";
        super.speed = 4;
        Random random = new Random();
        super.direction = random.nextInt(4);
        super.deleted = 0;
    }

    /**
     * determines unique behaviour of green ghost
     */

    @Override
    protected void onCollision() {
        super.direction = (super.direction + 2) % 4;
    }
}
