package dungeonmania.entities.inventory;

public class Crafting {
    public boolean canCraftBow(int wood, int arrow) {
        return wood >= 1 && arrow >= 3;
    }

    public boolean canCraftShield(int treasure, int key, int wood, int sunstone) {
        return wood >= 2 && (treasure >= 1 || key >= 1 || sunstone >= 1);
    }

    public boolean canCraftSceptre(int wood, int arrow, int treasure, int key, int sunstone) {
        return (wood >= 1 || arrow >= 2) && (treasure >= 1 || key >= 1 || sunstone >= 1) && sunstone >= 1;
    }

    public boolean canCraftMidnightArmour(int sword, int sunstone) {
        return sword >= 1 && sunstone >= 1;
    }
}
