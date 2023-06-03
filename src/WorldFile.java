import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import bagel.util.*;
/**
 * Represents a worldFile
 */
public class WorldFile {
    private static final String Player = "Player";
    private static final String Ghost = "Ghost";
    private static final String GhostRed = "GhostRed";
    private static final String GhostBlue = "GhostBlue";
    private static final String GhostGreen = "GhostGreen";
    private static final String GhostPink = "GhostPink";
    private static final String Cherry = "Cherry";
    private static final String Pellet = "Pellet";
    private static final String Wall = "Wall";
    private static final String Dot = "Dot";

    /**
     * Method used to read file and create objects
     * @param filename a CSV file giving x and y coordinates of each game object
     * @return a HashMap containing an object keyword, it's corresponding arrayList of location Points.
     */
    public HashMap<String, ArrayList<Point>> readCSV(String filename) {
        HashMap<String, ArrayList<Point>> worldFile = new HashMap<>();
        String[] details = new String[]{Player, Ghost, GhostRed, GhostBlue, GhostGreen, GhostPink, Cherry, Pellet, Wall, Dot};
        // for each keyword, add ArrayList containing x && y coordinates
        for (String detail : details)
            worldFile.put(detail, new ArrayList<>());

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            // parse csv file line by line
            while (line != null) {
                // split line into two components ["image", Point]
                String[] arrLine = line.split(",");
                // convert to Double
                Double x = Double.parseDouble(arrLine[1]);
                Double y = Double.parseDouble(arrLine[2]);
                // add coordinates to arraylist in hashmap
                for (String detail : details) {
                    if (line.contains(detail)) {
                        Point point = new Point(x, y);
                        worldFile.get(detail).add(point);
                    }
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return worldFile;
    }
}

