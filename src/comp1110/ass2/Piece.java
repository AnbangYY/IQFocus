package comp1110.ass2;

import java.util.ArrayList;
import java.util.List;

public class Piece {
    char ch;
    int H;
    int W;
    private List<Tile> tiles;

    public List<Tile> getTiles() {
        return this.tiles;
    }
    public int getH() {
        return this.H;
    }
    public int getW() {
        return this.W;
    }
    public char getChar() {
        return this.ch;
    }
    public Piece(char p) {
        tiles = new ArrayList<>();
        this.ch = p;

        switch(p) {
            case 'a':
                this.tiles.add(new Tile(0,0,Color.G));
                this.tiles.add(new Tile(1,0,Color.W));
                this.tiles.add(new Tile(2,0,Color.R));
                this.tiles.add(new Tile(1,1,Color.R));
                W = 3;
                H = 2;
                break;
            case 'b':
                this.tiles.add(new Tile(1,0,Color.B));
                this.tiles.add(new Tile(2,0,Color.G));
                this.tiles.add(new Tile(3,0,Color.G));
                this.tiles.add(new Tile(0,1,Color.W));
                this.tiles.add(new Tile(1,1,Color.W));
                W = 4;
                H = 2;
                break;
            case 'c':
                this.tiles.add(new Tile(2,0,Color.G));
                this.tiles.add(new Tile(0,1,Color.R));
                this.tiles.add(new Tile(1,1,Color.R));
                this.tiles.add(new Tile(2,1,Color.W));
                this.tiles.add(new Tile(3,1,Color.B));
                W = 4;
                H = 2;
                break;
            case 'd':
                this.tiles.add(new Tile(0,0,Color.R));
                this.tiles.add(new Tile(1,0,Color.R));
                this.tiles.add(new Tile(2,0,Color.R));
                this.tiles.add(new Tile(2,1,Color.B));
                W = 3;
                H = 2;
                break;
            case 'e':
                this.tiles.add(new Tile(0,0,Color.B));
                this.tiles.add(new Tile(1,0,Color.B));
                this.tiles.add(new Tile(2,0,Color.B));
                this.tiles.add(new Tile(0,1,Color.R));
                this.tiles.add(new Tile(1,1,Color.R));
                W = 3;
                H = 2;
                break;
            case 'f':
                this.tiles.add(new Tile(0,0,Color.W));
                this.tiles.add(new Tile(1,0,Color.W));
                this.tiles.add(new Tile(2,0,Color.W));
                W = 3;
                H = 1;
                break;
            case 'g':
                this.tiles.add(new Tile(0,0,Color.W));
                this.tiles.add(new Tile(1,0,Color.B));
                this.tiles.add(new Tile(1,1,Color.B));
                this.tiles.add(new Tile(2,1,Color.W));
                W = 3;
                H = 2;
                break;
            case 'h':
                this.tiles.add(new Tile(0,0,Color.R));
                this.tiles.add(new Tile(1,0,Color.G));
                this.tiles.add(new Tile(2,0,Color.G));
                this.tiles.add(new Tile(0,1,Color.W));
                this.tiles.add(new Tile(0,2,Color.W));
                W = 3;
                H = 3;
                break;
            case 'i':
                this.tiles.add(new Tile(0,0,Color.B));
                this.tiles.add(new Tile(1,0,Color.B));
                this.tiles.add(new Tile(1,1,Color.W));
                H = 2;
                W = 2;
                break;
            case 'j':
                this.tiles.add(new Tile(0,0,Color.G));
                this.tiles.add(new Tile(1,0,Color.G));
                this.tiles.add(new Tile(2,0,Color.W));
                this.tiles.add(new Tile(3,0,Color.R));
                this.tiles.add(new Tile(0,1,Color.G));
                W = 4;
                H = 2;
                break;
        }
    }
}
