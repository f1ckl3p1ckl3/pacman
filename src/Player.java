import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import java.util.ArrayList;

/**
 * Represents player object
 */
public class Player extends GameObject {
    private final String pacOpen = "res/pacOpen.png";
    private final String pacClose = "res/pac.png";
    /**
     * Opens and closes pacman mouth
     */
    public String openClose(int indicator) {
        if (indicator % 2 == 0) {
            return (pacClose);
        }
        return (pacOpen);
    }

    /**
     * Updates player movement
     * @param wallLocation containing all wall locations on level
     * @param playerLocation current coordinates
     * @param input user input from keyboard
     * @param speed player speed
     * @return new player Point after user input has been applied
     */
    public Point movement(ArrayList<Point> wallLocation, Point playerLocation, Input input, double speed) {
        double x = playerLocation.x;
        double y = playerLocation.y;
        if (input.isDown(Keys.DOWN)) {
            y += speed;
        }
        else if (input.isDown(Keys.UP)) {
            y -= speed;
        }
        else if (input.isDown(Keys.LEFT)) {
            x -= speed;
        }
        else if (input.isDown(Keys.RIGHT)) {
            x += speed;
        }
        Point point = new Point(x, y);
        if (super.isCollision(wallLocation, point, pacOpen, super.wallFileName) <= 0) {
            return point;
        }
        return playerLocation;
    }

    /**
     * updates the rotation of pacman image
     * @param input user input
     * @param rotationValue current rotation value determining rotation of pacman
     * @return updated rotation value
     */
    public double updateRotation(Input input, double rotationValue) {
        if (input.isDown(Keys.DOWN)) {
            rotationValue = Math.toRadians(90);
        }
        else if (input.isDown(Keys.LEFT)) {
            rotationValue = Math.toRadians(180);
        }
        else if (input.isDown(Keys.UP)) {
            rotationValue = Math.toRadians(270);
        }
        else if (input.isDown(Keys.RIGHT)) {
            rotationValue = Math.toRadians(0);
        }
        return rotationValue;
    }

    /**
     * draws player image
     * @param wall contains all wall coordinates to ensure player never goes through wall
     * @param point current player coordinate
     * @param input user input to update player movement
     * @param indicator used to open and close pacman mouth
     * @param speed player speed
     * @param rotation rotation value for rotation of pacman image
     */
    public void createMovingObject(ArrayList<Point> wall, Point point, Input input, int indicator, double speed, double rotation){
        super.createMovingObject(openClose(indicator), movement(wall, point, input, speed), rotation);
    }

}
