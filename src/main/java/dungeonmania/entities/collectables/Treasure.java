package dungeonmania.entities.collectables;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Snake.SnakeBuffs;
import dungeonmania.entities.enemies.Snake.SnakeHead;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Treasure extends Collectable implements InventoryItem, SnakeFood, SnakeStatBuff {
    public static final int DEFAULT_SNAKE_BUFF = 1;

    public Treasure(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this))
                return;
            map.destroyEntity(this);
        }

        if (entity instanceof SnakeHead) {
            SnakeHead head = map.getEntities(SnakeHead.class).get(0);
            head.consume(map, this);
            map.destroyEntity(this);
        }
    }

    @Override
    public void applyBuff(BattleStatistics origin, SnakeBuffs buffs) {
        origin.setHealth(origin.getHealth() + buffs.getSnakeTreasureBuff());
    }
}
