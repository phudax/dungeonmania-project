package dungeonmania.entities.inventory;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;

public class InventoryManagement {
    private Crafting crafting = new Crafting();

    public InventoryItem buildBow(List<InventoryItem> items, boolean remove, EntityFactory factory) {
        List<Wood> wood = getEntities(items, Wood.class);
        List<Arrow> arrows = getEntities(items, Arrow.class);

        if (crafting.canCraftBow(wood.size(), arrows.size()) && remove) {
            removeItemsForBow(items);
            return factory.buildBow();
        }

        return null;
    }

    public InventoryItem buildShield(List<InventoryItem> items, boolean remove, EntityFactory factory) {
        List<Wood> wood = getEntities(items, Wood.class);
        List<Treasure> treasure = getEntities(items, Treasure.class);
        List<Key> keys = getEntities(items, Key.class);
        List<SunStone> sunstones = getEntities(items, SunStone.class);
        treasure.removeIf(item -> item instanceof SunStone);
        if (crafting.canCraftShield(treasure.size(), keys.size(), wood.size(), sunstones.size()) && remove) {
            removeItemsForShield(items, treasure, keys);
            return factory.buildShield();
        }

        return null;
    }

    public InventoryItem buildSceptre(List<InventoryItem> items, boolean remove, EntityFactory factory) {
        List<Wood> wood = getEntities(items, Wood.class);
        List<Arrow> arrows = getEntities(items, Arrow.class);
        List<Treasure> treasure = getEntities(items, Treasure.class);
        List<Key> keys = getEntities(items, Key.class);
        List<SunStone> sunstones = getEntities(items, SunStone.class);
        treasure.removeIf(item -> item instanceof SunStone);
        if (crafting.canCraftSceptre(wood.size(), arrows.size(), treasure.size(), keys.size(), sunstones.size())
                && remove) {
            removeItemsForSceptre(items, wood, arrows, treasure, keys, sunstones);
            return factory.buildSceptre();
        }

        return null;
    }

    public InventoryItem buildMidnightArmour(List<InventoryItem> items, boolean remove, EntityFactory factory) {
        List<Sword> swords = getEntities(items, Sword.class);
        List<SunStone> sunstones = getEntities(items, SunStone.class);

        if (crafting.canCraftMidnightArmour(swords.size(), sunstones.size()) && remove) {
            removeItemsForMidnightArmour(items, swords, sunstones);
            return factory.buildMidnightArmour();
        }

        return null;
    }

    public void removeItemsForBow(List<InventoryItem> items) {
        List<Wood> wood = getEntities(items, Wood.class);
        List<Arrow> arrows = getEntities(items, Arrow.class);
        items.remove(wood.get(0));
        items.remove(arrows.get(0));
        items.remove(arrows.get(1));
        items.remove(arrows.get(2));
    }

    public void removeItemsForShield(List<InventoryItem> items, List<Treasure> treasure, List<Key> keys) {
        treasure.removeIf(item -> item instanceof SunStone);
        List<Wood> wood = getEntities(items, Wood.class);
        items.remove(wood.get(0));
        items.remove(wood.get(1));

        if (treasure.size() >= 1) {
            items.remove(treasure.get(0));
        } else if (keys.size() >= 1) {
            items.remove(keys.get(0));
        }
    }

    public void removeItemsForSceptre(List<InventoryItem> items, List<Wood> wood, List<Arrow> arrows,
            List<Treasure> treasure, List<Key> keys, List<SunStone> sunstones) {
        treasure.removeIf(item -> item instanceof SunStone);
        if (wood.size() >= 1) {
            items.remove(wood.get(0));
        } else {
            items.remove(arrows.get(0));
            items.remove(arrows.get(1));
        }

        if (treasure.size() >= 1) {
            items.remove(treasure.get(0));
        } else if (keys.size() >= 1) {
            items.remove(keys.get(0));
        }

        items.remove(sunstones.get(0));
    }

    public void removeItemsForMidnightArmour(List<InventoryItem> items, List<Sword> swords, List<SunStone> sunstones) {
        items.remove(swords.get(0));
        items.remove(sunstones.get(0));

    }

    private <T extends InventoryItem> List<T> getEntities(List<InventoryItem> items, Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }
}
