/* Project: Match 3 tile game 
*  CS106 Data Structures and Algorithms
*  Mariya Mushtaq, Isbah Ameer and Virginia Do
*
* Class SwapEvent that implements GameEvent and implements methods that facilitate 
* a swap event in match 3 game
*/

public class SwapEvent implements GameEvent {
    private Tile tile1;
    private Tile tile2;

    //constructor
    public SwapEvent(Tile tile1, Tile tile2) {
        this.tile1 = tile1;
        this.tile2 = tile2;
    }

    @Override
    public void process(Match3Game game) {
        swapTiles(tile1, tile2);
        game.processMatch(new Tile[] {tile1, tile2});
    }

    @Override
    public void undo(Match3Game game) {
        swapTiles(tile1, tile2);
    }

    @Override
    public void redo(Match3Game game) {
        swapTiles(tile1, tile2);
    }

    /*
     * method SwapTiles that takes 2 tiles for parameters and swaps their position for a match
     */
    private void swapTiles(Tile tile1, Tile tile2) {
        int tempNumber = tile1.getNumber();
        int tempRow = tile1.getRow();
        int tempColumn = tile1.getColumn();

        tile1.setNumber(tile2.getNumber());
        tile1.setRow(tile2.getRow());
        tile1.setColumn(tile2.getColumn());

        tile2.setNumber(tempNumber);
        tile2.setRow(tempRow);
        tile2.setColumn(tempColumn);
    }
}
