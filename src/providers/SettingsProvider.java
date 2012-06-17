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

package providers;

import pojos.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class SettingsProvider {

    private final String FILENAME;
    private HashMap<Settings, String> settings = new HashMap<Settings, String>();

    public SettingsProvider(String filename) {
        FILENAME = filename;
        retrieveSettings();
    }

    private void retrieveSettings(){
        try {
        // Try to find a file
            File f = new File(FILENAME);
            Properties settingsProperties = new Properties();
            // If the file exists, attempt to use it
            if(f.exists()){
                // Read in properties
                FileInputStream in = new FileInputStream(FILENAME);
                settingsProperties.load(in);
                in.close();
                Set<String> propertyNames = settingsProperties.stringPropertyNames();
                // Set all points from the properties file
                settings = new HashMap<Settings, String>();
                for(String name : propertyNames){
                    for (Settings setting : Settings.values()){
                        if (name.equals(setting.toString())){
                            String value = settingsProperties.getProperty(name);
                            settings.put(setting, value);
                            break;
                        }
                    }
                }
            }
            // All points have been loaded or prompted for
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeProperties() {
        try {
            // Create file
            Properties pointProperties = new Properties();
            FileOutputStream out = new FileOutputStream(FILENAME);
            for(Settings property : settings.keySet()){
                pointProperties.setProperty(property.toString(), settings.get(property));
            }
            pointProperties.store(out, "---This file stores various settings---");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putSetting(Settings setting, String value){
        settings.put(setting, value);
    }

    public String getSetting(Settings setting){
        if(settings.containsKey(setting)) return settings.get(setting);
        else return null;
    }
}
