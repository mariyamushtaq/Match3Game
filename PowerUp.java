public abstract class PowerUp extends Tile {
    public PowerUp(int number) {
        super(number);
    }

    public abstract void activate();
}
