import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2023
 *
 * Please enter your name below
 * Annabel Sutherland
 */
public class ShadowPac extends AbstractGame {
    // GAME ATTRIBUTES
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static String GAME_START1 = "PRESS SPACE TO START";
    private final static String GAME_START2 = "USE ARROW KEYS TO MOVE";
    private final Font FONT_64 = new Font("res/FSO8BITR.ttf", 64);
    private final Font FONT_24 = new Font("res/FSO8BITR.ttf", 24);
    private final Font FONT_20 = new Font("res/FSO8BITR.ttf", 20);
    private final Font FONT_40 = new Font("res/FSO8BITR.ttf", 40);
    private final static String GAME_OVER = "GAME OVER!";
    private final static String GAME_WIN = "WELL DONE!";
    private final static String LEVEL_COMP = "LEVEL COMPLETE";
    private final static String STARTLVL1 = "PRESS SPACE TO START";
    private final static String STARTLVL1_2 = "USE ARROW KEYS TO MOVE";
    private final static String STARTLVL1_3 = "EAT THE PELLET TO ATTACK";
    private final static String heartFileName = "res/heart.png";

    // WORLD
    private final WorldFile LEVEL = new WorldFile();
    private final HashMap<String, ArrayList<Point>> level0 = LEVEL.readCSV("res/level0.csv");
    private final HashMap<String, ArrayList<Point>> level1 = LEVEL.readCSV("res/level1.csv");
    // PLAYER
    private double rotation = 0;
    private final double speed = 3;
    private Point coordinate0 = level0.get("Player").get(0);
    private Point coordinate1 = level1.get("Player").get(0);
    private final Point start0 = level0.get("Player").get(0);
    private final Point start1 = level1.get("Player").get(0);
    // WALL
    private final Wall wall0 = new Wall();
    private final Wall wall1 = new Wall();
    private final ArrayList<Point> wallCoordinates0 = level0.get("Wall");
    private final ArrayList<Point> wallCoordinates1 = level1.get("Wall");
    // DOT
    private Dot dot1 = new Dot();
    private Dot dot0 = new Dot();
    private GameObject object0 = new GameObject();
    private ArrayList<Point> dotCoordinates0 = level0.get("Dot");
    private ArrayList<Point> dotCoordinates1 = level1.get("Dot");
    // GHOSTS
    private final List<Ghost> ghosts;
    // red ghost
    private final Point redGhostLocation1 = level1.get("GhostRed").get(0);
    // blue ghost
    private final Point blueGhostLocation1 = level1.get("GhostBlue").get(0);
    // green ghost
    private final Point greenGhostLocation1 = level1.get("GhostGreen").get(0);
    // pink ghost
    private final Point pinkGhostLocation1 = level1.get("GhostPink").get(0);
    // LEVEL MAINTAINER
    private GameObject object1 = new GameObject();
    private int beginLevel1 = 0;
    private int health = 3;
    private int score = 0;
    private int indicator = 0;
    private int frameCounter = 0;
    private int count1 = 0;
    private boolean begun = false;
    private boolean begun1 = false;
    // win condition level 0
    private boolean win0() {
        return score == 1210;
    }
    // win condition level 1
    private boolean win1() {
        return score == 800;
    }
    // lose condition
    private boolean lose() {
        return health == 0;
    }
    // updates player score
    private void scoreTracker() {
        FONT_20.drawString("SCORE " + score, 25, 25);
    }
    // updates player health
    private void heartTracker() {
        Image heart = new Image(heartFileName);
        for (int i = 0; i < health; i++){
            heart.drawFromTopLeft(900+i*30, 10);
        }
    }


    public ShadowPac() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);

        Red red = new Red(new Point(redGhostLocation1.x, redGhostLocation1.y));
        Blue blue = new Blue(new Point(blueGhostLocation1.x, blueGhostLocation1.y));
        Green green = new Green(new Point(greenGhostLocation1.x, greenGhostLocation1.y));
        Pink pink = new Pink(new Point(pinkGhostLocation1.x, pinkGhostLocation1.y));

        ghosts = new ArrayList<>(List.of(red, blue, green, pink));
    }
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Method that performs state update
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        if (input.wasPressed(Keys.SPACE) && beginLevel1 == 0) {
            begun = true;
        }
        // start game if space key pressed
        if (!begun) {
            FONT_64.drawString(GAME_TITLE, 260, 250);
            FONT_24.drawString(GAME_START1, 320, 440);
            FONT_24.drawString(GAME_START2, 305, 474);
            return;
        }
        // print win condition for level 0
        if (this.win0() && beginLevel1 == 0 || beginLevel1 == 1) {
            score = 0;
            health = 3;
            beginLevel1 = 1;
            double width = FONT_64.getWidth(LEVEL_COMP);
            FONT_64.drawString(LEVEL_COMP, (Window.getWidth() - width) / 2.0, (Window.getHeight() - 64) / 2.0);
            if (count1 == 300) {
                beginLevel1 = 2;
                return;
            }
            count1 += 1;
            return;
        }
        if (input.wasPressed(Keys.SPACE) && beginLevel1 == 2) {
            begun1 = true;
        }
        if (!begun1 && beginLevel1 == 2) {
            FONT_40.drawString(STARTLVL1, 200, 390);
            FONT_40.drawString(STARTLVL1_2, 200, 440);
            FONT_40.drawString(STARTLVL1_3, 200, 490);
            return;
        }
        // print win condition for level 1
        if (this.win1() && beginLevel1 == 2) {
            double width = FONT_64.getWidth(GAME_WIN);
            FONT_64.drawString(GAME_WIN, (Window.getWidth() - width) / 2.0, (Window.getHeight() - 64) / 2.0);
            return;
        }
        // print lose condition
        if (this.lose()) {
            double width = FONT_64.getWidth(GAME_OVER);
            FONT_64.drawString(GAME_OVER, (Window.getWidth() - width) / 2.0, (Window.getHeight() - 64) / 2.0);
            return;
        }
        // tracks number of frames, indicator used for opening and closing pacman mouth
        if (frameCounter == 15) {
            frameCounter = 0;
            indicator += 1;
        }
        frameCounter += 1;
        // begin level 0
        if (beginLevel1 == 0) {
            wall0.createObject(wallCoordinates0);

            dot0.createObject(dotCoordinates0);
            score = dot0.removeDot(dotCoordinates0, coordinate0, score);

            object0.createRedGhost();
            health = object0.isCollisionWRedGhost(coordinate0, health);
            coordinate0 = object0.isCollisionWRed(coordinate0, start0);

            Player player0 = new Player();
            player0.createMovingObject(wallCoordinates0, coordinate0, input, indicator, speed, rotation);
            rotation = player0.updateRotation(input, rotation);
            coordinate0 = player0.movement(wallCoordinates0, coordinate0, input, speed);
        }
        // begin level 1
        if (beginLevel1 == 2) {
            wall1.createObject(wallCoordinates1);
            Player player1 = new Player();

            dot1.createObject(dotCoordinates1);
            score = dot1.removeDot(dotCoordinates1, coordinate1, score);

            object1.createPellet();
            object1.isCollisionWPellet(coordinate1);

            object1.createCherry();
            score = object1.isCollisionWCherry(coordinate1, score);
            // run behaviour for each ghost
            for (Ghost ghost : ghosts) {
                ghost.update();
                if (ghost.deleter(coordinate1)) {}
                else if (ghost.isCollisionWPlayer(coordinate1) != -1) {
                    ghost.position = ghost.initialPosition;
                    health -= 1;
                    coordinate1 = start1;
                    ghost.drawGhost();
                }
                else {
                    ghost.drawGhost();
                }
                score = ghost.updateScore(coordinate1, score);
            }
            object1.trackFrenzy();
            object1.count();

            player1.createMovingObject(wallCoordinates1, coordinate1, input, indicator, speed, rotation);
            rotation = player1.updateRotation(input, rotation);
            coordinate1 = player1.movement(wallCoordinates1, coordinate1, input, speed);
        }
        // update player score and health
        this.scoreTracker();
        this.heartTracker();
    }
}

