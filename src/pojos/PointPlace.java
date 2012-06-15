package pojos;

/**
 * User: Rory
 * Date: 3/13/12
 * Time: 3:20 PM
 */

public enum PointPlace {
    smeltWeapons,

    item1,
    item2,

    metal1,
    metal2,
    metal3,

    viewBackpack,
    nextButton,
    firstSlot,
    spaceBetween,

    craft,
    ok1,
    ok2,

    samplingPoint,

    combineScrapButton,
    combineReclaimedButton,

    empty,
    nextItem,

    backButton,
    backpackButton,
    sortButton,
    sortByClass,
    outOfTheWay;

    public String getName(PointPlace place){
        return place.toString();
    }
}
