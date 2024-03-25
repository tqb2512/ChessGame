import Game.ChessGame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChessGame chessGame = new ChessGame();
        frame.add(chessGame);
        frame.pack();
        frame.setVisible(true);
        chessGame.start();
    }
}