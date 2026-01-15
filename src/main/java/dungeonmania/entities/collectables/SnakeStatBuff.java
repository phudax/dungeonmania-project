package dungeonmania.entities.collectables;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.enemies.Snake.SnakeBuffs;

public interface SnakeStatBuff {

    public void applyBuff(BattleStatistics origin, SnakeBuffs buffs);
}
