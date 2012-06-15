package pojos;

import java.util.ArrayList;

/**
 * User: Rory
 * Date: 2/26/12
 * Time: 4:59 PM
 */

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
