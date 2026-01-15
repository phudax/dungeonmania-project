package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Conductor;
import dungeonmania.entities.Entity;
import dungeonmania.entities.LogicAnd;
import dungeonmania.entities.LogicCoAnd;
import dungeonmania.entities.LogicOr;
import dungeonmania.entities.LogicStrategy;
import dungeonmania.entities.LogicXor;
import dungeonmania.entities.Player;
import dungeonmania.entities.Switch;
import dungeonmania.entities.Wire;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;

public class Bomb extends Collectable implements InventoryItem {
    public enum State {
        SPAWNED, INVENTORY, PLACED
    }

    private List<Conductor> poweredBy = new ArrayList<>();
    private boolean logical = false;

    public static final int DEFAULT_RADIUS = 1;
    private State state;
    private int radius;
    private LogicStrategy logic;
    private List<Conductor> adjConductors = new ArrayList<>();

    private List<Switch> subs = new ArrayList<>();

    public Bomb(Position position, int radius) {
        super(position);
        state = State.SPAWNED;
        this.radius = radius;
    }

    public Bomb(Position position, int radius, String logic) {
        super(position);
        state = State.SPAWNED;
        this.radius = radius;
        if (logic.equals("and")) {
            this.logic = new LogicAnd();
        } else if (logic.equals("or")) {
            this.logic = new LogicOr();
        } else if (logic.equals("xor")) {
            this.logic = new LogicXor();
        } else if (logic.equals("co_and")) {
            this.logic = new LogicCoAnd();
        }
        logical = true;
    }

    public void subscribe(Switch s) {
        this.subs.add(s);
    }

     public void subscribe(Wire w) {
        this.adjConductors.add(w);
    }

    public void notify(GameMap map) {
        explode(map);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (state != State.SPAWNED)
            return;
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this))
                return;
            subs.stream().forEach(s -> s.unsubscribe(this));
            map.destroyEntity(this);
        }
        this.state = State.INVENTORY;
    }

    public void onPutDown(GameMap map, Position p) {
        translate(Position.calculatePositionBetween(getPosition(), p));
        map.addEntity(this);
        this.state = State.PLACED;
        List<Position> adjPosList = getPosition().getCardinallyAdjacentPositions();
        adjPosList.stream().forEach(node -> {
            List<Entity> entities = map.getEntities(node).stream().filter(e -> (e instanceof Switch))
                    .collect(Collectors.toList());
            entities.stream().map(Switch.class::cast).forEach(s -> s.subscribe(this, map));
            entities.stream().map(Switch.class::cast).forEach(s -> this.subscribe(s));
            if (logical) {
                entities.stream().map(Switch.class::cast).forEach(s -> adjConductors.add(s));
                List<Entity> wires = map.getEntities(node).stream().filter(e -> (e instanceof Wire))
                    .collect(Collectors.toList());
                wires.stream().map(Wire.class::cast).forEach(w -> adjConductors.add(w));
                for (Conductor c : adjConductors) {
                    if (c instanceof Wire) {
                        Wire w = (Wire) c;
                        if (w.poweredBy().size() > 0 && !poweredBy.contains(c)) {
                            poweredBy.add(c);
                        }

                    }
                }
                if (logic.check(adjConductors, poweredBy)) {
                    explode(map);
                }
            }

        });
    }

    public void explode(GameMap map) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                List<Entity> entities = map.getEntities(new Position(i, j));
                entities = entities.stream().filter(e -> !(e instanceof Player)).collect(Collectors.toList());
                for (Entity e : entities)
                    map.destroyEntity(e);
            }
        }
    }

    public State getState() {
        return state;
    }

    public boolean isLogical() {
        return logical;
    }

    public void addPowerSource(GameMap map, Conductor c) {
        poweredBy.add(c);
        if (logic.check(adjConductors, poweredBy)) {
            explode(map);
        }
    }
}
