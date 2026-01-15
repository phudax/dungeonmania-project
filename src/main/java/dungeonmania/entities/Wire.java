package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wire extends Conductor {

    private List<Switch> poweredBy = new ArrayList<>();
    private List<Switch> adjSwitchs = new ArrayList<>();

    public Wire(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    public void notify(GameMap map, Switch s) {
        if (!poweredBy.contains(s)) {
            poweredBy.add(s);
            for (int i = 0; i < getAdjWires().size(); i++) {
                getAdjWires().get(i).notify(map, s);
            }
            for (int i = 0; i < getAdjLogicals().size(); i++) {
                getAdjLogicals().get(i).notify(this, map);
            }
        }
    }

    public void notifyOff(GameMap map, Switch s) {
        if (poweredBy.contains(s)) {
            poweredBy.remove(s);
            for (int i = 0; i < getAdjWires().size(); i++) {
                getAdjWires().get(i).notifyOff(map, s);
            }
            for (int i = 0; i < getAdjLogicals().size(); i++) {
                getAdjLogicals().get(i).notifyOff(this, map);
            }
        }
    }

    public void subscribe(Object o) {
        if (o instanceof Wire) {
            List<Wire> adjW = getAdjWires();
            adjW.add((Wire) o);
            setAdjWires(adjW);
        } else if (o instanceof LogicalEntity) {
            List<LogicalEntity> adjL = getAdjLogicals();
            adjL.add((LogicalEntity) o);
            setAdjLogicals(adjL);
        } else if (o instanceof Switch) {
            adjSwitchs.add((Switch) o);
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public List<Switch> poweredBy() {
        return poweredBy;
    }

    public boolean isOn() {
        return poweredBy().size() > 0 ? true : false;
    }
}
