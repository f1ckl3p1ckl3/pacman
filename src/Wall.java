import bagel.util.Point;
import java.util.ArrayList;

/**
 * represents Wall objects
 */
public class Wall extends GameObject {
    String wallFileName = "res/wall.png";

    /**
     * Draws wall objects
     * @param wall locations for each level
     */
    public void createObject(ArrayList<Point> wall) {
        super.createObject(wallFileName, wall);
    }

}
