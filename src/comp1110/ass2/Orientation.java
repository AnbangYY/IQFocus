package comp1110.ass2;

public enum Orientation {
    NORTH, EAST, WEST, SOUTH;
    //Orientation indicates the possible direction of each piece
    public int toInt() {
        // This method convert orientation to associated integer to meet the requirement of task.

        if (this==NORTH)
            return 0;
        else if (this == EAST)
            return 1;
        else if (this==SOUTH)
            return 2;
        else return 3;
    }
}
