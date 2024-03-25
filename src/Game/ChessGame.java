package Game;

import piece.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessGame extends JPanel implements Runnable {
    final int FPS = 60;
    final int WHITE = 1;
    final int BLACK = 0;
    Board board = new Board();
    ArrayList<Piece> pieces = new ArrayList<>();
    Piece selectedPiece = null;
    Thread thread;
    Timer deselectTimer;
    Mouse mouse = new Mouse();
    int turn = WHITE;

    public ChessGame() {
        this.setPreferredSize(new Dimension(board.boardWidth, board.boardHeight));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(i, 1, board.tileSize, BLACK, this));
            pieces.add(new Pawn(i, 6, board.tileSize, WHITE, this));
        }
        pieces.add(new Rook(0, 0, board.tileSize, BLACK, this));
        pieces.add(new Rook(7, 0, board.tileSize, BLACK, this));
        pieces.add(new Rook(0, 7, board.tileSize, WHITE, this));
        pieces.add(new Rook(7, 7, board.tileSize, WHITE, this));
        pieces.add(new Bishop(2, 0, board.tileSize, BLACK, this));
        pieces.add(new Bishop(5, 0, board.tileSize, BLACK, this));
        pieces.add(new Bishop(2, 7, board.tileSize, WHITE, this));
        pieces.add(new Bishop(5, 7, board.tileSize, WHITE, this));
        pieces.add(new Knight(1, 0, board.tileSize, BLACK, this));
        pieces.add(new Knight(6, 0, board.tileSize, BLACK, this));
        pieces.add(new Knight(1, 7, board.tileSize, WHITE, this));
        pieces.add(new Knight(6, 7, board.tileSize, WHITE, this));
        pieces.add(new Queen(3, 0, board.tileSize, BLACK, this));
        pieces.add(new Queen(3, 7, board.tileSize, WHITE, this));
        pieces.add(new King(4, 0, board.tileSize, BLACK, this));
        pieces.add(new King(4, 7, board.tileSize, WHITE, this));

        deselectTimer = new Timer(100, e -> selectedPiece = null);
        deselectTimer.setRepeats(false);
    }

    public boolean isAllyOccupied(int col, int row) {
        for (Piece piece : pieces) {
            if (piece.col == col && piece.row == row && piece.color == turn) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnemyOccupied(int col, int row) {
        for (Piece piece : pieces) {
            if (piece.col == col && piece.row == row && piece.color != turn) {
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate() {
        Piece king = null;
        for (Piece piece : pieces) {
            if (piece instanceof King && piece.color == turn) {
                king = piece;
                break;
            }
        }
        if (king != null && isUnderAttack(king)) {
            ArrayList<int[][]> validMoves = king.showValidMoves();
            return validMoves.isEmpty();
        }
        return false;
    }

    public boolean isUnderAttack(Piece piece) {
        for (Piece opponentPiece : pieces) {
            if (opponentPiece.color != turn && opponentPiece.isValidMove(piece.col, piece.row)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        board.draw(g2d);
        for (Piece piece : pieces) {
            piece.draw(g2d);
        }

        if (selectedPiece != null) {
            g2d.setColor(new Color(0, 255, 0, 100));
            g2d.fillRect(selectedPiece.col * board.tileSize, selectedPiece.row * board.tileSize, board.tileSize, board.tileSize);
            ArrayList<int[][]> validMoves = selectedPiece.showValidMoves();
            for (int[][] move : validMoves) {
                g2d.setColor(new Color(0, 255, 0, 100));
                g2d.fillRect(move[0][0] * board.tileSize, move[0][1] * board.tileSize, board.tileSize, board.tileSize);
            }
        }
    }

    public void update() {

        Piece king = getKing(turn);
        if (king == null) {
            JOptionPane.showMessageDialog(this, (turn == WHITE ? "Black" : "White") + " wins!");
            System.exit(0);
        }

        if (mouse.isPressed) {
            if (selectedPiece == null) {
                for (Piece piece : pieces) {
                    if (piece.contains(mouse.x, mouse.y) && piece.color == turn) {
                        selectedPiece = piece;
                        mouse.isPressed = false;
                        break;
                    }
                }
            } else {
                int newCol = mouse.x / board.tileSize;
                int newRow = mouse.y / board.tileSize;
                if ((newCol == selectedPiece.col && newRow == selectedPiece.row) || SwingUtilities.isRightMouseButton(mouse.e)) {
                    deselectTimer.start();
                } else if (selectedPiece.isValidMove(newCol, newRow)) {
                    int oldCol = selectedPiece.col;
                    int oldRow = selectedPiece.row;
                    Piece capturedPiece = null;
                    for (int i = 0; i < pieces.size(); i++) {
                        if (pieces.get(i).col == newCol && pieces.get(i).row == newRow && pieces.get(i).color != turn) {
                            capturedPiece = pieces.remove(i);
                            break;
                        }
                    }
                    selectedPiece.move(newCol, newRow);
                    if (isUnderAttack(getKing(turn))) {
                        selectedPiece.move(oldCol, oldRow);
                        if (capturedPiece != null) {
                            pieces.add(capturedPiece);
                        }
                    } else {
                        selectedPiece = null;
                        turn = turn == WHITE ? BLACK : WHITE;
                    }
                }
            }
        }
    }

    private Piece getKing(int color) {
        for (Piece piece : pieces) {
            if (piece instanceof King && piece.color == color) {
                return piece;
            }
        }
        return null;
    }


    @Override
    public void run() {
        while (true) {
            repaint();
            update();
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }
}
