import bagel.util.Point;

import java.util.ArrayList;

/**
 * Represents dots in each level
 */

public class Dot extends GameObject {
    private final String dotFileName = "res/dot.png";
    private final String playerFileName = "res/pac.png";

    /**
     * Draws dot object
     * @param dots coordinate Points stored in ArrayList
     */
    public void createObject(ArrayList<Point> dots) {
        super.createObject(dotFileName, dots);
    }

    /**
     * Removes dot when player collides with one
     * @param dots current dot coordinate
     * @param point current player coordinates
     * @param score player score
     * @return decreased value of player score
     */
    public int removeDot(ArrayList<Point> dots, Point point, int score) {
        if (isCollisionWPlayer(dots, point) != -1) {
            score += 10;
            dots.remove(isCollisionWPlayer(dots, point));
        }
        return score;
    }

    /**
     * Determines if value is/isnt collision with player
     * @param dots current dot coordinate
     * @param point current player coordinate
     * @return int value of index of dot coordinate. Also acts as boolean value, returning -1 if false.
     */
    public int isCollisionWPlayer(ArrayList<Point> dots, Point point) {
        return super.isCollision(dots, point, dotFileName, playerFileName);
    }
}
