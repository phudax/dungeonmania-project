package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.util.Position;

public interface MoveStrategy {

    public Position move(Game game);

}
