package dungeonmania.entities;

import java.util.List;

public class LogicOr implements LogicStrategy {

    public LogicOr() {
    }

    @Override
    public boolean check(List<Conductor> adj, List<Conductor> pow) {
        if (pow.size() >= 1) return true;
        return false;
    }

}

