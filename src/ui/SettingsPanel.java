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

/**
 * User: Rory
 * Date: 3/14/12
 * Time: 10:53 AM
 */

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

        Button applyButton = new Button("Apply Settings");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                settingsProvider.putSetting(Settings.webAPIKey, webAPIArea.getText());
                settingsProvider.putSetting(Settings.steamID, steamIDArea.getText());
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
