package piece;

import Game.ChessGame;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public abstract class Piece {
    public int x, y;
    public int col, row;
    public int tileSize;
    public int color;
    public BufferedImage image;
    protected ChessGame game;

    public Piece(int col, int row, int tileSize, int color, ChessGame game) {
        this.col = col;
        this.row = row;
        this.x = col * tileSize;
        this.y = row * tileSize;
        this.tileSize = tileSize;
        this.color = color;
        this.game = game;
    }

    public BufferedImage loadSvg(String path) {
        try {
            PNGTranscoder transcoder = new PNGTranscoder();
            transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, 70f);
            transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, 70f);
            TranscoderInput input = new TranscoderInput(getClass().getResourceAsStream(path));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(stream);
            transcoder.transcode(input, output);
            return ImageIO.read(new ByteArrayInputStream(stream.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x , y, null);
    }


    public boolean contains(int x, int y) {
        return x > this.x && x < this.x + tileSize && y > this.y && y < this.y + tileSize;
    }

    public abstract ArrayList<int[][]> showValidMoves();

    public void move(int col, int row) {
        this.col = col;
        this.row = row;
        this.x = col * tileSize;
        this.y = row * tileSize;
    }

    public boolean isValidMove(int col, int row) {
        ArrayList<int[][]> validMoves = showValidMoves();
        for (int[][] move : validMoves) {
            if (move[0][0] == col && move[0][1] == row) {
                return true;
            }
        }
        return false;
    }
}
