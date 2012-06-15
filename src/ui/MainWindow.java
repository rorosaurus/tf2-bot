package ui;

import pojos.PointPlace;
import providers.PointProvider;
import providers.SettingsProvider;
import system.Outputter;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * User: Rory
 * Date: 3/13/12
 * Time: 3:47 PM
 */

public class MainWindow extends JFrame {

    private final String POINTS_FILENAME = "points.properties";
    private final String SETTINGS_FILENAME = "settings.properties";

    private JTextArea outputArea = new JTextArea("Ready");
    private Outputter outputter = new Outputter(outputArea);

    private PointProvider pointProvider = new PointProvider(POINTS_FILENAME);
    private SettingsProvider settingsProvider = new SettingsProvider(SETTINGS_FILENAME);

    public MainWindow() throws Exception {
        super();
        init();
    }

    private void init() throws Exception {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel scrapPanel = new ScrapPanel(settingsProvider, pointProvider, outputter);
        JPanel tradePanel = new TradePanel(settingsProvider, outputter);
        JPanel settingsPanel = new SettingsPanel(settingsProvider, pointProvider, outputter);

        tabbedPane.addTab("Scrap", new ImageIcon(getClass().getResource("/images/metal.png")), 
                scrapPanel, "Handles scrapping of weapons and metal");
        tabbedPane.addTab("Trade", new ImageIcon(getClass().getResource("/images/trade.png")), 
                tradePanel, "Handles trade and chat assisting");
        tabbedPane.addTab("Settings", new ImageIcon(getClass().getResource("/images/settings.png")), 
                settingsPanel, "Change options");

        Panel outputPanel = new Panel();
        outputPanel.setPreferredSize(new Dimension(200,300));
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.X_AXIS));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputPanel.add(scrollPane);
        outputArea.setLineWrap(true);
        outputArea.setAutoscrolls(true);
        outputArea.setEditable(false);

        JSplitPane rootPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane, outputPanel);

        add(rootPanel);
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        setTitle("TF2 Bot");
        setSize(700, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        checkForPointsFile();
    }

    private void checkForPointsFile(){
        File f = new File(POINTS_FILENAME);
        if(!f.exists()){
            try {
                new PointPrompter(PointPlace.values(), pointProvider, "To use the ScrapBot, please configure all points.  Then close window.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            pointProvider.getRestOfPoints("These points were not found.  Please configure, then close window.");
        }
    }
}
