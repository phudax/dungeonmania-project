package dungeonmania.entities.playerState;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Player;

public abstract class PlayerState {
    public enum PlayerStateType {
        BASE, INVINCIBLE, INVISIBLE
    }

    private Player player;
    private PlayerStateType stateType;

    PlayerState(Player player, PlayerStateType stateType) {
        this.player = player;
        this.stateType = stateType;
    }

    public PlayerStateType getStateType() {
        return stateType;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void transitionInvisible();

    public abstract void transitionInvincible();

    public abstract void transitionBase();

    public abstract BattleStatistics applyBuff(BattleStatistics origin);
}
