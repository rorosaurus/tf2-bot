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
import pojos.Settings;
import providers.PointProvider;
import providers.SettingsProvider;
import system.Outputter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {

    private SettingsProvider settingsProvider;
    private PointProvider pointProvider;
    private Outputter outputter;

    public SettingsPanel(SettingsProvider provider, PointProvider pProvider, Outputter out) {
        super();
        settingsProvider = provider;
        pointProvider = pProvider;
        outputter = out;
        init();
    }

    private void init(){
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        settingsPanel.add(new JLabel("Web API Key"));
        final JTextArea webAPIArea = new JTextArea("--Put Web API Key Here--");
        if(settingsProvider.getSetting(Settings.webAPIKey) != null){
            webAPIArea.setText(settingsProvider.getSetting(Settings.webAPIKey));
        }
        settingsPanel.add(webAPIArea);

        settingsPanel.add(new JLabel("64-bit Steam ID"));
        final JTextArea steamIDArea = new JTextArea("--Put 64-bit Steam ID Here--");
        if(settingsProvider.getSetting(Settings.steamID) != null){
            steamIDArea.setText(settingsProvider.getSetting(Settings.steamID));
        }
        settingsPanel.add(steamIDArea);

        settingsPanel.add(new JLabel("Trade Bot Autodelay (in ms)"));
        final JSpinner autodelaySpinner = new JSpinner();
        autodelaySpinner.setValue(40);
        if(settingsProvider.getSetting(Settings.autodelay) != null){
            autodelaySpinner.setValue(Integer.parseInt(settingsProvider.getSetting(Settings.autodelay)));
        }
        settingsPanel.add(autodelaySpinner);

        Button applyButton = new Button("Apply Settings");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                settingsProvider.putSetting(Settings.webAPIKey, webAPIArea.getText());
                settingsProvider.putSetting(Settings.steamID, steamIDArea.getText());
                settingsProvider.putSetting(Settings.autodelay, autodelaySpinner.getValue().toString());
                settingsProvider.writeProperties();
                outputter.output("Settings saved.");
            }
        });
        settingsPanel.add(applyButton);

        Button pointsButton = new Button("Reconfigure Points");
        pointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    new PointPrompter(PointPlace.values(), pointProvider, "Reconfigure points as desired, then close window.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        settingsPanel.add(pointsButton);

        add(settingsPanel);
    }
}
