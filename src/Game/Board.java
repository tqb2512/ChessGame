package Game;

import piece.Piece;

import java.awt.*;

public class Board {
    int rows = 8;
    int columns = 8;
    int tileSize = 70;
    int boardWidth = rows * tileSize;
    int boardHeight = columns * tileSize;
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.setColor(new Color(255, 206, 158));
                } else {
                    g2d.setColor(new Color(209, 139, 71));
                }
                g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
            }
        }
    }
}
