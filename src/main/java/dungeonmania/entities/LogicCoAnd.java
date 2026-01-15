package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

public class LogicCoAnd implements LogicStrategy {

    public LogicCoAnd() {
    }

    @Override
    public boolean check(List<Conductor> adj, List<Conductor> pow) {
        if (adj.size() < 2 || pow.size() < 2 || pow.size() < adj.size()) return false;
        for (Conductor c : pow) {
            if (c instanceof Switch) {
                Switch refSwitch = (Switch) c;
                for (Conductor c2 : pow) {
                    if (c2 instanceof Wire) {
                        Wire wire = (Wire) c2;
                        if (!wire.poweredBy().contains(refSwitch)) return false;
                    }
                }
            }
        }
        List<List<Switch>> wireList = new ArrayList<>();
        for (Conductor c : pow) {
            if (c instanceof Wire) {
                Wire w = (Wire) c;
                wireList.add(w.poweredBy());
            }
        }
        return haveCommonElements(wireList);
    }

    public static boolean haveCommonElements(List<List<Switch>> lists) {
        if (lists.size() > 0) {
            List<Switch> firstList = lists.get(0);

            for (Switch element : firstList) {
                boolean foundInAll = true;
                for (int i = 1; i < lists.size(); i++) {
                    if (!lists.get(i).contains(element)) {
                        foundInAll = false;
                        break;
                    }
                }
                if (foundInAll) {
                    return true;
                }
            }
        }
        return false;
    }
}
