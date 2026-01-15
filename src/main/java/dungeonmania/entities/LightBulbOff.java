package dungeonmania.entities;

import java.util.List;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LightBulbOff extends LogicalEntity {

    public LightBulbOff(Position position, String logic) {
        super(position, logic);
    }

    @Override
    public void notify(Conductor c, GameMap map) {
        List<Conductor> pow = getPowConductors();
        if (!pow.contains(c)) {
            pow.add(c);
        }
        setPowConductors(pow);
        if (getLogicStrategy().check(getAdjConductors(), getPowConductors())) {
            map.destroyEntity(this);
            LightBulbOn onBulb = new LightBulbOn(this);
            map.addEntity(onBulb);
            for (int i = 0; i < getAdjConductors().size(); i++) {
                getAdjConductors().get(i).replaceBulb(this, onBulb);
            }
        }
    }

    @Override
    public void notifyOff(Conductor c, GameMap map) {
        List<Conductor> pow = getPowConductors();
        if (pow.contains(c)) {
            pow.remove(c);
        }
        setPowConductors(pow);
        if (getLogicStrategy().check(getAdjConductors(), getPowConductors())) {
            map.destroyEntity(this);
            LightBulbOn onBulb = new LightBulbOn(this);
            map.addEntity(onBulb);
            for (int i = 0; i < getAdjConductors().size(); i++) {
                getAdjConductors().get(i).replaceBulb(this, onBulb);
            }
        }
    }


}
