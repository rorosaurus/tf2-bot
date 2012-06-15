package ui;

import pojos.Settings;
import providers.SettingsProvider;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * User: Rory
 * Date: 3/13/12
 * Time: 7:42 PM
 */

public class ScrapButton extends Button implements ItemListener {

    private boolean simulate = true;
    private SettingsProvider settingsProvider;

    public ScrapButton(String s, SettingsProvider provider) throws Exception {
        super(s);
        settingsProvider = provider;
        if(settingsProvider.getSetting(Settings.simulate) != null){
            simulate = Boolean.parseBoolean(settingsProvider.getSetting(Settings.simulate));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        simulate = itemEvent.getStateChange() != ItemEvent.DESELECTED;
        settingsProvider.putSetting(Settings.simulate, simulate+"");
        settingsProvider.writeProperties();
    }

    public boolean getSimulate() {
        return simulate;
    }
}
