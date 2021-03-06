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

import pojos.Metal;
import pojos.PointPlace;
import pojos.Settings;
import providers.PointProvider;
import providers.SettingsProvider;
import robots.ScrapBot;
import robots.TradeBot;
import system.Outputter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrapPanel extends JPanel {

    private SettingsProvider settingsProvider;
    private PointProvider pointProvider;
    private Outputter outputter;

    public ScrapPanel(SettingsProvider provider, PointProvider pProvider, Outputter out) throws Exception {
        super();
        settingsProvider = provider;
        pointProvider = pProvider;
        outputter = out;
        init();
    }

    private void init() throws Exception {
        JPanel scrapPanel = new JPanel();
        scrapPanel.setLayout(new BoxLayout(scrapPanel, BoxLayout.Y_AXIS));

        JPanel scrapAllPanel = new JPanel();
        scrapAllPanel.setLayout(new BoxLayout(scrapAllPanel, BoxLayout.X_AXIS));
        final JCheckBox simulateBox = new JCheckBox("Simulate");
        simulateBox.setSelected(true);
        if(settingsProvider.getSetting(Settings.simulate) != null){
            simulateBox.setSelected(Boolean.parseBoolean(settingsProvider.getSetting(Settings.simulate)));
        }
        final ScrapButton scrapAllButton = new ScrapButton("Scrap All Weapons", settingsProvider);
        simulateBox.addItemListener(scrapAllButton);
        scrapAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(hasAllPoints()){
                    try {
                        if(scrapAllButton.getSimulate()){
                            outputter.output("Simulating scrapping...");
                        }
                        else{
                            outputter.output("Scrapping items in 3 seconds...");
                            Thread.sleep(3000);
                        }
                        Thread botThread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    ScrapBot robbie = new ScrapBot(pointProvider, settingsProvider, outputter);
                                    robbie.scrapWeapons(true, scrapAllButton.getSimulate());
                                    if(!scrapAllButton.getSimulate()) outputter.output(robbie.getNumOfLeftClicks() + " clicks saved.");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        botThread.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        scrapAllPanel.add(scrapAllButton);
        scrapAllPanel.add(simulateBox);
        scrapPanel.add(scrapAllPanel);

        Button combineMetalsButton = new Button("Combine All Metals");
        combineMetalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(hasAllPoints()){
                    try {
                        outputter.output("Combining metal in 3 seconds...");
                        Thread.sleep(3000);
                        ScrapBot robbie = new ScrapBot(pointProvider, settingsProvider, outputter);
                        robbie.combineMetal(Metal.SCRAP);
                        robbie.combineMetal(Metal.RECLAIMED);
                        outputter.output(robbie.getNumOfLeftClicks() + " clicks saved.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        scrapPanel.add(combineMetalsButton);

        Button scrapCombineSortButton = new Button("Scrap, Combine, and Sort");
        scrapCombineSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(hasAllPoints()){
                    try {
                        outputter.output("Scrapping, Combining, and Sorting in 3 seconds...");
                        Thread.sleep(3000);
                        ScrapBot robbie = new ScrapBot(pointProvider, settingsProvider, outputter);
                        robbie.scrapWeapons(true, false);
                        robbie.combineMetal(Metal.SCRAP);
                        robbie.combineMetal(Metal.RECLAIMED);
                        robbie.sortBackpack();
                        outputter.output(robbie.getNumOfLeftClicks() + " clicks saved.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        scrapPanel.add(scrapCombineSortButton);

        add(scrapPanel);
    }

    private boolean hasAllPoints(){
        boolean hasEveryPoint = true;
        for(PointPlace place : PointPlace.values()){
            if(pointProvider.getPoint(place) == null){
                hasEveryPoint = false;
                outputter.output("Missing point '" + place.toString() + "'.");
            }
        }
        if(!hasEveryPoint) outputter.output("Cannot proceed until points are configured.");
        return hasEveryPoint;
    }
}
