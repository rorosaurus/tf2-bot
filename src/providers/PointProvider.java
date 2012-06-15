package providers;

import pojos.PointPlace;
import ui.PointPrompter;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * User: Rory
 * Date: 3/10/12
 * Time: 12:49 AM
 */

public class PointProvider {

    private final String FILENAME;
    private HashMap<PointPlace, Point> points = new HashMap<PointPlace, Point>();

    public PointProvider(String filename) {
        FILENAME = filename;
        retrievePoints();
    }

    public Point getPoint(PointPlace place){
        if(points.containsKey(place)) return points.get(place);
        else return null;
    }

    public void retrievePoints() {
        try {
            // Try to find a file
            File f = new File(FILENAME);
            Properties pointProperties = new Properties();
            // If the file exists, attempt to use it
            if(f.exists()){
                // Read in properties
                FileInputStream in = new FileInputStream(FILENAME);
                pointProperties.load(in);
                in.close();
                Set<String> propertyNames = pointProperties.stringPropertyNames();
                // Set all points from the properties file
                points = new HashMap<PointPlace, Point>();
                for(String name : propertyNames){
                    for (PointPlace pointPlace : PointPlace.values()){
                        if (name.equals(pointPlace.toString())){
                            String value = pointProperties.getProperty(name);
                            int commaIndex = value.indexOf(",");
                            int x = Integer.parseInt(value.substring(0, commaIndex));
                            int y = Integer.parseInt(value.substring(commaIndex+1, value.length()));
                            Point point = new Point(x, y);
                            points.put(pointPlace, point);
                            break;
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRestOfPoints(String message){
        // Ensure all points are filled out, prompt if necessary
        ArrayList<PointPlace> placesToGet = new ArrayList<PointPlace>();
        for(PointPlace pointPlace : PointPlace.values()){
            if(!points.containsKey(pointPlace)){
                placesToGet.add(pointPlace);
            }
        }
        if(!placesToGet.isEmpty()) promptForPoints(placesToGet, message);
    }

    private void promptForPoints(ArrayList<PointPlace> places, String message) {
        try {
            new PointPrompter(places, this, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putPoint(PointPlace place, Point point){
        points.put(place, point);
    }

    public void writeProperties() {
        try {
            // Create file
            Properties pointProperties = new Properties();
            FileOutputStream out = new FileOutputStream(FILENAME);
            for(PointPlace pointPlace : points.keySet()){
                pointProperties.setProperty(pointPlace.toString(), points.get(pointPlace).x + "," + points.get(pointPlace).y);
            }
            pointProperties.store(out, "---This file stores the location of points to click---");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
