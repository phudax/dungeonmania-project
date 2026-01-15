package dungeonmania.entities.enemies.Snake;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeParts {
    private List<SnakeBody> snakeParts = new ArrayList<>();
    private SnakeHead head;
    private Position tailPos;

    public SnakeParts(SnakeHead head) {
        this.head = head;
        tailPos = head.getPosition();
    }

    public SnakeParts(SnakeHead head, List<SnakeBody> snakeBody) {
        this.head = head;
        this.snakeParts = snakeBody;
    }

    public void moveSnakeBody(Game game, Position start) {
        Position currPos = start;
        Position nextPos;
        if (snakeParts.size() < 1)
            return;

        tailPos = snakeParts.get(snakeParts.size() - 1).getPosition();

        for (SnakeBody body: snakeParts) {
            nextPos = body.getPosition();
            body.setNextPos(currPos);
            body.move(game);
            currPos = nextPos;
        }
    }

    public void growBody(GameMap map) {
            System.out.println("Growing at: " + tailPos + "SnakeBody: " + toString());
            SnakeBody bodyPart = new SnakeBody(tailPos, head.getHealth(), head.getAttack(), head);
            snakeParts.add(bodyPart);
            map.addEntity(bodyPart);
    }

    public void applyBufftoBody(BattleStatistics newStats, boolean isInvincible, boolean isInvisible) {
        for (SnakeBody body: snakeParts) {
            body.updateStats(newStats.getHealth(), newStats.getAttack(),
                newStats.getDefence(), isInvincible, isInvisible);
        }
    }

    public void destroyBodyFromHead(GameMap map) {
        for (SnakeBody bodyPart: snakeParts) {
            map.removeNode(bodyPart);
        }
    }

    public void destroyBodyFromBody(GameMap map, SnakeBody start) {
        for (int i = snakeParts.indexOf(start); i < snakeParts.size(); i++) {
            map.removeNode(snakeParts.get(i));
        }

        snakeParts.subList(snakeParts.indexOf(start), snakeParts.size()).clear();
    }

    public SnakeParts snakeSubBody(SnakeBody start, SnakeHead head) {
        // System.out.println("71BODY: " + toString());
        int startIndex = snakeParts.indexOf(start);
        SnakeParts snakeSubBody = new SnakeParts(head);
        for (int i = startIndex;  i <= snakeParts.size() - 1; i++) {
            snakeSubBody.addPart(snakeParts.get(i));
        }

        snakeParts.removeAll(snakeSubBody.getSnakeBodyList());
        snakeSubBody.setTailPos(tailPos);
        if (snakeParts.size() == 0) {
            tailPos = head.getPosition();
        } else {
            tailPos = snakeParts.get(snakeParts.size() - 1).getPosition();
        }

        return snakeSubBody;
    }

    public void setTailPos(Position tailPos) {
        if (snakeParts.size() < 1)
        this.tailPos = tailPos;
    };

    public int getSizeOfBody() {
        return snakeParts.size();
    }

    public List<SnakeBody> getBodyList() {
        return snakeParts;
    }

    public void attachHead(SnakeHead head) {
        this.head = head;
        for (SnakeBody snakePart : snakeParts) {
            snakePart.setHead(head);
        }
    }

    public SnakeBody toBeNextHead(SnakeBody bodyPart) {
        int i = snakeParts.indexOf(bodyPart);
        return snakeParts.get(i + 1);
    }

    public void addPart(SnakeBody part) {
        snakeParts.add(part);
    }

    public void removePart(SnakeBody part) {
        snakeParts.remove(part);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SnakeBody [");

        for (SnakeBody part : snakeParts) {
            sb.append("at index: " + snakeParts.indexOf(part) + " ").append(part.toString()).append(", ");
        }

        if (!snakeParts.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("]");

        return sb.toString();
    }

    public void setBodyPartHead() {
        for (SnakeBody snakePart : snakeParts) {
            snakePart.setHead(head);
        }
    }

    public List<SnakeBody> getSnakeBodyList() {
        return snakeParts;
    }
}
