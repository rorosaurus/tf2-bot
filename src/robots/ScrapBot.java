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

import org.json.JSONArray;
import org.json.JSONObject;
import pojos.Metal;
import pojos.PointPlace;
import pojos.TF2Class;
import pojos.Weapon;
import providers.PointProvider;
import system.Outputter;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * User: Rory
 * Date: 3/2/12
 * Time: 4:50 PM
 */

/**
 * This bot handles functions like scrapping weapons, combine metals, and sorting the backpack
 */
public class ScrapBot extends SmartRobot {

    private final String BACKPACKBASEURL = "http://api.steampowered.com/ITFItems_440/GetPlayerItems/v0001/?steamID=STEAMIDGOESHERE&key=KEYGOESHERE";
    private final String SCHEMABASEURL = "http://api.steampowered.com/ITFItems_440/GetSchema/v0001/?language=en&key=KEYGOESHERE";

    // These MUST be replaced with a valid WebAPI key and the 64 bit id of your account
    private final String WEBKEY = "898102520FDEC3CBC13A211A8B951C6B";
    private final String ACCOUNT64BITID = "76561198043649643";

    private final PointProvider pointProvider;
    private Outputter outputter;

    private JSONArray schemaItems;
    private JSONArray backpackItems;
    private ArrayList<Weapon> items = new ArrayList<Weapon>();

    public ScrapBot(PointProvider provider, Outputter out) throws Exception {
        super();
        pointProvider = provider;
        outputter = out;
        // TODO: Experiment with delays lower than 100ms
        setAutoDelay(70);
    }

    private void getSchema() throws Exception {
        outputter.output("Retrieving Schema...");
        String schemaUrlString = SCHEMABASEURL.replace("KEYGOESHERE", WEBKEY);
        URL schemaURL = new URL(schemaUrlString);
        URLConnection connection = schemaURL.openConnection();
        String result = "";
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            result = result.concat(inputLine);
        in.close();
        JSONObject resultJson = new JSONObject(result);
        schemaItems = resultJson.getJSONObject("result").getJSONObject("items").getJSONArray("item");
        outputter.output("Schema retrieved.");
    }

    private void getBackpack() throws Exception {
        outputter.output("Retrieving Backpack...");
        String backpackUrlString = BACKPACKBASEURL.replace("KEYGOESHERE", WEBKEY).replace("STEAMIDGOESHERE", ACCOUNT64BITID);
        URL backpackURL = new URL(backpackUrlString);
        URLConnection connection = backpackURL.openConnection();
        String result = "";
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            result = result.concat(inputLine);
        in.close();
        JSONObject resultJson = new JSONObject(result);
        backpackItems = resultJson.getJSONObject("result").getJSONObject("items").getJSONArray("item");
        outputter.output("Backpack Retrieved.");
    }

    public void scrapWeapons(boolean verbose, boolean pretend) throws Exception {
        // TODO: Pretty sure I'll need to ensure that it's scrapping scrappable weapons.  eg. pull out genuines, etc.
        getSchema();
        getBackpack();

        final Point smeltWeapons = pointProvider.getPoint(PointPlace.smeltWeapons);

        outputter.output("Scrapping Weapons...");
        leftClick(smeltWeapons.x, smeltWeapons.y);

        // Populate the array with all items and the information we care about.
        int bitmask = 0xFFFF;
        for(int i=0; i<backpackItems.length(); i++){
            JSONObject item = backpackItems.getJSONObject(i);
            int inventoryValue = backpackItems.getJSONObject(i).getInt("inventory");
            Weapon currentItem = new Weapon();
            currentItem.setDefindex(item.getInt("defindex"));
            currentItem.setInventorySlot(inventoryValue & bitmask);
            items.add(currentItem);
        }

        // Fill in remaining information and remove non-weapons
        ArrayList<Weapon> newList = new ArrayList<Weapon>();
        for (Weapon item : items){
            for (int i=0; i<schemaItems.length();i++){
                JSONObject schemaItem = schemaItems.getJSONObject(i);
                if (item.getDefindex().equals(schemaItem.getInt("defindex")) && item.getInventorySlot()>0){
                    if(schemaItem.has("craft_material_type")){
                        if(schemaItem.getString("craft_material_type").equals("weapon")){
                            item.setName(schemaItem.getString("item_name"));
                            JSONArray classesJson = schemaItem.getJSONObject("used_by_classes").getJSONArray("class");
                            ArrayList<TF2Class> classes = new ArrayList<TF2Class>();
                            for (int j=0; j<classesJson.length();j++){
                                String tf2Class = classesJson.getString(j);
                                TF2Class currentClass = TF2Class.SCOUT;
                                if(tf2Class.equals("Scout")) currentClass = TF2Class.SCOUT;
                                else if(tf2Class.equals("Soldier")) currentClass = TF2Class.SOLIDER;
                                else if(tf2Class.equals("Pyro")) currentClass = TF2Class.PYRO;
                                else if(tf2Class.equals("Demoman")) currentClass = TF2Class.DEMOMAN;
                                else if(tf2Class.equals("Heavy")) currentClass = TF2Class.HEAVY;
                                else if(tf2Class.equals("Engineer")) currentClass = TF2Class.ENGINEER;
                                else if(tf2Class.equals("Medic")) currentClass = TF2Class.MEDIC;
                                else if(tf2Class.equals("Sniper")) currentClass = TF2Class.SNIPER;
                                else if(tf2Class.equals("Spy")) currentClass = TF2Class.SPY;
                                classes.add(currentClass);
                            }
                            item.setClasses(classes);
                            newList.add(item);
                        }
                    }
                }
            }
        }
        items = newList;
        
        // Now, compare and determine two items to scrap
        ArrayList<Weapon> itemsToIgnore = new ArrayList<Weapon>();
        for (Weapon item : items){
            // If the item is still there, it hasn't been used in crafting OR it hasn't been iterated to yet.
            if(!itemsToIgnore.contains(item)){
                Weapon match = null;
                for(Weapon hopefulMatch : items){
                    if (!hopefulMatch.equals(item) && !itemsToIgnore.contains(hopefulMatch)){
                        for(TF2Class tf2Class : item.getClasses()){
                            for(TF2Class hopefulClass : hopefulMatch.getClasses()){
                                if (tf2Class.equals(hopefulClass)){
                                    // These two weapons are compatible.  Scrap 'em!
                                    match = hopefulMatch;
                                    break;
                                }
                            }
                            if(match != null) break;
                        }
                    }
                    if(match != null) break;
                }
                // Come out here
                if (match != null){
                    // Scrap
                    if(verbose)outputter.output("Scrapping a " + item.getName() + " and a " + match.getName() + ".");
                    if(!pretend){
                        scrapWeapons(item, match);
                        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
                        Point ok2 = pointProvider.getPoint(PointPlace.ok2);
                        if(mouseLoc.getX() != ok2.x || mouseLoc.getY() != ok2.getY()) break;
                    }
                    itemsToIgnore.add(match);
                }
                itemsToIgnore.add(item);
            }
        }
        outputter.output("Weapons Scrapped.");
    }

    private void scrapWeapons(Weapon wep1, Weapon wep2) throws Exception {
        final Point item1 = pointProvider.getPoint(PointPlace.item1);
        final Point item2 = pointProvider.getPoint(PointPlace.item2);

        final Point viewBackpack = pointProvider.getPoint(PointPlace.viewBackpack);
        final Point nextButton = pointProvider.getPoint(PointPlace.nextButton);

        final Point firstSlot = pointProvider.getPoint(PointPlace.firstSlot);

        final int widthBetween = pointProvider.getPoint(PointPlace.spaceBetween).x;
        final int heightBetween = pointProvider.getPoint(PointPlace.spaceBetween).y;

        final Point craft = pointProvider.getPoint(PointPlace.craft);
        
        final Point ok1 = pointProvider.getPoint(PointPlace.ok1);
        final Point ok2 = pointProvider.getPoint(PointPlace.ok2);

        final Point samplingPoint = pointProvider.getPoint(PointPlace.samplingPoint);
        final Color samplingColor = new Color(41, 37, 38);
        
        leftClick(item1.x, item1.y);
        leftClick(viewBackpack.x, viewBackpack.y);
        // -1 because the first slot is invSlot 1
        int xOffset = (wep1.getInventorySlot()-1)%10;
        int yOffset = (wep1.getInventorySlot()-1)/10;
        if (yOffset>4){
            for (int i=0; i<(yOffset/5); i++){
                leftClick(nextButton.x, nextButton.y);
            }
        }
        leftClick(firstSlot.x+(xOffset*widthBetween), firstSlot.y+((yOffset%5)*heightBetween));

        leftClick(item2.x, item2.y);
        leftClick(viewBackpack.x, viewBackpack.y);
        // -1 because the first slot is invSlot 1
        int xOffset2 = (wep2.getInventorySlot()-1)%10;
        int yOffset2 = (wep2.getInventorySlot()-1)/10;
        if (yOffset2>4){
            for (int i=0; i<(yOffset2/5); i++){
                leftClick(nextButton.x, nextButton.y);
            }
        }
        leftClick(firstSlot.x+(xOffset2*widthBetween), firstSlot.y+((yOffset2%5)*heightBetween));

        leftClick(craft.x, craft.y);

        // Wait until done crafting
        Color currentColor = getPixelColor(samplingPoint.x, samplingPoint.y);
        while(currentColor.equals(samplingColor)){
            currentColor = getPixelColor(samplingPoint.x, samplingPoint.y);
        }
        leftClick(ok1.x, ok1.y);
        leftClick(ok2.x, ok2.y);
    }

    public void combineMetal(Metal metalType) throws Exception {
        final Point combineScrapButton = pointProvider.getPoint(PointPlace.combineScrapButton);
        final Point combineReclaimedButton = pointProvider.getPoint(PointPlace.combineReclaimedButton);

        final Point metal1 = pointProvider.getPoint(PointPlace.metal1);
        final Point metal2 = pointProvider.getPoint(PointPlace.metal2);
        final Point metal3 = pointProvider.getPoint(PointPlace.metal3);

        final Point craft = pointProvider.getPoint(PointPlace.craft);

        final Point empty = pointProvider.getPoint(PointPlace.empty);
        final Point nextItem = pointProvider.getPoint(PointPlace.nextItem);

        final Point ok1 = pointProvider.getPoint(PointPlace.ok1);
        final Point ok2 = pointProvider.getPoint(PointPlace.ok2);

        final Point samplingPoint = pointProvider.getPoint(PointPlace.samplingPoint);
        final Color samplingColor = new Color(41, 37, 38);

        final Color outOfMetal = new Color(42, 39, 37);

        // Select crafting option
        switch (metalType) {
            case SCRAP: outputter.output("Combining Scrap Metal...");
                leftClick(combineScrapButton.x, combineScrapButton.y);
                break;
            case RECLAIMED: outputter.output("Combining Reclaimed Metal...");
                leftClick(combineReclaimedButton.x, combineReclaimedButton.y);
                break;
            default: break;
        }

        while(true) {
            leftClick(metal1.x, metal1.y);
            if (getPixelColor(nextItem.x, nextItem.y).equals(outOfMetal)) break;
            leftClick(nextItem.x, nextItem.y);

            leftClick(metal2.x, metal2.y);
            if (getPixelColor(nextItem.x, nextItem.y).equals(outOfMetal)) break;
            leftClick(nextItem.x, nextItem.y);

            leftClick(metal3.x, metal3.y);
            if (getPixelColor(nextItem.x, nextItem.y).equals(outOfMetal)) break;
            leftClick(nextItem.x, nextItem.y);

            leftClick(craft.x, craft.y);

            // Wait until done crafting
            Color currentColor = getPixelColor(samplingPoint.x, samplingPoint.y);
            while(currentColor.equals(samplingColor)){
                currentColor = getPixelColor(samplingPoint.x, samplingPoint.y);
            }
            leftClick(ok1.x, ok1.y);
            leftClick(ok2.x, ok2.y);
        }
        leftClick(empty.x, empty.y);
        outputter.output("Metal Combined.");
    }

    public void sortBackpack() throws Exception{
            final Point backButton = pointProvider.getPoint(PointPlace.backButton);
            final Point backpackButton = pointProvider.getPoint(PointPlace.backpackButton);
            final Point sortButton = pointProvider.getPoint(PointPlace.sortButton);
            final Point sortByClass = pointProvider.getPoint(PointPlace.sortByClass);
            final Point outOfTheWay = pointProvider.getPoint(PointPlace.outOfTheWay);

            outputter.output("Sorting Backpack...");
            leftClick(backButton.x, backButton.y);
            leftClick(backpackButton.x, backpackButton.y);
            leftClick(sortButton.x, sortButton.y);
            leftClick(sortByClass.x, sortByClass.y);
            mouseMove(outOfTheWay.x, outOfTheWay.y);
            outputter.output("Backpack Sorted.");
    }
}
