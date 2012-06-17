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

import pojos.Metal;
import providers.PointProvider;
import robots.ScrapBot;
import robots.SmartRobot;
import robots.TradeBot;
import ui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Main {

    /**
     * Main function - creates GUI
     * @param args - not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    final MainWindow gui = new MainWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Will scrap every combination of weapons in backpack, then combine the metal, then sort
     * The following conditions MUST be met:
     * - TF2 is running in windowed mode at 1024x768 and centered in the middle of a 1920x1080 PRIMARY display
     *      (eg. launch TF2 with "-windowed -width 1024 -height 768" as the parameters, minus the quotes)
     * - Valid Internet connection
     * - User must not interfere with controls.  There is the possibility for an infinite loop if interrupted.
     *      (worst case scenario, punch the power button or attempt to end the rouge process with keyboard shortcuts)
     * Notes:
     * - This can and will scrap all available weapons.  It doesn't care if they're vintage or sentimental.
     * - It's currently set to sort your backpack, too.
     * - You've been warned.
     * @param simulate - true/false value indicating whether to truly scrap, or to just pretend
     * @throws Exception - for the ScrapBot
     */
//    private static void scrapAndSort(boolean simulate) throws Exception {
//        PointProvider test = new PointProvider("");
//        ScrapBot robbie = new ScrapBot(test);
//        if(simulate){
//            // Simulate
//            robbie.scrapWeapons(true, true);
//        }
//        else {
//            // Give the user a five second head start
//            Thread.sleep(5000);
//            // Scrap, combine, and sort
//            robbie.scrapWeapons(true, false);
//            robbie.combineMetal(Metal.SCRAP);
//            robbie.combineMetal(Metal.RECLAIMED);
//            robbie.sortBackpack();
//        }
//        System.out.println(robbie.getNumOfLeftClicks() + " clicks saved.");
//    }

    /**
     * Will add the specified number of full pages to the trade
     * Designed for Steam Trade, will add items in order, assumes the user has already applied desired filter
     * Assumes the following conditions are met:
     * - Mouse position is above the 'next page' button after the three second delay
     * - The Steam Trade window has been selected and is the active window
     * @param numOfPages - the number of pages to add
     * @throws Exception - for the TradeBot
     */
//    private static void fillTradeByPage(Integer numOfPages) throws Exception {
//        // Give the user a three second head start
//        Thread.sleep(3000);
//        // Add specified items to the trade
//        TradeBot robbie = new TradeBot();
//        // Passing null will trade the whole page
//        if(numOfPages != null){
//            robbie.tradePagesOfFilter(numOfPages);
//        }
//        else{
//            robbie.tradePage();
//        }
//    }

    /**
     * Will add the specified number of items to the trade
     * Designed for Steam Trade, will add items in order, assumes the user has already applied desired filter
     * Assumes the following conditions are met:
     * - Mouse position is above the 'next page' button after the three second delay
     * - The Steam Trade window has been selected and is the active window
     * @param numOfItems - the number of items to add to the current page
     * @throws Exception - for the TradeBot
     */
//    private static void fillTradeByItems(int numOfItems) throws Exception {
//        // Give the user a three second head start
//        Thread.sleep(3000);
//        // Add specified items to the trade
//        TradeBot robbie = new TradeBot();
//        // Passing null will trade the whole page
//        robbie.tradeItemsInFilter(numOfItems);
//    }

    /**
     * Will output a randomly selected message continuously until process is terminated
     * Assumes the following:
     * - You are in a TF2 server after the initial five seconds
     * - The user can and will terminate this process manually in the time between one output and the next
     * You can add 'weight' to a message by adding it multiple times to increase it's frequency
     * @throws Exception - for the TradeBot
     */
//    private static void outputMessages() throws Exception {
//        // The messages to output
//        String[] messages = {
//                "Buying crates - paying 1 weapon for 8 of your crates!  Only trade me if you have at least 8 crates to trade!",
//                "Buying crates - paying 1 weapon for 8 of your crates!  Only trade me if you have at least 8 crates to trade!",
//                "Buying crates - paying 1 weapon for 8 of your crates!  Only trade me if you have at least 8 crates to trade!",
//                "Buying Apoco-Fists and Unarmed Combat.",
//        };
//        // Give human a five second head start
//        Thread.sleep(5000);
//        // Spam chat
//        TradeBot robbie = new TradeBot();
//        robbie.setMessagesToOutput(messages);
//        robbie.outputMessages();
//    }

//    private static void testSearch() throws Exception {
//        ArrayList<String> images = new ArrayList<String>();
//        images.add("images/testing/test.png");
//        images.add("images/testing/test2.png");
//        images.add("images/testing/test3.png");
//        images.add("images/testing/test4.png");
//        images.add("images/testing/test5.png");
//
//        SmartRobot robbie = new SmartRobot();
//        Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//        BufferedImage screenshot = robbie.createScreenCapture(captureSize);
//
//        for (String image : images){
//            long start = System.currentTimeMillis();
//            Point location = robbie.findLocOfImage(image, screenshot);
//            long stop = System.currentTimeMillis();
//            if (!location.equals(new Point(-1, -1))){
//                System.out.println("Found " + image + " at " + location.x + ", " + location.y);
//            }
//            else{
//                System.out.println("Did not find " + image);
//            }
//            System.out.println("Took " + ((stop-start)/1000d) + " seconds.");
//            System.out.println();
//        }
//    }
}
