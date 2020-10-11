package comp1110.ass2;
import comp1110.ass2.Color.*;

import java.util.ArrayList;
import java.util.List;

import static comp1110.ass2.Color.R;

public enum TileType {
    A,B,C,D,E,F,G,H,I,J;
    static List<Color [][]> colors = new ArrayList<>();
    static {
        Color[][] AS = new Color[][]{{Color.G,Color.W,Color.R},
                {Color.NULL,Color.R,Color.NULL}};

        Color[][] BS = new Color[][]{{Color.NULL,Color.B,Color.G,Color.G},
                {Color.W,Color.W,Color.NULL,Color.NULL}};

        Color[][] CS = new Color[][]{{Color.NULL,Color.NULL,Color.G,Color.NULL},
                {Color.R,Color.R,Color.W,Color.B}};

        Color[][] DS = new Color[][]{{Color.R,Color.R,Color.R},
                {Color.NULL,Color.NULL,Color.B}};

        Color[][] ES = new Color[][]{{Color.B,Color.B,Color.B},
                {Color.R,Color.R,Color.NULL}};

        Color[][] FS = new Color[][]{{Color.W,Color.W,Color.W}};

        Color[][] GS = new Color[][]{{Color.W,Color.B,Color.NULL},
                {Color.NULL,Color.B,Color.W}};

        Color[][] HS = new Color[][]{{Color.R,Color.G,Color.G},
                {Color.W,Color.NULL,Color.NULL},
                {Color.W,Color.NULL,Color.NULL}};

        Color[][] IS = new Color[][]{{Color.B,Color.B},
                {Color.NULL,Color.W}};

        Color[][] JS = new Color[][]{{Color.G,Color.G,Color.W,Color.R},
                {Color.G,Color.NULL,Color.NULL,Color.NULL}};
        colors.add(AS);
        colors.add(BS);
        colors.add(CS);
        colors.add(DS);
        colors.add(ES);
        colors.add(FS);
        colors.add(GS);
        colors.add(HS);
        colors.add(IS);
        colors.add(JS);

    }
    public  Color[][] getState(){
        if(this.name().equals("A"))
            return colors.get(0);
        if(this.name().equals("B"))
            return colors.get(1);
        if(this.name().equals("C"))
            return colors.get(2);
        if(this.name().equals("D"))
            return colors.get(3);
        if(this.name().equals("E"))
            return colors.get(4);
        if(this.name().equals("F"))
            return colors.get(5);
        if(this.name().equals("G"))
            return colors.get(6);
        if(this.name().equals("H"))
            return colors.get(7);
        if(this.name().equals("I"))
            return colors.get(8);
        if(this.name().equals("J"))
            return colors.get(9);
        return null;
    }

}
