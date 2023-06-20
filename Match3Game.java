/* Project: Match 3 tile game 
*  CS106 Data Structures and Algorithms
*  Mariya Mushtaq, Isbah Ameer and Virginia Do
*
* Class Match3Game that implements a match 3 game that utilizes various data structures to perform game tasks.
*/

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class Match3Game {

    // attributes
    private Tile[][] grid;
    private int score;
    private Stack<GameEvent> gameStates;
    private Deque<GameEvent> gameEvents;
    private int numMatches;
    private int powerMatches;
    public Match3Game(int numRows, int numColumns) {
        grid = new Tile[numRows][numColumns];
        score = 0;
        gameStates = new Stack<>();
        gameEvents = new LinkedList<>();
    }


    public void generateTiles() {
        Random random = new Random();
        Tile tile = null;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int tileNumber = random.nextInt(6);
                if (tileNumber == 4) { // Assign a probability for the ScoreMultiplier to appear - idea changed so remove
                    //tile = new ScoreMultiplier(tileNumber, 2, this);
                    grid[i][j] = tile;
                } else if (tileNumber == 5) { // Assign a probability for the Blocker obstacle to appear
                    tile = new Blocker(tileNumber);
                    grid[i][j] = tile;
                } else {
                    tile = new Tile(tileNumber % 4); // Normal tile with number 0-3
                    grid[i][j] = tile;
                }
                tile.setRow(i);
                tile.setColumn(j);
            }
        }
    }

    // accessors and modifiers
    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //accessor for number of normal matches
    public int getNumMatches() {
        return numMatches;
    }

    // accessor for number of powerup matches
    public int getPowerMatches() {
        return powerMatches;
    }

    /*
     * method swapTiles that takes two tiles as parameters and swaps them 
     */
    public void swapTiles(Tile tile1, Tile tile2) {
        if ((tile1 instanceof Blocker && ((Blocker) tile1).isBlocked()) || (tile2 instanceof Blocker && ((Blocker) tile2).isBlocked())) {
            // Do not allow the swap if one of the tiles is a blocked Blocker obstacle
            return;
        }
    
        // Swap the positions of two tiles
        Tile temp = tile1;
        tile1 = tile2;
        tile2 = temp;
    
        // Create a SwapEvent object and add it to the gameEvents deque
        SwapEvent swapEvent = new SwapEvent(tile1, tile2);
        swapEvent.process(this);
        gameEvents.add(swapEvent);
    }

    /*
     * method processMatch() that taken in parameters and incrementss counter int numMatches
     */
    public void processMatch(Tile[] tiles) {
        // Remove the matched tiles and add the score to the player's total
        int matchScore = tiles.length * 10;
        score += matchScore;
        for (Tile tile : tiles) {
            tile.setNumber(-1); // Mark the tile for removal
        }
        numMatches+=1;
        // refillGrid(); // Call a method to refill the grid with new tiles
    }

    /*
     * method activatePowerUP() that adds powerup event and increments powerMatches counter
     */
    public void activatePowerUp(PowerUp powerUp) {
        // Activate a power-up
        // powerUp.activate();
    
        // Add the event to the game events queue
        PowerUpEvent powerUpEvent = new PowerUpEvent(powerUp);
        powerUpEvent.process(this);
        gameEvents.add(powerUpEvent);
        powerMatches++;
    }
    

    /*
     * method processGameEvent() that add events to the state of the game
     */
    public void processGameEvent(GameEvent event) {
        // Process a game event and update the game state accordingly
        event.process(this);
        gameStates.push(event); // Add the event to the gameStates stack for undo/redo purposes
    }    

    /*
     * method to undo
     */
    public void undo() {
        if (!gameStates.isEmpty()) {
            GameEvent event = gameStates.pop();
            event.undo(this);
            gameEvents.addFirst(event); // Add the event back to the gameEvents deque for redo purposes
        }
    }
    /*
     * method to redo
     */
    public void redo() {
        if (!gameEvents.isEmpty()) {
            GameEvent event = gameEvents.pollFirst();
            event.redo(this);
            gameStates.push(event); // Add the event back to the gameStates stack for undo purposes
        }
    }


    /*
     * method to refill the grid with random tiles after matches remove some tiles from the grid
     */
    public void refillGrid() {
        Random random = new Random();
    
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Tile tile = grid[i][j];
                if (tile.getNumber() == -1) {
                    // Tile marked for removal, generate a new random tile
                    int tileNumber = random.nextInt(4); // Generate a number from 0 to 3
                    grid[i][j] = new Tile(tileNumber);
                }
            }
        }
    }    

    public Tile[][] getGrid() {
        return this.grid;
    }
    

    // main method with some test cases
    public static void main(String[] args) {
        Match3Game game = new Match3Game(5, 5);
        game.generateTiles();
        Tile[][] grids = game.getGrid();
        
        System.out.println("BEFORE SWAPPING :: " + grids[0][0].getNumber() + " :: " +  grids[0][1].getNumber());
        game.swapTiles(grids[0][0], grids[0][1]);
        System.out.println("AFTER SWAPPING :: " + grids[0][0].getNumber() + " :: " +  grids[0][1].getNumber());


        System.out.println("BEFORE processMatch :: " + grids[0][1].getNumber());
        Tile[] tiles = {grids[0][1], grids[1][1], grids[1][3]};
        game.processMatch(tiles);
        System.out.println("AFTER processMatch :: " + grids[0][1].getNumber());
        System.out.println("SCORE:: " + game.score);
        for (int i = 0; i < grids.length; ++i) {
            for (int j = 0; j < grids[i].length; ++j) {
                System.out.print(grids[i][j].getNumber() + "\t");
            }
            System.out.println();
        }


        System.out.println("TESTING activatePowerUp");
        PowerUp powerUp = null;
        for (int i = 0; i < grids.length; ++i) {
            for (int j = 0; j < grids[i].length; ++j) {
                if (grids[i][j] instanceof PowerUp) {
                    powerUp = (PowerUp) grids[i][j];
                    break;
                }
                if (powerUp != null) {
                    break;
                }
            }
        }
        if (powerUp != null) {
            System.out.println("BEFORE ACTIVATING POWERUP:: score = " + game.getScore());
            game.activatePowerUp(powerUp);
            System.out.println("AFTER ACTIVATING POWERUP:: score = " + game.getScore());
        } else {
            System.out.println("NO POWERUP TILE FOUND");
        }

        System.out.println("TESTING Blocker");
        Blocker blocker = null;
        Tile nextBlockerTile = null;
        for (int i = 0; i < grids.length; ++i) {
            for (int j = 0; j < grids[i].length; ++j) {
                if (grids[i][j] instanceof Blocker) {
                    blocker = (Blocker) grids[i][j];
                    nextBlockerTile = grids[i][j > 0 ? 0 : j+1];
                    break;
                }
                if (blocker != null) {
                    break;
                }
            }
        }
        if (blocker != null) {
            blocker.setBlocked(false);
            System.out.println("BEFORE SWAPPING BLOCKER :: Blocker: " + blocker.getRow() + ", " + blocker.getColumn());
            game.swapTiles(blocker, nextBlockerTile);
            System.out.println("BEFORE SWAPPING BLOCKER :: Blocker: " + blocker.getRow() + ", " + blocker.getColumn());
            blocker.setBlocked(true);
            System.out.println("BEFORE SWAPPING BLOCKER :: Blocker: " + blocker.getRow() + ", " + blocker.getColumn());
            game.swapTiles(blocker, nextBlockerTile);
            System.out.println("BEFORE SWAPPING BLOCKER :: Blocker: " + blocker.getRow() + ", " + blocker.getColumn());
        } else {
            System.out.println("NO BLOCKER TILE FOUND");
        }        
        
    }

}
