package comp1110.ass2;

import java.util.ArrayList;
import java.util.List;

import static comp1110.ass2.Color.NULL;
import static comp1110.ass2.TileType.*;

public class Placement {
    char shape;
    int x;
    int y;
    int orientation;


    public Placement(char shape, int x, int y, int orientation) {
        this.shape = shape;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }
    public Placement(String placement) {
        this.shape = placement.charAt(0);
        this.x = Integer.parseInt(String.valueOf(placement.charAt(1)));
        this.y = Integer.parseInt(String.valueOf(placement.charAt(2)));
        this.orientation = Integer.parseInt(String.valueOf(placement.charAt(3)));
    }
    public char getShape() {
        return shape;
    }

    public void setShape(char shape) {
        this.shape = shape;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public List<Tile> getTiles(){
        List <Tile> tiles = new ArrayList<>();
        Color [][] State = getType(shape).getState();
        if(orientation == 0){

            for(int i =0 ;i < State.length; i++)
                for (int j = 0; j < State[i].length; j++) {
                    if(State[i][j] != NULL){
                        Tile tile = new Tile(j+x,i+y,State[i][j]);
                        tiles.add(tile);
                    }
                }
        }
        if(orientation == 1){
            for(int i =0 ;i < State.length; i++)
                for (int j = 0; j < State[i].length; j++) {
                    if(State[i][j] != NULL) {
                        Tile tile = new Tile(-i-1+State.length+x,j+y,State[i][j]);
                        tiles.add(tile);
                    }
                }

        }
        if(orientation == 2){
            for(int i =0 ;i < State.length; i++)
                for (int j = 0; j < State[i].length; j++) {
                    if(State[i][j] != NULL) {
                        Tile tile = new Tile(-j - 1 + State[i].length + x, -i - 1 + State.length + y, State[i][j]);
                        tiles.add(tile);
                    }
                }

        }
        if(orientation == 3){
            for(int i =0 ;i < State.length; i++)
                for (int j = 0; j < State[i].length; j++) {
                    if(State[i][j] != NULL)
                    {
                        Tile tile = new Tile(i + x, -j - 1 + State[i].length + y, State[i][j]);
                        tiles.add(tile);

                    }
                }

        }
        return tiles;
    }

    public TileType getType(char shape){
        if (shape == 'a')return A;
        if (shape == 'b')return B;
        if (shape == 'c')return C;
        if (shape == 'd')return D;
        if (shape == 'e')return E;
        if (shape == 'f')return F;
        if (shape == 'g')return G;
        if (shape == 'h')return H;
        if (shape == 'i')return I;
        if (shape == 'j')return J;
        return null;
    }

}
