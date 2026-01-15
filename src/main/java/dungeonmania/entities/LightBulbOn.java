package dungeonmania.entities;

import java.util.List;

import dungeonmania.map.GameMap;

public class LightBulbOn extends LogicalEntity {

    public LightBulbOn(LightBulbOff b) {
        super(b.getPosition(), b.getLogicString());
        setPowConductors(b.getPowConductors());
        setAdjConductors(b.getAdjConductors());
    }

    @Override
    public void notify(Conductor c, GameMap map) {
        List<Conductor> pow = getPowConductors();
        if (!pow.contains(c)) {
            pow.add(c);
        }
        setPowConductors(pow);
        if (!getLogicStrategy().check(getAdjConductors(), getPowConductors())) {
            turnOff(c, map);
        }
    }

    @Override
    public void notifyOff(Conductor c, GameMap map) {
        List<Conductor> pow = getPowConductors();
        if (pow.contains(c)) {
            pow.remove(c);
        }
        setPowConductors(pow);
        if (!getLogicStrategy().check(getAdjConductors(), getPowConductors())) {
            turnOff(c, map);
        }
    }

    public void turnOff(Conductor c, GameMap map) {
            map.destroyEntity(this);
            LightBulbOff offBulb = new LightBulbOff(getPosition(), getLogicString());
            offBulb.setAdjConductors(getAdjConductors());
            offBulb.setPowConductors(getPowConductors());
            map.addEntity(offBulb);
            for (int i = 0; i < getAdjConductors().size(); i++) {
                getAdjConductors().get(i).replaceBulb(this, offBulb);
            }
    }
}

