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

package robots;

import system.Outputter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TradeBot extends SmartRobot {

    private Outputter outputter;

    private String[] messagesToOutput;
    int spacing = 115;

    /**
     * Constructor
     * @param out outputter to output text to
     * @throws Exception - for the Robot
     * AutoDelay should not be lowered much more
     */
    public TradeBot(Outputter out, int autodelay) throws Exception {
        super();
        outputter = out;
        setAutoDelay(autodelay);
    }

    /**
     * Standard setter for the output messages
     * @param messagesToOutput - Array of messages to output
     */
    public void setMessagesToOutput(String[] messagesToOutput) {
        this.messagesToOutput = messagesToOutput;
    }

    /**
     * Adds all items in the current 4x4 grid of Steam Trade into the trade
     * Assumes the following:
     * - The Steam Trade window is the active window
     * - The user places the mouse in the center of the "next page" button
     * - The Steam Trade window is NOT scaled down at all
     *      (eg. small changes in window size do not make buttons or items shrink or grow in size)
     * @throws Exception - for the Robot
     */
    public void tradePage() throws Exception {
        // The next button is located at the current mouse location
        Point nextButton = MouseInfo.getPointerInfo().getLocation();
        // Compute the relative location of the first item
        Point firstItemLoc = new Point(nextButton.x-350, nextButton.y-380);

        // Click every item in the 4x4 grid
        for (int j=0;j<4;j++){
            for (int i=0;i<4;i++){
                // Add the item
                doubleClick((firstItemLoc.x+(spacing*i)),(firstItemLoc.y+(spacing*j)));
            }
        }
        verifyPageUpTo(4, 4, firstItemLoc);
    }

    /**
     * Adds the specified number of items from the 4x4 grid of filtered items on Steam Trade into the trade
     * Assumes the following:
     * - The Steam Trade window is the active window
     * - The user places the mouse in the center of the "next page" button
     * - The Steam Trade window is NOT scaled down at all
     *      (eg. small changes in window size do not make buttons or items shrink or grow in size)
     * @param numOfItemsToTrade - an integer value representing the desired number of items to add
     * @throws Exception - for the Robot
     */
    public void tradeItemsInFilter(int numOfItemsToTrade) throws Exception {
        int numOfItemsToVerify = numOfItemsToTrade;
        // Compute the number of pages needed
        int numPages = (int) Math.ceil(numOfItemsToTrade/16f);
        // The next button is located at the current mouse location
        Point nextButton = MouseInfo.getPointerInfo().getLocation();
        // Compute the relative location of the first item
        Point firstItemLoc = new Point(nextButton.x-350, nextButton.y-380);

        // Click every item on the page until a new page is required, or you have added the correct number of items
        outerloop:
        for (int k=0; k<numPages;k++){
            for (int j=0;j<4;j++){
                for (int i=0;i<4;i++){
                    // Add the item
                    doubleClick((firstItemLoc.x+(spacing*i)),(firstItemLoc.y+(spacing*j)));
                    // Decrement the number of items remaining
                    numOfItemsToTrade--;
                    // Break out of the loop if we have added them all
                    if(numOfItemsToTrade <= 0){
                        verifyPageUpTo(i, j, firstItemLoc);
                        break outerloop;
                    }
                }
            }
            verifyPageUpTo(4, 4, firstItemLoc);
            // Go to the next page, give the window enough time to update the display
            leftClick(nextButton.x, nextButton.y);
            Thread.sleep(500);
        }
    }

    private void verifyPageUpTo(int a, int b, Point firstItemLoc) throws Exception{
        outerloop:
        for (int j=0;j<4;j++){
            for (int i=0;i<4;i++){
                Color boxColor = getPixelColor((firstItemLoc.x+(spacing*i)),(firstItemLoc.y+(spacing*j)));
                if(!boxColor.equals(new Color(41, 41, 41))){
                    doubleClick((firstItemLoc.x+(spacing*i)),(firstItemLoc.y+(spacing*j)));
                }
                // Break out of the loop if we have added them all
                if(j >= b && i >= a){
                    break outerloop;
                }
            }
        }
    }

    /**
     * Adds the specified number of pages of the 4x4 grid of filtered items on Steam Trade into the trade
     * Assumes the following:
     * - The Steam Trade window is the active window
     * - The user places the mouse in the center of the "next page" button
     * - The Steam Trade window is NOT scaled down at all
     *      (eg. small changes in window size do not make buttons or items shrink or grow in size)
     * @param numPages - an integer value representing the number of pages to add
     * @throws Exception - for the Robot
     */
    public void tradePagesOfFilter(int numPages) throws Exception {
        // The next button is located at the current mouse location
        Point nextButton = MouseInfo.getPointerInfo().getLocation();
        // Compute the relative location of the first item
        Point firstItemLoc = new Point(nextButton.x-350, nextButton.y-380);

        // Add every item in on the page, as many times as specified
        for (int k=0; k<numPages;k++){
            for (int j=0;j<4;j++){
                for (int i=0;i<4;i++){
                    // Add the item
                    doubleClick((firstItemLoc.x+(spacing*i)),(firstItemLoc.y+(spacing*j)));
                }
            }
            verifyPageUpTo(4, 4, firstItemLoc);
            // Go to the next page, give the window enough time to update the display
            leftClick(nextButton.x, nextButton.y);
            Thread.sleep(500);
        }
    }

    /**
     * This function will continuously output messages until the process is terminated
     * Intended use: automatic advertisement while playing on a Trade Server
     * Notes:
     * - Obviously must be shut off once entering a trade, etc.
     * - Will briefly pause the game to notify user that a message is about to be output
     *      (this allows the user to stop playing briefly to prevent messing up the message)
     * - Will display errors is a message is too long to type in chat
     * @throws Exception - for the Robot
     */
    public void outputMessages() throws Exception {
        // Define the maximum number of character that can be entered
        Integer maxChatChar = 127;
        // Set the auto delay to give the user a larger time-cushion
        setAutoDelay(500);
        // Verify messages have been specifed prior (by using setMessagesToOutput())
        if(messagesToOutput == null){
            outputter.output("Messages have not been set.");
        }
        else {
            // Validate the length of the messages
            ArrayList<Integer> badStringNums = new ArrayList<Integer>();
            for (int i=0; i<messagesToOutput.length; i++){
                if(messagesToOutput[i].length() > maxChatChar){
                    badStringNums.add(i);
                }
            }
            // Continue if all messages are valid
            if (badStringNums.size() == 0) {
                // Seed a random
                Random rand = new Random();
                // Loop until the user terminates the process manually
                while (true) {
                    // Pause briefly to warn user that output is about to begin
                    commander("ESC");
                    commanderRelease("ESC");
                    commander("ESC");
                    commanderRelease("ESC");
                    Thread.sleep(1000);
                    // Bring up the chat window
                    output("y");
                    Thread.sleep(100);
                    // Type out a message
                    output(messagesToOutput[rand.nextInt(messagesToOutput.length)]);
                    commander("ENTER");
                    // Wait a while before next output
                    Thread.sleep(20*1000);                  // 20 seconds
                    Thread.sleep(rand.nextInt(10)*1000);    // Plus a random number of seconds between 0-9
                }
            }
            else {
                // Display errors and exit
                outputter.output("The bot stopped for the following reason(s):");
                for (Integer badNum : badStringNums) {
                    outputter.output("Message number " + badNum + " is too long to type in chat.");
                }
            }
        }
    }
}
