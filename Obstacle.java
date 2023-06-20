public abstract class Obstacle extends Tile {
    public Obstacle(int number) {
        super(number);
    }

    public abstract void trigger(); 
}
