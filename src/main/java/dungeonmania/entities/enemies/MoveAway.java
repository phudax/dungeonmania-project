package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MoveAway implements MoveStrategy {
    private Entity enemy;
    private Position nextPos;

    public MoveAway(Entity enemy) {
        this.enemy = enemy;
    }

    public Position move(Game game) {
        GameMap map = game.getMap();
        Position plrDiff = Position.calculatePositionBetween(map.getPlayer().getPosition(), enemy.getPosition());

        Position moveX = (plrDiff.getX() >= 0) ? Position.translateBy(enemy.getPosition(), Direction.RIGHT)
                : Position.translateBy(enemy.getPosition(), Direction.LEFT);
        Position moveY = (plrDiff.getY() >= 0) ? Position.translateBy(enemy.getPosition(), Direction.UP)
                : Position.translateBy(enemy.getPosition(), Direction.DOWN);
        Position offset = enemy.getPosition();
        if (plrDiff.getY() == 0 && map.canMoveTo(enemy, moveX))
            offset = moveX;
        else if (plrDiff.getX() == 0 && map.canMoveTo(enemy, moveY))
            offset = moveY;
        else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
            if (map.canMoveTo(enemy, moveX))
                offset = moveX;
            else if (map.canMoveTo(enemy, moveY))
                offset = moveY;
        } else {
            if (map.canMoveTo(enemy, moveY))
                offset = moveY;
            else if (map.canMoveTo(enemy, moveX))
                offset = moveX;
        }
        nextPos = offset;
        return nextPos;
    }
}
