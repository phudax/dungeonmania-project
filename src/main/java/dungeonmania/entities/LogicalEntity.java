package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LogicalEntity extends Entity implements LogicInterface {
    private LogicStrategy logic;
    private String logicString;

    private List<Conductor> adjConductors = new ArrayList<>();
    private List<Conductor> poweredConductors = new ArrayList<>();

    public LogicalEntity(Position position, String logic) {
        super(position);
        if (logic.equals("and")) {
            this.logic = new LogicAnd();
        } else if (logic.equals("or")) {
            this.logic = new LogicOr();
        } else if (logic.equals("xor")) {
            this.logic = new LogicXor();
        } else if (logic.equals("co_and")) {
            this.logic = new LogicCoAnd();
        }
        logicString = logic;
    }

    public LogicStrategy getLogicStrategy() {
        return logic;
    }

    public String getLogicString() {
        return logicString;
    }

    public void notify(Conductor c, GameMap map) {
    }

    public void notifyOff(Conductor c, GameMap map) {
    }

    public void subscribe(Conductor c) {
        adjConductors.add(c);
    }

    public List<Conductor> getAdjConductors() {
        return adjConductors;
    }

    public List<Conductor> getPowConductors() {
        return poweredConductors;
    }

    public void setAdjConductors(List<Conductor> adj) {
        this.adjConductors = adj;
    }

    public void setPowConductors(List<Conductor> pow) {
        this.poweredConductors = pow;
    }
}
