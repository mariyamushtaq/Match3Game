public class Blocker extends Obstacle {
    private boolean blocked;

    public Blocker(int number) {
        super(number);
        blocked = false;
    }

    @Override
    public void setNumber(int number) {
        if (!isBlocked()) {
            super.setNumber(number);
        }
    }

    @Override
    public void trigger() {
        // Set the blocked state to true
        blocked = true;      
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
