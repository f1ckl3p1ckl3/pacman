import bagel.util.Point;
import java.util.Random;

/**
 * represents pink ghosts
 */
public class Pink extends Ghost {
    private Random random;

    /**
     * constructor which initialises starting position, speed and direction, extends from Ghost superclass
     * @param initialPosition
     */
    public Pink(Point initialPosition) {
        super(initialPosition);

        this.random = new Random();
        super.ghostFileName = "res/ghostPink.png";
        super.direction = random.nextInt(4);
        super.speed = 3;
        super.deleted = 0;
    }

    /**
     * determines unique behaviour of pink ghost movement
     */
    @Override
    protected void onCollision() {
        super.direction = (super.direction + 2) % 4;
        move();
        super.direction = (super.direction + 2) % 4;
        super.direction = (super.direction + random.nextInt(3) + 1) % 4;
    }
}
