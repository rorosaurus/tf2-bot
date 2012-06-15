package system;

import javax.swing.*;

/**
 * User: Rory
 * Date: 3/13/12
 * Time: 7:16 PM
 */

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
