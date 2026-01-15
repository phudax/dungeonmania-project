package dungeonmania.entities.enemies.Snake;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.SnakeFood;
import dungeonmania.entities.collectables.SnakePotionBuff;
import dungeonmania.entities.collectables.SnakeStatBuff;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeHead extends Enemy implements Snake {
    public static final int DEFAULT_ATTACK = 1;
    public static final int DEFAULT_HEALTH = 5;

    private SnakeParts snakeBody;
    private boolean isInvisible = false;
    private boolean isInvincible = false;
    private SnakeBuffs buffs;

    public SnakeHead(Position position, double health, double attack, SnakeBuffs buffs) {
        super(position, health, attack);
        snakeBody = new SnakeParts(this);
        this.buffs = buffs;
    }
    @Override
    public void move(Game game) {
        GameMap map = game.getMap();
        List<Entity> entities = map.getEntities(Entity.class);
        List<Entity> foodInRange = new ArrayList<>();

        for (Entity e : entities) {
            if (e instanceof SnakeFood) {
                foodInRange.add(e);
            }
        }

        if (foodInRange.isEmpty()) {
            return; // No food in range, do nothing
        }

        Entity targetFood = findClosestReachableFood(map, foodInRange);

        if (targetFood == null) {
            return; // No reachable food, do nothing
        }

        Position currentPosition = getPosition();
        Position nextPos = map.dijkstraPathFind(getPosition(), targetFood.getPosition(), this);

        // Check if nextPos is not null and different from current position before moving
        if (nextPos != null && !nextPos.equals(currentPosition)) {
            map.moveTo(this, nextPos); // Move the SnakeHead to the calculated position
            snakeBody.moveSnakeBody(game, currentPosition); // Update the SnakeBody position
            snakeBody.setTailPos(currentPosition); // Set the tail position if the head has moved
        }

    }

    private Entity findClosestReachableFood(GameMap map, List<Entity> foodInRange) {
        Entity closestFood = null;
        int minDistance = Integer.MAX_VALUE;

        for (Entity food : foodInRange) {
            int distance = map.getDistanceBetweenNodes(getPosition(), food.getPosition(), this);
            if (distance < minDistance && distance != -1) {
                minDistance = distance;
                closestFood = food;
            }
        }

        return closestFood;
    }



    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (entity instanceof Snake) {
            return false;
        }

        return true;
    }

    public void consume(GameMap map, Entity entity) {

        if (!entity.getPosition().equals(getPosition()))
        return;

        snakeBody.growBody(map);

        if (entity instanceof SnakePotionBuff) {
            ((SnakePotionBuff) entity).applyBuff(this);
        } else {
            BattleStatistics currStats = getBattleStatistics();
            ((SnakeStatBuff) entity).applyBuff(currStats, buffs);
        }
        snakeBody.applyBufftoBody(getBattleStatistics(), isInvincible, isInvisible);
        System.out.println(getHealth());
    }

    @Override
    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        snakeBody.destroyBodyFromHead(map);
        g.unsubscribe(getId());
    }

    public double getHealth() {
        return getBattleStatistics().getHealth();
    }

    public double getAttack() {
        return getBattleStatistics().getAttack();
    }

    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }

    public void setInvisible(boolean isInvisible) {
        this.isInvisible = isInvisible;
    }

    public SnakeParts getSnakeBody() {
        return snakeBody;
    }

    public void attachBody(SnakeParts snakeBody) {
        this.snakeBody = snakeBody;
        snakeBody.setBodyPartHead();
        // System.out.println(snakeBody.toString());
    }

    public SnakeBuffs getBuffs() {
        return buffs;
    }

    public boolean getIsInvisible() {
        return isInvisible;
    }


}
