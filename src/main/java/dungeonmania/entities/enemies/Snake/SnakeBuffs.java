package dungeonmania.entities.enemies.Snake;

public class SnakeBuffs {
    private double snakeArrowBuff;
    private double snakeTreasureBuff;
    private double snakeKeyBuff;

    public SnakeBuffs(double snakeArrowBuff, double snakeTreasureBuff, double snakeKeyBuff) {
        this.snakeArrowBuff = snakeArrowBuff;
        this.snakeTreasureBuff = snakeTreasureBuff;
        this.snakeKeyBuff = snakeKeyBuff;
    }

    public double getSnakeArrowBuff() {
        return snakeArrowBuff;
    }

    public double getSnakeTreasureBuff() {
        return snakeTreasureBuff;
    }

    public double getSnakeKeyBuff() {
        return snakeKeyBuff;
    }
}
