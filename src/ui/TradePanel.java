/******************************************************************************
 * This file is part of tf2-bot.                                              *
 *                                                                            *
 * Foobar is free software: you can redistribute it and/or modify             *
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
import robots.TradeBot;
import system.Outputter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Rory
 * Date: 3/14/12
 * Time: 10:53 AM
 */

public class TradePanel extends JPanel {

    private SettingsProvider settingsProvider;
    private Outputter outputter;

    public TradePanel(SettingsProvider provider, Outputter out) {
        super();
        settingsProvider = provider;
        outputter = out;
        init();
    }

    private void init() {
        JPanel tradePanel = new JPanel();
        tradePanel.setLayout(new BoxLayout(tradePanel, BoxLayout.Y_AXIS));

        JButton addPageButton = new JButton("Add Entire Page");
        addPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    outputter.output("Trading whole page in 3 seconds...");
                    Thread.sleep(3000);
                    TradeBot robbie = new TradeBot(outputter);
                    robbie.tradePage();
                    outputter.output(robbie.getNumOfLeftClicks() + " clicks saved.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tradePanel.add(addPageButton);

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.X_AXIS));
        final JSpinner numItems = new JSpinner();
        JButton itemsButton = new JButton("Trade Items");
        itemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int numOfItems = Integer.parseInt(numItems.getValue().toString());
                    outputter.output("Trading " + numOfItems + " items in 3 seconds...");
                    Thread.sleep(3000);
                    TradeBot robbie = new TradeBot(outputter);
                    robbie.tradeItemsInFilter(numOfItems);
                    outputter.output(robbie.getNumOfLeftClicks() + " clicks saved.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        itemsPanel.add(numItems);
        itemsPanel.add(itemsButton);
        tradePanel.add(itemsPanel);

        JPanel pagesPanel = new JPanel();
        pagesPanel.setLayout(new BoxLayout(pagesPanel, BoxLayout.X_AXIS));
        final JSpinner numPages = new JSpinner();
        JButton pagesButton = new JButton("Trade Pages");
        pagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int numOfPages = Integer.parseInt(numPages.getValue().toString());
                    outputter.output("Trading " + numOfPages + " pages in 3 seconds...");
                    Thread.sleep(3000);
                    TradeBot robbie = new TradeBot(outputter);
                    robbie.tradePagesOfFilter(numOfPages);
                    outputter.output(robbie.getNumOfLeftClicks() + " clicks saved.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pagesPanel.add(numPages);
        pagesPanel.add(pagesButton);
        tradePanel.add(pagesPanel);

        JPanel messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.X_AXIS));
        JButton editMessagesButton = new JButton("Edit Messages");
        editMessagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    new MessagesEditor(settingsProvider, outputter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        messagesPanel.add(editMessagesButton);

        JButton outputButton = new JButton("Output Messages");
        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    outputter.output("TradeBot will chat in 5 seconds...");
                    Thread.sleep(5000);
                    TradeBot robbie = new TradeBot(outputter);
                    String messagesString = settingsProvider.getSetting(Settings.messages);
                    robbie.setMessagesToOutput(messagesString.split("\n"));
                    // TODO: Allow the user to stop this thread by clicking a button
                    robbie.outputMessages();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        messagesPanel.add(outputButton);
        tradePanel.add(messagesPanel);

        add(tradePanel);
    }
}
