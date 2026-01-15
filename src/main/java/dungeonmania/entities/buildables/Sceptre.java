package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Sceptre extends Buildable {
    private int duration;

    public Sceptre(int duration) {
        super(null);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void use(Game game) {
        duration--;
    }

    @Override
    public int getDurability() {
        return 1;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }
}
