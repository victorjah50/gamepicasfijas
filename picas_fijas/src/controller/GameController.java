package controller;

import model.Game;

public class GameController {
    private Game game;

    public void startNewGame(int attempts) {
        game = new Game(attempts);
    }

    public Game getGame() {
        return game;
    }

    public Game.Result guess(String guess) {
        return game.tryGuess(guess);
    }
}
