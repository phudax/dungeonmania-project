package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.util.Position;

public class Conductor extends Entity {

    private List<Wire> adjWires = new ArrayList<>();
    private List<LogicalEntity> adjLogicals = new ArrayList<>();

    public Conductor(Position position) {
        super(position);
    }

    public void setAdjWires(List<Wire> adj) {
        this.adjWires = adj;
    }

    public void setAdjLogicals(List<LogicalEntity> adj) {
        this.adjLogicals = adj;
    }

    public List<Wire> getAdjWires() {
        return this.adjWires;
    }

    public List<LogicalEntity> getAdjLogicals() {
        return this.adjLogicals;
    }

    public void replaceBulb(LogicalEntity old, LogicalEntity rep) {
        adjLogicals.remove(old);
        adjLogicals.add(rep);
    }
}
