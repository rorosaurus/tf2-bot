package ui;

import pojos.PointPlace;

import javax.swing.*;
import java.awt.*;

/**
 * User: Rory
 * Date: 3/14/12
 * Time: 12:14 PM
 */

// TODO: just make an image and give it a tooltip
public class HelpWindow extends JFrame {

    private PointPlace pointPlace;
    private JPanel rootPanel = new JPanel();
    private JTextArea infoLabel = new JTextArea();
    private final int width = 300;
    private final int height = 200;

    public HelpWindow(PointPlace place) throws Exception {
        super();
        pointPlace = place;
        init();
        initPlace();
    }

    private void init(){
        add(rootPanel);
        setTitle("Help on " + pointPlace.toString());
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Point curLoc = MouseInfo.getPointerInfo().getLocation();
        setLocation(curLoc.x-(width/2), curLoc.y-(height/2));
        setVisible(true);
    }

    private void initPlace(){
        infoLabel.setLineWrap(true);
        infoLabel.setEditable(false);
        rootPanel.add(infoLabel);
        switch(pointPlace){
            case smeltWeapons: {
                infoLabel.setText("This is where the 'Smelt Weapons' \noption is.  Durr.");
            }
        }
    }
}
