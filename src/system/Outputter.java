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

package system;

import javax.swing.*;

// TODO: find a better way to do this, make it run in its own thread
public class Outputter {

    private JTextArea jTextArea;

    public Outputter(JTextArea area) {
        jTextArea = area;
    }

    public void output(String message) {
        jTextArea.setText(jTextArea.getText() + "\n" + message);
    }

    public void outputNewline() {
        jTextArea.setText(jTextArea.getText() + "\n");
    }
}
