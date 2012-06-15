package providers;

import pojos.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * User: Rory
 * Date: 3/13/12
 * Time: 8:43 PM
 */

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
