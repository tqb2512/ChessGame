package piece;

import Game.ChessGame;

import java.util.ArrayList;

public class Pawn extends Piece {
    public boolean hasMoved = false;
    public Pawn(int x, int y, int tileSize, int color, ChessGame game) {
        super(x, y, tileSize, color, game);
        image = loadSvg(STR."/pieces/\{color == 1 ? "w" : "b"}-pawn.svg");
    }

    @Override
    public ArrayList<int[][]> showValidMoves() {
        ArrayList<int[][]> validMoves = new ArrayList<int[][]>();

        // If the pawn is white
        if (color == 1) {
            if (row > 0) {
                if (!game.isAllyOccupied(col, row - 1) && !game.isEnemyOccupied(col, row - 1)) {
                    int[][] move = new int[1][2];
                    move[0][0] = col;
                    move[0][1] = row - 1;
                    validMoves.add(move);

                    if (!hasMoved && row > 1 && !game.isAllyOccupied(col, row - 2) && !game.isEnemyOccupied(col, row - 2)) {
                        int[][] move2 = new int[1][2];
                        move2[0][0] = col;
                        move2[0][1] = row - 2;
                        validMoves.add(move2);
                    }
                }

                // Check for diagonal captures
                if (col > 0 && game.isEnemyOccupied(col - 1, row - 1)) {
                    int[][] move = new int[1][2];
                    move[0][0] = col - 1;
                    move[0][1] = row - 1;
                    validMoves.add(move);
                }
                if (col < 7 && game.isEnemyOccupied(col + 1, row - 1)) {
                    int[][] move = new int[1][2];
                    move[0][0] = col + 1;
                    move[0][1] = row - 1;
                    validMoves.add(move);
                }
            }
        }
        // If the pawn is black
        else {
            if (row < 7) {
                if (!game.isAllyOccupied(col, row + 1) && !game.isEnemyOccupied(col, row + 1)) {
                    int[][] move = new int[1][2];
                    move[0][0] = col;
                    move[0][1] = row + 1;
                    validMoves.add(move);

                    if (!hasMoved && row < 6 && !game.isAllyOccupied(col, row + 2) && !game.isEnemyOccupied(col, row + 2)) {
                        int[][] move2 = new int[1][2];
                        move2[0][0] = col;
                        move2[0][1] = row + 2;
                        validMoves.add(move2);
                    }
                }

                // Check for diagonal captures
                if (col > 0 && game.isEnemyOccupied(col - 1, row + 1)) {
                    int[][] move = new int[1][2];
                    move[0][0] = col - 1;
                    move[0][1] = row + 1;
                    validMoves.add(move);
                }
                if (col < 7 && game.isEnemyOccupied(col + 1, row + 1)) {
                    int[][] move = new int[1][2];
                    move[0][0] = col + 1;
                    move[0][1] = row + 1;
                    validMoves.add(move);
                }
            }
        }

        return validMoves;
    }
}
