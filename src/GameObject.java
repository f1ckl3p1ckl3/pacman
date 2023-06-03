import bagel.Image;
import bagel.util.Point;
import bagel.*;
import java.util.HashMap;
import java.lang.String;
import java.util.ArrayList;

/**
 * Represents Game objects in each level
 */

public class GameObject {
    private final WorldFile LEVEL = new WorldFile();
    /**
     * initialise relevant objects with corresponding data from CSV level file
     */
    public HashMap<String, ArrayList<Point>> level0 = LEVEL.readCSV("res/level0.csv");
    public HashMap<String, ArrayList<Point>> level1 = LEVEL.readCSV("res/level1.csv");
    public ArrayList<Point> pellet = level1.get(pelletKeyWord);
    private final String Ghost0 = "Ghost";
    private final ArrayList<Point> redGhostLocation0 = level0.get(Ghost0);
    private final String wallKeyWord = "Wall";
    public ArrayList<Point> walls1 = level1.get(wallKeyWord);
    public String wallFileName = "res/wall.png";
    private final String redGhostFile = "res/ghostRed.png";
    private static final String pelletKeyWord = "Pellet";
    private static final String pelletFileName = "res/pellet.png";
    private static final String cherryKeyWord = "Cherry";
    private static final String cherryFileName = "res/cherry.png";
    private static final String playerFile = "res/pac.png";
    public ArrayList<Point> cherry = level1.get(cherryKeyWord);
    protected static boolean isFrenzy = false;
    protected boolean drawPellet = true;
    protected boolean drawCherry = true;
    protected double frenzyCounter = 0;

    /**
     * Draws each object
     * @param fileName
     * @param coordinates
     */
    public void createObject(String fileName, ArrayList<Point> coordinates) {
        Image object = new Image(fileName);
        for (int i = 0; i < coordinates.size(); i += 1) {
            object.drawFromTopLeft((coordinates.get(i)).x, (coordinates.get(i)).y);
        }
    }
    private void reverseFrenzy() {
        isFrenzy = false;
    }

    /**
     * Tracks frenzy, making sure it only lasts 1000 frames
     */
    public void trackFrenzy() {
        if (frenzyCounter == 1000) {
            reverseFrenzy();
        }
    }

    /**
     * Tracks how long frenzy mode has been active for
     * @return
     */
    public double count() {
        if (isFrenzy) {
            frenzyCounter += 1;
        }
        return frenzyCounter;
    }

    /**
     * Creates object which is moving
     * @param fileName
     * @param point coordinate of object
     * @param rotation
     */
    public void createMovingObject(String fileName, Point point, double rotation) {
        Image image = new Image(fileName);
        DrawOptions options = new DrawOptions();
        image.drawFromTopLeft(point.x, point.y, options.setRotation(rotation));
    }

    /**
     * Determines if two objects collide
     * @param arrList all locations of stationary object
     * @param point location of moving object
     * @param fileName1 stationary object fileName
     * @param fileName2 moving object fileName
     * @return int value of index for which collision occurs in arrList.
     *         Also acts as boolean value, returning -1 when collision is false
     */
    public int isCollision(ArrayList<Point> arrList, Point point, String fileName1, String fileName2) {
        Image object1 = new Image(fileName1);
        Image object2 = new Image(fileName2);
        double heightObject1 = object1.getHeight();
        double widthObject1 = object1.getWidth();
        double heightObject2 = object2.getHeight();
        double widthObject2 = object2.getWidth();

        for (int i = 0; i < arrList.size(); i+=1) {
            double object1X1 = arrList.get(i).x;
            double object1X2 = object1X1 + widthObject2;
            double object1Y1 = arrList.get(i).y;
            double object1Y2 = object1Y1 + heightObject2;

            boolean right = object1X2 < point.x;
            boolean left = (point.x + widthObject1) < object1X1;
            boolean above = (point.y + heightObject1) < object1Y1;
            boolean below = object1Y2 < point.y;

            if (!(left || right || above || below)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Draws pellet object
     */
    public void createPellet() {
        if (drawPellet)
            this.createObject(pelletFileName, pellet);
    }

    /**
     * Determines if player collides with pellet
     * @param player coordinate
     */
    public void isCollisionWPellet(Point player) {
        if (isCollision(pellet, player, playerFile, pelletFileName) != -1) {
            drawPellet = false;
            isFrenzy = true;
        }
    }

    /**
     * Draws cherry object
     */
    public void createCherry() {
        if (drawCherry)
            this.createObject(cherryFileName, cherry);
    }

    /**
     * Determines if player collides with cherry
     * @param player coordinate
     * @param score
     * @return updated score value
     */
    public int isCollisionWCherry(Point player, int score) {
        if (isCollision(cherry, player, playerFile, cherryFileName) != -1) {
            cherry.remove(isCollision(cherry, player, playerFile, cherryFileName));
            score += 20;
        }
        return score;
    }

    /**
     * creates Red Ghost in level 0
     */
    public void createRedGhost() {
        this.createObject(redGhostFile, redGhostLocation0);
    }

    /**
     * Determines if player collides with Red Ghost in level 0
     * @param player coordinate
     * @param health
     * @return updates health of player
     */
    public int isCollisionWRedGhost(Point player, int health) {
        if (isCollision(redGhostLocation0, player, playerFile, redGhostFile) != -1) {
            health -= 1;
        }
        return health;
    }

    /**
     * Updates player location if collision occurs with red Ghost in level 0
     * @param player current location
     * @param start original location
     * @return starting value of player
     */
    public Point isCollisionWRed(Point player, Point start) {
        if (isCollision(redGhostLocation0, player, playerFile, redGhostFile) != -1) {
            player = start;
        }
        return player;
    }
}
