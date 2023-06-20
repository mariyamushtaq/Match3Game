public interface GameEvent {
    void process(Match3Game game);
    void undo(Match3Game game);
    void redo(Match3Game game);
}
