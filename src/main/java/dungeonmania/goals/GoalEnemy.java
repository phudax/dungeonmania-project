package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;

public class GoalEnemy implements Goal {
    private int enemyGoal;

    public GoalEnemy(int enemyGoal) {
        this.enemyGoal = enemyGoal;
    }

    @Override
    public boolean achieved(Game game) {
        game.getMap().getEntities(ZombieToastSpawner.class);
        if (game.getPlayer() == null)
            return false;

        return game.getEnemiesKilled() >= enemyGoal &&  game.getMap().getEntities(ZombieToastSpawner.class).size() == 0;
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";

        return ":enemies";
    }
}
