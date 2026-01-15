package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface LogicInterface {
    public void notify(Conductor c, GameMap map);
    public void notifyOff(Conductor c, GameMap map);
}
