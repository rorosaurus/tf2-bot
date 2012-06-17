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

package pojos;

import java.util.ArrayList;

public class Weapon {
    
    private String name;
    private Integer defindex;
    private Integer inventorySlot;
    private ArrayList<TF2Class> classes;
    
    public Weapon() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDefindex() {
        return defindex;
    }

    public void setDefindex(Integer defindex) {
        this.defindex = defindex;
    }

    public Integer getInventorySlot() {
        return inventorySlot;
    }

    public void setInventorySlot(Integer inventorySlot) {
        this.inventorySlot = inventorySlot;
    }

    public ArrayList<TF2Class> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<TF2Class> classes) {
        this.classes = classes;
    }
}
