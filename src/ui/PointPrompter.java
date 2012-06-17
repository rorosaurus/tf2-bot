/******************************************************************************
 * This file is part of tf2-bot.                                              *
 *                                                                            *
 * tf2-bot is free software: you can redistribute it and/or modify            *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * tf2-bot is distributed in the hope that it will be useful,                 *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with tf2-bot.  If not, see <http://www.gnu.org/licenses/>.           *
 ******************************************************************************/

package ui;

import pojos.PointPlace;
import providers.PointProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class PointPrompter extends JFrame {

    private final ArrayList<PointPlace> pointPlaces;
    private PointProvider pointProvider;
    private String message;

    public PointPrompter(ArrayList<PointPlace> places, PointProvider provider, String mes) throws Exception {
        pointPlaces = places;
        pointProvider = provider;
        message = mes;
        init();
    }

    public PointPrompter(PointPlace[] places, PointProvider provider, String mes) throws Exception {
        pointPlaces = new ArrayList<PointPlace>();
        Collections.addAll(pointPlaces, places);
        pointProvider = provider;
        message = mes;
        init();
    }

    private void init(){
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));

        if(message != null){
            rootPanel.add(new JLabel(message));
        }

        JPanel buttonsPanel = new JPanel();
        JScrollPane allButtonsPanel = new JScrollPane(buttonsPanel);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        for(final PointPlace place : pointPlaces){
            buttonsPanel.add(new PointItem(place));
        }

        rootPanel.add(allButtonsPanel);
        add(rootPanel);
        setTitle("Configure Points");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class PointItem extends JPanel {

        JLabel pointLabel = new JLabel();

        private PointItem(final PointPlace place) {
            super();
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            JButton setButton = new JButton("Set " + place.toString());
            final Point loc = new Point(-1, -1);
            if(pointProvider.getPoint(place) != null){
                loc.x = pointProvider.getPoint(place).x;
                loc.y = pointProvider.getPoint(place).y;
            }
            setButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        JFrame notice = new JFrame();
                        notice.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        Point curLoc = MouseInfo.getPointerInfo().getLocation();
                        notice.setLocation(curLoc.x-75, curLoc.y-20);
                        notice.setResizable(false);
                        notice.setVisible(true);
                        notice.setTitle("3");
                        Thread.sleep(1000);
                        notice.setTitle("2");
                        Thread.sleep(1000);
                        notice.setTitle("1");
                        Thread.sleep(1000);
                        Point curMouseLoc = MouseInfo.getPointerInfo().getLocation();
                        notice.dispose();
                        pointProvider.putPoint(place, curMouseLoc);
                        pointProvider.writeProperties();
                        setPointLabel(curMouseLoc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            add(setButton);
            setPointLabel(loc);
            add(pointLabel);
            JButton helpButton = new JButton("?");
            helpButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        new HelpWindow(place);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            add(helpButton);
        }

        private void setPointLabel(Point loc){
            pointLabel.setText(loc.x + ", " + loc.y);
        }
    }
}
