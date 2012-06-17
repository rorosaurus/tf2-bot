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

import pojos.Settings;
import providers.SettingsProvider;
import system.Outputter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
