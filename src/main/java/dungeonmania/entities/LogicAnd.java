package dungeonmania.entities;

import java.util.List;

public class LogicAnd implements LogicStrategy {

    public LogicAnd() {
    }

    @Override
    public boolean check(List<Conductor> adj, List<Conductor> pow) {
        if (adj.size() >= 2 && pow.size() == adj.size()) {
            return true;
        }
        return false;
    }

}
