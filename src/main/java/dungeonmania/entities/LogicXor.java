package dungeonmania.entities;

import java.util.List;

public class LogicXor implements LogicStrategy {

    public LogicXor() {
    }

    @Override
    public boolean check(List<Conductor> adj, List<Conductor> pow) {
        if (pow.size() == 1) {
            return true;
        }
        return false;
    }
}

