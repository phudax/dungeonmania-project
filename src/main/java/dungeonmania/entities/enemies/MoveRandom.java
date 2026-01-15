package dungeonmania.entities.enemies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MoveRandom implements MoveStrategy {
    private Entity enemy;
    private Position nextPos;

    public MoveRandom(Entity enemy) {
        this.enemy = enemy;
    }

    public Position move(Game game) {
        GameMap map = game.getMap();
        Random randGen = new Random();
        List<Position> pos = enemy.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(enemy, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = enemy.getPosition();
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
        }
        return nextPos;
    }

}
