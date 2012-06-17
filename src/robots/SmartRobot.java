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

package robots;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * User: Rory
 * Date: 3/2/12
 * Time: 4:50 PM
 */

public class SmartRobot extends Robot{

    private int numOfLeftClicks = 0;

    public String[] validCommands = {"CTRL", "ALT", "DEL", "ESC", "TAB", "CAPS", "SHIFT", "UP", "DOWN", "LEFT", "RIGHT",
            "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "ENTER", "INSERT", "DELETE", "HOME",
            "END", "PGUP", "PGDWN", "NUMLOCK", "WIN", "PAUSE"};

    public SmartRobot() throws AWTException {
        super();
    }

    public void output(String message){
        output(message, 0);
    }

    public void output(String message, int delay){
        int saveDelay = getAutoDelay();
        setAutoDelay(1);
        char [] mess = message.toCharArray();
        delay(delay);
        for(int i = 0; i < message.length(); i++){
            int charCode = getCharCode(mess[i]);
            if(charCode != KeyEvent.VK_SHIFT){
                keyPress(getCharCode(mess[i]));
                keyRelease(getCharCode(mess[i]));
            }
            else {
                keyPress(KeyEvent.VK_SHIFT);
                keyPress(getShiftCode(mess[i]));
                keyRelease(getShiftCode(mess[i]));
                keyRelease(KeyEvent.VK_SHIFT);
            }
        }
        setAutoDelay(saveDelay);
    }

    //commands will be comma delimited with NO spaces eg. CTRL,ALT,DEL
    public void commander(String commands){
        commander(commands, 0);
    }

    public void commander(String commands, int delay){
        char [] comm = commands.toCharArray();
        StringBuffer command = new StringBuffer();
        int start = 0;
        delay(delay);
        for(int i = 0; i < comm.length; i++){
            int end = i;
            if(comm[i] == ','){
                command.append(commands.substring(start,end));
                for(int index = 0; index < validCommands.length; index++){
                    if(command.toString().equals(validCommands[index])){
                        keyPress(getCommandCode(command.toString()));
                    }
                }
                command.delete(0, command.length());
                start = ++end;
            }
            if(i == comm.length-1){
                command.append(commands.substring(start, end+1));
                for(int index = 0; index < validCommands.length; index++){
                    if(command.toString().equals(validCommands[index])){
                        keyPress(getCommandCode(command.toString()));
                    }
                }
                command.delete(0, command.length());
            }
        }
    }

    public void commanderRelease(String commands){
        commanderRelease(commands, 0);
    }

    public void commanderRelease(String commands, int delay){
        char [] comm = commands.toCharArray();
        StringBuffer command = new StringBuffer();
        int start = 0;
        for(int i = 0; i < comm.length; i++){
            int end = i;
            if(comm[i] == ','){
                command.append(commands.substring(start,end));
                for(int index = 0; index < validCommands.length; index++){
                    if(command.toString().equals(validCommands[index])){
                        keyRelease(getCommandCode(command.toString()));
                    }
                }
                command.delete(0, command.length());
                start = ++end;
            }
            if(i == comm.length-1){
                command.append(commands.substring(start, end+1));
                for(int index = 0; index < validCommands.length; index++){
                    if(command.toString().equals(validCommands[index])){
                        keyRelease(getCommandCode(command.toString()));
                    }
                }
                command.delete(0, command.length());
            }
        }
    }

    public void run(String executable){
        commander("WIN");
        output("r");
        commanderRelease("WIN");
        output(executable);
        commander("ENTER");
        commanderRelease("ENTER");
    }

    public void openDir(String dir){
        commander("WIN");
        output("r");
        commanderRelease("WIN");
        output(dir);
        commander("ENTER");
        commanderRelease("ENTER");
    }

    public int getCommandCode(String command) {
        int commandCode;
        if(command.equals("CTRL"))
            commandCode = KeyEvent.VK_CONTROL;
        else if(command.equals("ALT"))
            commandCode = KeyEvent.VK_ALT;
        else if(command.equals("DEL"))
            commandCode = KeyEvent.VK_DELETE;
        else if(command.equals("ESC"))
            commandCode = KeyEvent.VK_ESCAPE;
        else if(command.equals("TAB"))
            commandCode = KeyEvent.VK_TAB;
        else if(command.equals("CAPS"))
            commandCode = KeyEvent.VK_CAPS_LOCK;
        else if(command.equals("SHIFT"))
            commandCode = KeyEvent.VK_SHIFT;
        else if(command.equals("UP"))
            commandCode = KeyEvent.VK_UP;
        else if(command.equals("DOWN"))
            commandCode = KeyEvent.VK_DOWN;
        else if(command.equals("LEFT"))
            commandCode = KeyEvent.VK_LEFT;
        else if(command.equals("RIGHT"))
            commandCode = KeyEvent.VK_RIGHT;
        else if(command.equals("F1"))
            commandCode = KeyEvent.VK_F1;
        else if(command.equals("F2"))
            commandCode = KeyEvent.VK_F2;
        else if(command.equals("F3"))
            commandCode = KeyEvent.VK_F3;
        else if(command.equals("F4"))
            commandCode = KeyEvent.VK_F4;
        else if(command.equals("F5"))
            commandCode = KeyEvent.VK_F5;
        else if(command.equals("F6"))
            commandCode = KeyEvent.VK_F6;
        else if(command.equals("F7"))
            commandCode = KeyEvent.VK_F7;
        else if(command.equals("F8"))
            commandCode = KeyEvent.VK_F8;
        else if(command.equals("F9"))
            commandCode = KeyEvent.VK_F9;
        else if(command.equals("F10"))
            commandCode = KeyEvent.VK_F10;
        else if(command.equals("F11"))
            commandCode = KeyEvent.VK_F11;
        else if(command.equals("F12"))
            commandCode = KeyEvent.VK_F12;
        else if(command.equals("ENTER"))
            commandCode = KeyEvent.VK_ENTER;
        else if(command.equals("INSERT"))
            commandCode = KeyEvent.VK_INSERT;
        else if(command.equals("DELETE"))
            commandCode = KeyEvent.VK_DELETE;
        else if(command.equals("HOME"))
            commandCode = KeyEvent.VK_HOME;
        else if(command.equals("END"))
            commandCode = KeyEvent.VK_END;
        else if(command.equals("PGUP"))
            commandCode = KeyEvent.VK_PAGE_UP;
        else if(command.equals("PGDWN"))
            commandCode = KeyEvent.VK_PAGE_DOWN;
        else if(command.equals("NUMLOCK"))
            commandCode = KeyEvent.VK_NUM_LOCK;
        else if(command.equals("WIN"))
            commandCode = KeyEvent.VK_WINDOWS;
        else if(command.equals("PAUSE"))
            commandCode = KeyEvent.VK_PAUSE;
        else
            throw new IllegalArgumentException("Cannot press that key " + command);
        return commandCode;
    }

    public int getShiftCode(char character) {
        int charCode;
        switch(character){
            case 'A': charCode = KeyEvent.VK_A; break;
            case 'B': charCode = KeyEvent.VK_B; break;
            case 'C': charCode = KeyEvent.VK_C; break;
            case 'D': charCode = KeyEvent.VK_D; break;
            case 'E': charCode = KeyEvent.VK_E; break;
            case 'F': charCode = KeyEvent.VK_F; break;
            case 'G': charCode = KeyEvent.VK_G; break;
            case 'H': charCode = KeyEvent.VK_H; break;
            case 'I': charCode = KeyEvent.VK_I; break;
            case 'J': charCode = KeyEvent.VK_J; break;
            case 'K': charCode = KeyEvent.VK_K; break;
            case 'L': charCode = KeyEvent.VK_L; break;
            case 'M': charCode = KeyEvent.VK_M; break;
            case 'N': charCode = KeyEvent.VK_N; break;
            case 'O': charCode = KeyEvent.VK_O; break;
            case 'P': charCode = KeyEvent.VK_P; break;
            case 'Q': charCode = KeyEvent.VK_Q; break;
            case 'R': charCode = KeyEvent.VK_R; break;
            case 'S': charCode = KeyEvent.VK_S; break;
            case 'T': charCode = KeyEvent.VK_T; break;
            case 'U': charCode = KeyEvent.VK_U; break;
            case 'V': charCode = KeyEvent.VK_V; break;
            case 'W': charCode = KeyEvent.VK_W; break;
            case 'X': charCode = KeyEvent.VK_X; break;
            case 'Y': charCode = KeyEvent.VK_Y; break;
            case 'Z': charCode = KeyEvent.VK_Z; break;
            case '!': charCode = KeyEvent.VK_1;break;
            case '?': charCode = KeyEvent.VK_SLASH; break;
            case '<': charCode = KeyEvent.VK_COMMA; break;
            case '>': charCode = KeyEvent.VK_PERIOD; break;
            case '~': charCode = 192; break;
            case '@': charCode = KeyEvent.VK_2; break;
            case '#': charCode = KeyEvent.VK_3; break;
            case '$': charCode = KeyEvent.VK_4; break;
            case '%': charCode = KeyEvent.VK_5; break;
            case '^': charCode = KeyEvent.VK_6; break;
            case '&': charCode = KeyEvent.VK_7; break;
            case '*': charCode = KeyEvent.VK_8; break;
            case '(': charCode = KeyEvent.VK_9; break;
            case ')': charCode = KeyEvent.VK_0; break;
            case '_': charCode = KeyEvent.VK_MINUS; break;
            case '+': charCode = KeyEvent.VK_EQUALS; break;
            case '{': charCode = KeyEvent.VK_OPEN_BRACKET; break;
            case '}': charCode = KeyEvent.VK_CLOSE_BRACKET; break;
            case '|': charCode = KeyEvent.VK_BACK_SLASH; break;
            case ':': charCode = KeyEvent.VK_SEMICOLON; break;
            case '\"': charCode = KeyEvent.VK_QUOTE; break;
            default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
        return charCode;
    }

    public int getCharCode(char character) {
        int charCode;
        switch (character) {
            case 'a': charCode = KeyEvent.VK_A; break;
            case 'b': charCode = KeyEvent.VK_B; break;
            case 'c': charCode = KeyEvent.VK_C; break;
            case 'd': charCode = KeyEvent.VK_D; break;
            case 'e': charCode = KeyEvent.VK_E; break;
            case 'f': charCode = KeyEvent.VK_F; break;
            case 'g': charCode = KeyEvent.VK_G; break;
            case 'h': charCode = KeyEvent.VK_H; break;
            case 'i': charCode = KeyEvent.VK_I; break;
            case 'j': charCode = KeyEvent.VK_J; break;
            case 'k': charCode = KeyEvent.VK_K; break;
            case 'l': charCode = KeyEvent.VK_L; break;
            case 'm': charCode = KeyEvent.VK_M; break;
            case 'n': charCode = KeyEvent.VK_N; break;
            case 'o': charCode = KeyEvent.VK_O; break;
            case 'p': charCode = KeyEvent.VK_P; break;
            case 'q': charCode = KeyEvent.VK_Q; break;
            case 'r': charCode = KeyEvent.VK_R; break;
            case 's': charCode = KeyEvent.VK_S; break;
            case 't': charCode = KeyEvent.VK_T; break;
            case 'u': charCode = KeyEvent.VK_U; break;
            case 'v': charCode = KeyEvent.VK_V; break;
            case 'w': charCode = KeyEvent.VK_W; break;
            case 'x': charCode = KeyEvent.VK_X; break;
            case 'y': charCode = KeyEvent.VK_Y; break;
            case 'z': charCode = KeyEvent.VK_Z; break;
            case 'A': charCode = KeyEvent.VK_SHIFT; break;
            case 'B': charCode = KeyEvent.VK_SHIFT; break;
            case 'C': charCode = KeyEvent.VK_SHIFT; break;
            case 'D': charCode = KeyEvent.VK_SHIFT; break;
            case 'E': charCode = KeyEvent.VK_SHIFT; break;
            case 'F': charCode = KeyEvent.VK_SHIFT; break;
            case 'G': charCode = KeyEvent.VK_SHIFT; break;
            case 'H': charCode = KeyEvent.VK_SHIFT; break;
            case 'I': charCode = KeyEvent.VK_SHIFT; break;
            case 'J': charCode = KeyEvent.VK_SHIFT; break;
            case 'K': charCode = KeyEvent.VK_SHIFT; break;
            case 'L': charCode = KeyEvent.VK_SHIFT; break;
            case 'M': charCode = KeyEvent.VK_SHIFT; break;
            case 'N': charCode = KeyEvent.VK_SHIFT; break;
            case 'O': charCode = KeyEvent.VK_SHIFT; break;
            case 'P': charCode = KeyEvent.VK_SHIFT; break;
            case 'Q': charCode = KeyEvent.VK_SHIFT; break;
            case 'R': charCode = KeyEvent.VK_SHIFT; break;
            case 'S': charCode = KeyEvent.VK_SHIFT; break;
            case 'T': charCode = KeyEvent.VK_SHIFT; break;
            case 'U': charCode = KeyEvent.VK_SHIFT; break;
            case 'V': charCode = KeyEvent.VK_SHIFT; break;
            case 'W': charCode = KeyEvent.VK_SHIFT; break;
            case 'X': charCode = KeyEvent.VK_SHIFT; break;
            case 'Y': charCode = KeyEvent.VK_SHIFT; break;
            case 'Z': charCode = KeyEvent.VK_SHIFT; break;
            case '`': charCode = 192; break;
            case '0': charCode = KeyEvent.VK_0; break;
            case '1': charCode = KeyEvent.VK_1; break;
            case '2': charCode = KeyEvent.VK_2; break;
            case '3': charCode = KeyEvent.VK_3; break;
            case '4': charCode = KeyEvent.VK_4; break;
            case '5': charCode = KeyEvent.VK_5; break;
            case '6': charCode = KeyEvent.VK_6; break;
            case '7': charCode = KeyEvent.VK_7; break;
            case '8': charCode = KeyEvent.VK_8; break;
            case '9': charCode = KeyEvent.VK_9; break;
            case '-': charCode = KeyEvent.VK_MINUS; break;
            case '=': charCode = KeyEvent.VK_EQUALS; break;
            case '[': charCode = KeyEvent.VK_OPEN_BRACKET; break;
            case ']': charCode = KeyEvent.VK_CLOSE_BRACKET; break;
            case '\\': charCode = KeyEvent.VK_BACK_SLASH; break;
            case ';': charCode = KeyEvent.VK_SEMICOLON; break;
            case '\'': charCode = KeyEvent.VK_QUOTE; break;
            case '/': charCode = KeyEvent.VK_SLASH; break;
            case '.': charCode = KeyEvent.VK_PERIOD; break;
            case ',': charCode = KeyEvent.VK_COMMA; break;
            case '!': charCode = KeyEvent.VK_SHIFT; break;
            case '?': charCode = KeyEvent.VK_SHIFT; break;
            case '<': charCode = KeyEvent.VK_SHIFT; break;
            case '>': charCode = KeyEvent.VK_SHIFT; break;
            case '~': charCode = KeyEvent.VK_SHIFT; break;
            case '@': charCode = KeyEvent.VK_SHIFT; break;
            case '#': charCode = KeyEvent.VK_SHIFT; break;
            case '$': charCode = KeyEvent.VK_SHIFT; break;
            case '%': charCode = KeyEvent.VK_SHIFT; break;
            case '^': charCode = KeyEvent.VK_SHIFT; break;
            case '&': charCode = KeyEvent.VK_SHIFT; break;
            case '*': charCode = KeyEvent.VK_SHIFT; break;
            case '(': charCode = KeyEvent.VK_SHIFT; break;
            case ')': charCode = KeyEvent.VK_SHIFT; break;
            case '_': charCode = KeyEvent.VK_SHIFT; break;
            case '+': charCode = KeyEvent.VK_SHIFT; break;
            case '{': charCode = KeyEvent.VK_SHIFT; break;
            case '}': charCode = KeyEvent.VK_SHIFT; break;
            case '|': charCode = KeyEvent.VK_SHIFT; break;
            case ':': charCode = KeyEvent.VK_SHIFT; break;
            case '\"': charCode = KeyEvent.VK_SHIFT; break;
            case ' ': charCode = KeyEvent.VK_SPACE; break;
            default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
        return charCode;
    }

    public void setFocus(int x, int y){
        mouseMove(x, y);
    }

    public void setFocus(int x, int y, int delay){
        delay(delay);
        mouseMove(x, y);
    }

    public void leftClick(){
        mousePress(InputEvent.BUTTON1_MASK);
        mouseRelease(InputEvent.BUTTON1_MASK);
        numOfLeftClicks++;
    }

    public void leftClick(int delay){
        delay(delay);
        mousePress(InputEvent.BUTTON1_MASK);
        mouseRelease(InputEvent.BUTTON1_MASK);
        numOfLeftClicks++;
    }

    public void leftClick(int x, int y){
        setFocus(x, y);
        leftClick();
    }

    public void leftClick(int x, int y, int delay){
        delay(delay);
        setFocus(x, y);
        leftClick();
    }

    public void leftClickNoRelease(){
        mousePress(InputEvent.BUTTON1_MASK);
    }

    public void leftClickNoRelease(int delay){
        delay(delay);
        mousePress(InputEvent.BUTTON1_MASK);
    }

    public void leftClickNoRelease(int x, int y){
        setFocus(x, y);
        leftClickNoRelease();
    }

    public void leftClickNoRelease(int x, int y, int delay){
        delay(delay);
        setFocus(x, y);
        leftClickNoRelease();
    }

    public void doubleClick(){
        leftClick(1);
        leftClick(1);
    }

    public void doubleClick(int delay){
        delay(delay);
        leftClick(1);
        leftClick(1);
    }

    public void doubleClick(int x, int y){
        setFocus(x,y);
        doubleClick();
    }

    public void doubleClick(int x, int y, int delay){
        delay(delay);
        setFocus(x,y);
        doubleClick();
    }

    public void rightClick(){
        mousePress(InputEvent.BUTTON3_MASK);
        mouseRelease(InputEvent.BUTTON3_MASK);
    }

    public void rightClick(int delay){
        delay(delay);
        mousePress(InputEvent.BUTTON3_MASK);
        mouseRelease(InputEvent.BUTTON3_MASK);
    }

    public void rightClick(int x, int y){
        setFocus(x,y);
        rightClick();
    }

    public void rightClick(int x, int y, int delay){
        delay(delay);
        setFocus(x,y);
        rightClick();
    }

    public void rightClickNoRelease(){
        keyPress(InputEvent.BUTTON3_MASK);
    }

    public void rightClickNoRelease(int delay){
        delay(delay);
        keyPress(InputEvent.BUTTON3_MASK);
    }

    public void rightClickNoRelease(int x, int y){
        setFocus(x,y);
        rightClickNoRelease();
    }

    public void rightClickNoRelease(int x, int y, int delay){
        delay(delay);
        setFocus(x,y);
        rightClickNoRelease();
    }

    public int getNumOfLeftClicks() {
        return numOfLeftClicks;
    }

    /**
     * Attempts to file the x,y location of a specified image in a screenshot
     * @param fileLoc - the location of the source image
     * @param screenshot - the screenshot to compare against
     * @return Point indicating x,y location of first instance found in the screenshot, or null if none is found
     */
    public Point findLocOfImage(String fileLoc, BufferedImage screenshot){


        // Get Image
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/src/" + fileLoc);
        Image image = icon.getImage();

        // Create empty BufferedImage, sized to Image
        BufferedImage buffImage =
                new BufferedImage(
                        image.getWidth(null),
                        image.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB);

        // Draw Image into BufferedImage
        Graphics g = buffImage.getGraphics();
        g.drawImage(image, 0, 0, null);

        Point location = new Point(-1, -1);

        outerloop:
        for (int y=0; y<screenshot.getHeight(); y++){
            for (int x=0; x<screenshot.getWidth(); x++){
                if(compareRGBs(screenshot.getRGB(x,y),buffImage.getRGB(0,0)) &&
                        (x+buffImage.getWidth() <= screenshot.getWidth()) &&
                        (y+buffImage.getHeight() <= screenshot.getHeight())){
                    boolean isValid = true;
                    // We have found something that matches the first pixel.  Now iterate through the source image.
                    innerloop:
                    for(int j=0; j<buffImage.getHeight(); j++){
                        for(int i=0; i<buffImage.getWidth(); i++){
                            if(!compareRGBs(screenshot.getRGB(x+i, y+j),buffImage.getRGB(i, j))){
                                isValid = false;
                                break innerloop;
                            }
                        }
                    }
                    if(isValid){
                        location.setLocation(x,y);
                        break outerloop;
                    }
                }
            }
        }
        return location;
    }

    // TODO: this would probably be more effective is RMS value is compared
    private boolean compareRGBs(int first, int second){
//        boolean same = true;

        int  firstred = (first & 0x00ff0000) >> 16;
        int  firstgreen = (first & 0x0000ff00) >> 8;
        int  firstblue = first & 0x000000ff;

        int  secondred = (second & 0x00ff0000) >> 16;
        int  secondgreen = (second & 0x0000ff00) >> 8;
        int  secondblue = second & 0x000000ff;

        int margin = 50;
        if(Math.abs(firstred - secondred) > margin) return false;
        if(Math.abs(firstgreen - secondgreen) > margin) return false;
        if(Math.abs(firstblue - secondblue) > margin) return false;

//        int margin = 90;
//        return Math.abs(firstred - secondred) + Math.abs(firstgreen - secondgreen) + Math.abs(firstblue - secondblue) <= margin;
return true;
    }
}
