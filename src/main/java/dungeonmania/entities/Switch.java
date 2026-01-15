package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends Conductor implements Overlappable, MoveAwayable {
    private boolean activated;
    private List<Bomb> bombs = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    public void subscribe(Bomb b) {
        bombs.add(b);
    }

    public void subscribe(Wire w) {
        List<Wire> wires = getAdjWires();
        wires.add(w);
        setAdjWires(wires);
    }

    public void subscribe(LogicalEntity l) {
        List<LogicalEntity> logicals = getAdjLogicals();
        logicals.add(l);
        setAdjLogicals(logicals);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (activated) {
            bombs.stream().forEach(b -> {
                if (b.isLogical()) {
                    b.addPowerSource(map, this);
                } else b.notify(map);
            });
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = true;
            bombs.stream().forEach(b -> b.notify(map));
            for (int i = 0; i < getAdjWires().size(); i++) {
                getAdjWires().get(i).notify(map, this);
            }
            for (int i = 0; i < getAdjLogicals().size(); i++) {
                getAdjLogicals().get(i).notify(this, map);
            }
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
            for (int i = 0; i < getAdjWires().size(); i++) {
                getAdjWires().get(i).notifyOff(map, this);
            }
            for (int i = 0; i < getAdjLogicals().size(); i++) {
                getAdjLogicals().get(i).notifyOff(this, map);
            }
        }
    }

    public boolean isActivated() {
        return activated;
    }
}
