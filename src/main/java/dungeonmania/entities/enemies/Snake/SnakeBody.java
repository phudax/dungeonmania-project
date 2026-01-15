package dungeonmania.entities.enemies.Snake;

import java.util.List;
import java.util.Objects;

import dungeonmania.Game;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeBody extends Enemy implements Snake {
    private Position nextPos;
    private boolean isInvincible = false;
    private boolean isInvisible = false;
    private SnakeHead head;

    public SnakeBody(Position position, double health, double attack, SnakeHead head) {
        super(position, health, attack);
        this.head = head;
    }

    @Override
    public void move(Game game) {
        game.getMap().moveTo(this, nextPos);
    }

    public void setNextPos(Position nextPos) {
        this.nextPos = nextPos;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (entity instanceof Snake) {
            return false;
        }

        return true;
    }

    public void updateStats(double health, double attack, double defence, boolean isInvincible, boolean isInvisible) {
        getBattleStatistics().newBattleStatistics(health, attack, defence);
        this.isInvincible = isInvincible;
        this.isInvisible = isInvisible;

    }

    @Override
    public void onDestroy(GameMap map) {
        if (isInvincible) {
            List<SnakeBody> body = head.getSnakeBody().getBodyList();

            if (!body.get(body.size() - 1).equals(this)) { // Makes sure its not tail
                SnakeBody refHead = head.getSnakeBody().toBeNextHead(this);
                System.out.println("REFHEAD:" + refHead);

                  // remove this body from map                                           // Remove killed body
                SnakeHead newHead = new SnakeHead(refHead.getPosition(),
                    head.getHealth(), head.getAttack(), head.getBuffs());
                SnakeParts newBody = head.getSnakeBody().snakeSubBody(refHead, newHead);

                newBody.removePart(refHead);
                newHead.attachBody(newBody);
                newHead.setInvincible(isInvincible);
                newHead.setInvisible(isInvisible);

                map.removeNode(this);
                map.removeNode(refHead);
                map.addEntity(newHead);
                map.registerEnemy(newHead);
                System.out.println("Entities" + map.getEntities(SnakeHead.class).toString());
                System.out.println("Entities" + map.getEntities(SnakeBody.class).toString());

                System.out.println("Final new Snake" + newHead + newHead.getSnakeBody().toString());
                return;
            }

        }

        head.getSnakeBody().destroyBodyFromBody(map, this);
    }

    public boolean getIsInvisible() {
        return isInvisible;
    }

    public void setHead(SnakeHead head) {
        this.head = head;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Same object reference
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false; // Different classes or null object
        }

        SnakeBody other = (SnakeBody) obj;

        // Compare properties for equality
        // Example: If SnakePart has an ID field, compare IDs for equality
        return Objects.equals(getId(), other.getId());
    }
}
