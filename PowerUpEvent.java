/* Project: Match 3 tile game 
*  CS106 Data Structures and Algorithms
*  Mariya Mushtaq, Isbah Ameer and Virginia Do
*
* Class PowerUpEvent that implements GameEvent methods that facilitate 
* a power up or pop event in match 3 game
*/public class PowerUpEvent implements GameEvent {
    private PowerUp powerUp;

    public PowerUpEvent(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    @Override
    public void process(Match3Game game) {
        activatePowerUp(powerUp, game);
    }

    @Override
    public void undo(Match3Game game) {
        // deactivatePowerUp(powerUp, game); //TODO
    }

    @Override
    public void redo(Match3Game game) {
        activatePowerUp(powerUp, game);
    }

    private void activatePowerUp(PowerUp powerUp, Match3Game game) {
        powerUp.activate();
    }
}
