/* Project: Match 3 tile game 
*  CS106 Data Structures and Algorithms
*  Mariya Mushtaq, Isbah Ameer and Virginia Do
*
* Class Scoreboard that implements a score board for Match 3 tile game using Array data structure. The methods in this 
* this class take player name as a parameter to update scores and generate a scoreboard
*/



import java.util.ArrayList;

public class ScoreBoard {

    // attributes
    private ArrayList<Integer> scores;
    private int highScore = 0;
    private Match3Game game;

    // constructor 
    public ScoreBoard(Match3Game game) {
        this.game = game;
        scores = new ArrayList<Integer>();
    }

    /*
     * method calculateScore() to calculate scores based on the number of normal matches and powerup matches
     */
    public void calculateScore() {
        // numMatches = count for matches made on combining normal tiles
        int numMatches = game.getNumMatches();
        // powerMatches = count for matches made on combining powerup tiles
        int powerMatches = game.getPowerMatches();
        // score increment of 10 for each normal match
        int numScore = numMatches * 10;
        // score increment of 20 for each powerup match 
        int powerScore = powerMatches * 20;
        int playerScore = numScore + powerScore;
        // add score to the Array List
        scores.add(playerScore);

    }

    /*
     * method viewScore() that uses String Builder to display score from one game attempt
     */
    public String viewScore() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scoreboard:\n");
        for (int i = 0; i < scores.size(); i++) {
            sb.append("Game " + (i+1) + " score: " + scores.get(i) + "\n");
        }
        return sb.toString();
    }
    
    /*
     *  method viewScoreBoard() to generate a score board
     */
    public void viewScoreBoard() {
        System.out.println(this.toString());
    }
    


    /*
     * method getHighScore() that returns the highest score from all game attempts
     */
    public int getHighScore(){
        for (int i = 0; i < scores.size(); i++) {
            int score = scores.get(i);
            if (score > highScore) {
                highScore = score;
            }
        }
        return highScore;
    }

    
    /*
     * method toString() 
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+-----------------------+\n");
        sb.append("|       Scoreboard      |\n");
        sb.append("+-----------------------+\n");
        sb.append("|  Game   |   Score     |\n");
        sb.append("+-----------------------+\n");
        this.calculateScore();
        for (int i = 0; i < scores.size(); i++) {
            sb.append("|   " + String.format("%02d", i+1) + "    |   " + String.format("%05d", scores.get(i)) + "    |\n");
            sb.append("+-----------------------+\n");
        }
        
        return sb.toString();
    }

    
    public static void main(String[] args) {
        Match3Game game = new Match3Game(5, 5);
        game.generateTiles(); //very important

        ScoreBoard scoreBoard = new ScoreBoard(game);
        System.out.println("Before doing anything");
        System.out.println(scoreBoard.toString());

        System.out.println("after swapping two tiles");
        game.swapTiles(game.getGrid()[0][0], game.getGrid()[0][1]);
        System.out.println(scoreBoard.toString());

    }
}