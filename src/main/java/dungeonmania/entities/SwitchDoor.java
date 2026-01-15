package dungeonmania.entities;

import java.util.List;

import dungeonmania.entities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity {
    private boolean open = false;

    public SwitchDoor(Position position, String logic) {
        super(position.asLayer(Entity.DOOR_LAYER), logic);
    }
    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open || entity instanceof Spider) {
            return true;
        }
        return false;
    }

    @Override
    public void notify(Conductor c, GameMap map) {
        List<Conductor> pow = getPowConductors();
        if (!pow.contains(c)) {
            pow.add(c);
        }
        setPowConductors(pow);
        if (getLogicStrategy().check(getAdjConductors(), getPowConductors())) {
            open();
        } else close();
    }

    @Override
    public void notifyOff(Conductor c, GameMap map) {
        List<Conductor> pow = getPowConductors();
        if (pow.contains(c)) {
            pow.remove(c);
        }
        setPowConductors(pow);
        if (getLogicStrategy().check(getAdjConductors(), getPowConductors())) {
            open();
        } else close();
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }

    public void close() {
        open = false;
    }
}


