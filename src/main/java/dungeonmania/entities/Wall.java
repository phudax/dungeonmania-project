package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.enemies.Snake.Snake;
import dungeonmania.entities.enemies.Snake.SnakeBody;
import dungeonmania.entities.enemies.Snake.SnakeHead;
import dungeonmania.util.Position;

public class Wall extends Entity {
    public Wall(Position position) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (entity instanceof Spider) {
            return true;
        }

        if (entity instanceof Snake) {
            if (entity instanceof SnakeHead) {
                return map.getEntities(SnakeHead.class).get(0).getIsInvisible();
            }
            if (entity instanceof SnakeBody) {
                return map.getEntities(SnakeBody.class).get(0).getIsInvisible();
            }
        }

        return false;
    }

}
