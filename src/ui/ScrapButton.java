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

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
