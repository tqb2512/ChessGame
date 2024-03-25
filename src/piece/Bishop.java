package piece;

import Game.ChessGame;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(int x, int y, int tileSize, int color, ChessGame game) {
        super(x, y, tileSize, color, game);
        image = loadSvg(STR."/pieces/\{color == 1 ? "w" : "b"}-bishop.svg");
    }

    @Override
    public ArrayList<int[][]> showValidMoves() {
        ArrayList<int[][]> validMoves = new ArrayList<int[][]>();
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] direction : directions) {
            int newCol = col + direction[0];
            int newRow = row + direction[1];

            while (newCol >= 0 && newCol < 8 && newRow >= 0 && newRow < 8) {
                if (game.isAllyOccupied(newCol, newRow)) {
                    break;
                }
                int[][] move = new int[1][2];
                move[0][0] = newCol;
                move[0][1] = newRow;
                validMoves.add(move);

                if (game.isEnemyOccupied(newCol, newRow)) {
                    break;
                }

                newCol += direction[0];
                newRow += direction[1];
            }
        }

        return validMoves;
    }
}
