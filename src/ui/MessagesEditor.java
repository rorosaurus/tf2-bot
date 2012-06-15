package ui;

import pojos.Settings;
import providers.SettingsProvider;
import system.Outputter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Rory
 * Date: 3/14/12
 * Time: 10:19 AM
 */

public class MessagesEditor extends JFrame {

    SettingsProvider settingsProvider;
    Outputter outputter;
    JTextArea messagesArea;

    public MessagesEditor(SettingsProvider provider, Outputter out) throws Exception {
        super();
        settingsProvider = provider;
        outputter = out;
        init();
    }

    private void init(){
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));

        Panel inputPanel = new Panel();
        inputPanel.setPreferredSize(new Dimension(200,300));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        messagesArea = new JTextArea("Enter messages here.  Separate messages by starting a new line.\n" +
                "Messages can be no longer than 127 characters.");
        messagesArea.setLineWrap(false);
        JScrollPane scrollPane = new JScrollPane(messagesArea);
        inputPanel.add(scrollPane);

        if(settingsProvider.getSetting(Settings.messages) != null){
            messagesArea.setText(settingsProvider.getSetting(Settings.messages));
        }

        rootPanel.add(inputPanel);


        Button applyButton = new Button("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                settingsProvider.putSetting(Settings.messages, messagesArea.getText());
                settingsProvider.writeProperties();
                outputter.output("Messages Applied.");
                dispose();
            }
        });
        rootPanel.add(applyButton);

        add(rootPanel);
        setTitle("Edit Messages to Output");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
