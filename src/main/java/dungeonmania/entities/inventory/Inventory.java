package dungeonmania.entities.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;

public class Inventory {
    // Buildable strings
    public static final String BOW = "bow";
    public static final String SHIELD = "shield";
    public static final String SCEPTRE = "sceptre";
    public static final String MIDNIGHT_ARMOUR = "midnight_armour";
    private List<InventoryItem> items = new ArrayList<>();
    private Crafting crafting = new Crafting();
    private InventoryManagement management = new InventoryManagement();

    public boolean add(InventoryItem item) {
        items.add(item);
        return true;
    }

    public void remove(InventoryItem item) {
        items.remove(item);
    }

    public List<String> getBuildables() {
        int sunstones = count(SunStone.class);
        int wood = count(Wood.class);
        int arrows = count(Arrow.class);
        int treasure = count(Treasure.class) - sunstones;
        int keys = count(Key.class);
        int swords = count(Sword.class);
        List<String> result = new ArrayList<>();

        if (crafting.canCraftBow(wood, arrows)) {
            result.add(BOW);
        }
        if (crafting.canCraftShield(treasure, keys, wood, sunstones)) {
            result.add(SHIELD);
        }
        if (crafting.canCraftSceptre(wood, arrows, treasure, keys, sunstones)) {
            result.add(SCEPTRE);
        }
        if (crafting.canCraftMidnightArmour(swords, sunstones)) {
            result.add(MIDNIGHT_ARMOUR);
        }
        return result;
    }

    public InventoryItem checkBuildCriteria(Player p, boolean remove, String entity, EntityFactory factory) {

        switch (entity) {
        case BOW:
            return management.buildBow(items, remove, factory);
        case SHIELD:
            return management.buildShield(items, remove, factory);
        case SCEPTRE:
            return management.buildSceptre(items, remove, factory);
        case MIDNIGHT_ARMOUR:
            return management.buildMidnightArmour(items, remove, factory);
        default:
            return null;
        }

    }

    public <T extends InventoryItem> T getFirst(Class<T> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                return itemType.cast(item);
        return null;
    }

    public <T extends InventoryItem> int count(Class<T> itemType) {
        int count = 0;
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                count++;
        return count;
    }

    public Entity getEntity(String itemUsedId) {
        for (InventoryItem item : items)
            if (((Entity) item).getId().equals(itemUsedId))
                return (Entity) item;
        return null;
    }

    public List<Entity> getEntities() {
        return items.stream().map(Entity.class::cast).collect(Collectors.toList());
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

    public boolean hasWeapon() {
        return getFirst(Sword.class) != null || getFirst(Bow.class) != null;
    }

    public BattleItem getWeapon() {
        BattleItem weapon = getFirst(Sword.class);
        if (weapon == null)
            return getFirst(Bow.class);
        return weapon;
    }

}
