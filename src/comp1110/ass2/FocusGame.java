package comp1110.ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */
public class FocusGame {

    private static final int Columns = 9;
    private static final int Rows = 5;
    private static Tile [][] tiles;
    private static List<String> impossible;
    private static List<Piece> pieces;
    private static Map<Integer, Integer> challengeMap = new HashMap<>();

    static {
        impossible = new ArrayList<>();

        int[] positions = {12,13,14,21,22,23,30,31,32};
        for(int i = 0; i < positions.length; ++i) {
            challengeMap.put(positions[i], i);
        }
        System.out.println("----------start ----"+ challengeMap.containsKey(12));
        pieces = new ArrayList<>();
        for(char p = 'a'; p <= 'j'; ++p) {
            pieces.add(new Piece(p));
        }
    }
    static public void printTiles() {
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 9; ++j) {
                if(tiles[i][j] != null) {
                    System.out.println(""+i +j+" " + tiles[i][j].toString());
                }
            }
        }
    }
    static public void initializeBoardState(String placement){
        tiles = new Tile[5][9];
        tiles[4][0] = new Tile(4,0,Color.EMPTY);
        tiles[4][8] = new Tile(4,7,Color.EMPTY);
        if (!placement.isEmpty())
            for(int i =0; i<placement.length();i=i+4){
                String pla = placement.substring(i,i+4);
                addTileToBoard(placement);

            }

    }
    static void addTileToBoard(String placement){
        Placement pla = new Placement(placement);

        /* update the tile data structure for the two squares that compose this tile */
        List<Tile> tileList = pla.getTiles();

        for (Tile tile: tileList){

            updateTiles(tile);
        }
    }

    static void updateTiles(Tile tile){
        tiles[tile.getY()][tile.getX()] = tile;

    }
    /**
     * Determine whether a piece placement is well-formed according to the
     * following criteria:
     * - it consists of exactly four characters
     * - the first character is in the range a .. j (shape)
     * - the second character is in the range 0 .. 8 (column)
     * - the third character is in the range 0 .. 4 (row)
     * - the fourth character is in the range 0 .. 3 (orientation)
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    static boolean isPiecePlacementWellFormed(String piecePlacement) {
        // FIXME Task 2: determine whether a piece placement is well-formed

        return piecePlacement.length() == 4 && piecePlacement.charAt(0) >= 'a' && piecePlacement.charAt(0) <= 'j'
                && Integer.parseInt(String.valueOf(piecePlacement.charAt(1))) >= 0 && Integer.parseInt(String.valueOf(piecePlacement.charAt(1))) <= 8
                && Integer.parseInt(String.valueOf(piecePlacement.charAt(2))) >= 0 && Integer.parseInt(String.valueOf(piecePlacement.charAt(2))) <= 4
                && Integer.parseInt(String.valueOf(piecePlacement.charAt(3))) >= 0 && Integer.parseInt(String.valueOf(piecePlacement.charAt(3))) <= 3;
    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 10);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementStringWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed
        if(placement.length()<4 || placement.length()%4 != 0)return false;
        HashMap<Character,String> placementHashmap = new HashMap<>();
        String regex = "[a-zA-Z]\\d*";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(placement);
        while (matcher.find()){
            String placementTemp = matcher.group();
            if(!isPiecePlacementWellFormed(placementTemp))return false;
            if(placementHashmap.containsKey(placementTemp.charAt(0)))return false;
            placementHashmap.put(placementTemp.charAt(0),placementTemp);
        }
        return true;
    }

    /**
     * Determine whether a placement string is valid.
     *
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     *   rules of the game:
     *   - pieces must be entirely on the board
     *   - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     */
    static List<String> getPlacements(String placement){
        List<String> placements = new ArrayList<>();
        for(int i =0; i<placement.length();i=i+4){
            placements.add(placement.substring(i,i+4));
        }
        return placements;
    }
    public static boolean isPlacementStringValid(String placement) {
        // FIXME Task 5: determine whether a placement string is valid
        if(!isPlacementStringWellFormed(placement))
            return false;
        initializeBoardState("");
        List<String> placements = getPlacements(placement);
        for(String pla:placements) {
            Placement pla1 = new Placement(pla);
            List<Tile> tileList = pla1.getTiles();
            for (Tile tile : tileList) {
                if (tile.getX() < 0 || tile.getX() > 8 || tile.getY() < 0 || tile.getY() > 4 || tiles[tile.getY()][tile.getX()] != null)
                    return false;
                updateTiles(tile);
            }
        }
        return true;
    }

    public static boolean isPlacementStringValid(String placement, String challenge) {
        if(!isPlacementStringWellFormed(placement))
            return false;
        initializeBoardState("");
        List<String> placements = getPlacements(placement);
        //Tiles don't overlap with each other and challenge
        List<Integer> positions = new ArrayList<>();

        for(String pla:placements) {
            Placement pla1 = new Placement(pla);
            List<Tile> tileList = pla1.getTiles();
            for (Tile tile : tileList) {
                int X = tile.getX();
                int Y = tile.getY();
                int position = Y * Columns + X;
                if (X < 0 || X > 8 || Y < 0 || Y > 4 || position == 36 || position == 44 || positions.contains(position)) {
                    return false;
                }
                if(challengeMap.containsKey(position)) {

                    if (challenge.length() > 0) {
                        if (challengeMap.containsKey(position)) {
                            int index = challengeMap.get(position);
                            if (tile.getColor().toString().charAt(0) != challenge.charAt(index)) {
                                return false;
                            }
                        }
                    }
                }
                positions.add(position);
            }
        }
        return true;
    }

    /**
     * Given a string describing a placement of pieces and a string describing
     * a challenge, return a set of all possible next viable piece placements
     * which cover a specific board cell.
     *
     * For a piece placement to be viable
     * - it must be valid
     * - it must be consistent with the challenge
     *
     * @param placement A viable placement string
     * @param challenge The game's challenge is represented as a 9-character string
     *                  which represents the color of the 3*3 central board area
     *                  squares indexed as follows:
     *                  [0] [1] [2]
     *                  [3] [4] [5]
     *                  [6] [7] [8]
     *                  each character may be any of
     *                  - 'R' = RED square
     *                  - 'B' = Blue square
     *                  - 'G' = Green square
     *                  - 'W' = White square
     * @param col      The cell's column.
     * @param row      The cell's row.
     * @return A set of viable piece placements, or null if there are none.
     */
    public static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
        // FIXME Task 6: determine the set of all viable piece placements given existing placements and a challenge
        Map<Character, Boolean> placementMap = new HashMap<>();
        //Use HashMap, true and false to distinguish which has been put in the placement and which is the candidate
        for(char p = 'a'; p <= 'j'; ++p) {
            if(placement.indexOf(p) != -1) {
                placementMap.put(p, true);
            } else {
                placementMap.put(p, false);
            }
        }
        Set<Integer> pset = genPositions(placement);   //Save the location where tiles have been placed
        pset.add(36);    //Coordinates of the lower left corner
        pset.add(44);    //Coordinates of the lower right corner
        System.out.println(pset);
        Set<String> result = new TreeSet<String>();
        int count = 0;
        int position = row * Columns + col;  //Unifies rows and columns in a number
        char stickChar = '-';
        if(challengeMap.containsKey(position)) {
            stickChar = challenge.charAt(challengeMap.get(position));
        }  //If the point coordinates belong to challenge, consider the color

        for(Character p : placementMap.keySet()) {
            if(!placementMap.get(p)) {
                Piece piece = pieces.get(p-'a');  //View a candidate piece
                System.out.println("try character viabale " + p + " stickchar:" + stickChar);
                for (int orientation = 0; orientation < 4; ++orientation) {
                    for (Tile tile1 : piece.getTiles()) {   // Assume tile1 in (col,row)
                        if(stickChar != '-' && tile1.getColor().toString().charAt(0) != stickChar) {
                            continue;   //If the colors don't match, continue
                        }
                        count = 0;    //Count indicates that how many piece in the tile that meet the requirements.
                        for (Tile tile2 : piece.getTiles()) { // try to get position for tile2
                            if (!tile1.equals(tile2)) {  //Check whether other tiles are correct
                                position = getRelativeCordinate(orientation, tile1, tile2, col, row);
                                //When tile1 is on (col row) and the direction is orientation, calculate the coordinate position of tile2.
                                if (position == -1 || pset.contains(position)) {
                                    break;
                                }      //   Break when location conflicts
                                //check challenge
                                if(challengeMap.containsKey(position)) {     //Determine colors
                                    if(tile2.getColor().toString().charAt(0) == challenge.charAt(challengeMap.get(position))) {
                                        ++count;
                                    }
                                } else {
                                    ++count;
                                }
                            }
                        }
                        if (count == piece.getTiles().size() - 1) {    //All the pieces of this tile are suitable

                            result.add(getLeftTopCordinate(orientation,tile1,piece,col,row));
                            //Calculate the leftmost and topmost coordinates according to tile1's coordinates and direction
                        }
                    }
                }
            }
        }
        if(result.size() > 0) {
            return result;
        }
        return null;
    }

    /*Converts rows and columns to a numeric representation of the location.
      The first row, the first column is 0, the first row, the second column is 1, and so on until 43
      (because there are no 36 and 44 positions on the board)
     */
    private static Set<Integer> genPositions(String placement) {
        Set<Integer> pset = new TreeSet<>();
        List<Tile> tileList = new ArrayList<>();
        for(int i = 0; i < placement.length(); i += 4) {
            tileList.addAll(new Placement(placement.substring(i, i+4)).getTiles());
        }
        for(Tile tile : tileList) {
            pset.add(tile.getY() * Columns + tile.getX());
        }
        return pset;
    }



    private static String getLeftTopCordinate(int orientation, Tile t1, Piece piece, int col, int row) {
        //Calculate the leftmost and topmost coordinates according to tile1's coordinates and direction
        int left = 0;
        int top = 0;
        switch(orientation) {
            case 0:
                left = col - t1.getX();
                top = row - t1.getY();
                break;
            case 1:
                left = col - (piece.getH() - 1 - t1.getY());
                top = row - t1.getX();
                break;
            case 2:
                left = col - (piece.getW() - 1 - t1.getX());
                top = row - (piece.getH() - 1 - t1.getY());
                break;    //If tile rotates 180 degrees, consider width and height.
            case 3:
                left = col - t1.getY();
                top = row - (piece.getW()-1-t1.getX());
                break;
        }
        return ""+piece.getChar()+left + top + orientation;

    }

    //Calculate tile2 position according to tile1 position
    private static int getRelativeCordinate(int orientation, Tile t1, Tile t2, int col, int row) {
        int x = 0;
        int y = 0;
        switch(orientation) {
            case 0:
                x = col + t2.getX() - t1.getX();
                y = row + t2.getY() - t1.getY();
                break;
            case 1:
                x = col + t1.getY() - t2.getY();
                y = row + t2.getX() - t1.getX();
                break;
            case 2:
                x = col + t1.getX() - t2.getX();
                y = row + t1.getY() - t2.getY();
                break;
            case 3:
                x = col + t2.getY() - t1.getY();
                y = row + t1.getX() - t2.getX();
                break;
        }
        int position = y * Columns + x;
        if(x < 0 || x >= Columns || y < 0 || y >= Rows || position==36 || position==44) {
            return -1;
        }

        return position;

    }      //Calculating the current coordinates of tile2 with the current coordinates of tile1

    public static String genStartChallenge() {
        String challenge = "RRRBWBBRB";
        return challenge;

    }

    public static List<String> getNewChallenge(int ii, int jj, int kk) {
        /*
        Create a new challenge. The challenge is a 3 * 3 area, each row has three squares,
        each of which has four possible colors. There are 4 * 4 * 4 = 64 possibilities in each line.
        Traverse all possibilities, and get a challenge with only one solution through getsolution.
         */
        char[] color = {'R','G','W','B'};
        List<String> result = new ArrayList<>();
        String challenge = "";
        List<String> placement = null;
        for(int i = ii; i < 64; ++i) {
            for(int j = jj; j < 64; ++j) {
                for(int k = kk; k < 64; ++k) {
                    challenge = ""+color[i/16]+color[(i%16)/4]+color[i%4]+
                            color[j/16]+color[(j%16)/4]+color[j%4]+
                            color[k/16]+color[(k%16)/4]+color[k%4];
                    placement = FocusGame.getAllSolutions(challenge);
                    if(placement.size() == 1) {
                        System.out.println(challenge+ " " + i+" "+j+" "+k);
                        result.add(challenge);
                        result.add(""+i);
                        result.add(""+j);
                        result.add(""+k);
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public static Set<String> genHint(String placement, String challenge) {
        /*When the mouse is over the hint key, small pieces of the current status
          that can be placed on the board are displayed in the board

         */
        Set<Integer> pset = genPositions(placement);
        for(int i = 0; i < Columns * Rows-1;++i) {
            if(!pset.contains(i) && i != 36) {
                int col = i % Columns;
                int row = i / Columns;
                Set<String> startSet = getViablePiecePlacements(placement, challenge, col, row);
                return startSet;
            }
        }
        return null;
    }


    /**
     * Return the canonical encoding of the solution to a particular challenge.
     *
     * A given challenge can only solved with a single placement of pieces.
     *
     * Since some piece placements can be described two ways (due to symmetry),
     * you need to use a canonical encoding of the placement, which means you
     * must:
     * - Order the placement sequence by piece IDs
     * - If a piece exhibits rotational symmetry, only return the lowest
     *   orientation value (0 or 1)
     *
     * @param challenge A challenge string.
     * @return A placement string describing a canonical encoding of the solution to
     * the challenge.
     */
    public static String getSolution(String challenge) {
        long startTime = System.currentTimeMillis();

        List<Map<String, List<Tile> > > candidateList = new ArrayList<>();
        int index = 0;
        for(int i = (int)'a'; i < (int)'a'+10; ++i) {
            //For each tile, all possible positions on borad
            candidateList.add(genCandidate((char)i, challenge));
            System.out.println((char)i + " " +candidateList.get(index).size());
            ++index;
        }

        //Each piece has multiple candidate positions, which are arranged according to the number of candidates.

        Collections.sort(candidateList, new Comparator<Map<String, List<Tile> >>() {

            @Override
            public int compare(Map<String, List<Tile>> o1, Map<String, List<Tile>> o2) {
                int diff = o1.size() - o2.size();
                if(diff > 0) {
                    return 1;
                } else if(diff < 0) {
                    return -1;
                }
                return 0;
            }
        });

        Map<String, List<Tile> > abcList = getMerge(candidateList);   //Merge strategy (start with the smallest number of options to reduce time)


        String result = null;
        for(String key : abcList.keySet()) {
            result = key;
            System.out.print(key+ "-");
            for(Tile tile : abcList.get(key)) {
                System.out.print(tile.toString() + " ");
            }
            System.out.println();
        }

        System.out.println("result size:" + abcList.size());
        System.out.println(System.currentTimeMillis() - startTime);
        // FIXME Task 9: determine the solution to the game, given a particular challenge

        return result;
    }

    public static List<String> getAllSolutions(String challenge) {
        List<Map<String, List<Tile> > > candidateList = new ArrayList<>();
        int index = 0;
        for(int i = (int)'a'; i < (int)'a'+10; ++i) {
            candidateList.add(genCandidate((char)i, challenge));
            ++index;
        }

        Collections.sort(candidateList, new Comparator<Map<String, List<Tile> >>() {

            @Override
            public int compare(Map<String, List<Tile>> o1, Map<String, List<Tile>> o2) {
                int diff = o1.size() - o2.size();
                if(diff > 0) {
                    return 1;
                } else if(diff < 0) {
                    return -1;
                }
                return 0;
            }
        });
        //Merge strategy (start with the smallest number of options to reduce time)
        Map<String, List<Tile> > abcList = getMerge(candidateList);
        List<String> result = new ArrayList<String>();
        for(String key : abcList.keySet()) {
            result.add(key);
        }

        return result;
    }
       //Check whether the tiles in the two lists are compatible (whether the XY coordinates coincide)
    private static boolean isTwoTilesValid(List<Tile> a, List<Tile> b) {
        Set<Integer> testSet = new TreeSet<>();
        for(Tile tile : a) {
            testSet.add(tile.getY()*Columns + tile.getX());
        }
        for(Tile tile : b) {            if(testSet.contains(tile.getY()*Columns + tile.getX())) {
                return false;
            }
        }
        return true;
    }
    private static Map<String, List<Tile> > getMerge(Map<String, List<Tile> > aList, Map<String, List<Tile> > bList) {
        Map<String, List<Tile> > pieceMap = new HashMap<>();
        //Traverse 2 candidate sets
        for (Map.Entry<String, List<Tile>> a : aList.entrySet()) {
            for (Map.Entry<String, List<Tile>> b : bList.entrySet()) {
                if(isTwoTilesValid(a.getValue(), b.getValue())) {        //Check for coexistence
                    List<Tile> c = new ArrayList<>(a.getValue());
                    c.addAll(b.getValue());
                    if(isMaginValid(c)) {     //In order to improve efficiency, remove the corners
                        pieceMap.put(a.getKey()+b.getKey(), c);
                    }
                }
            }
        }

        return pieceMap;
    }

    //Combine multiple pieces of candidate positions, take the intersection, and leave the ones that can coexist. if all pieces can be coexisted, it is solution.
    private static Map<String, List<Tile> > getMerge(List<Map<String, List<Tile> > > alist ) {
        Map<String, List<Tile> > first = alist.get(0);
        for(int i = 1; i < alist.size(); ++i) {
            first = getMerge(first, alist.get(i));
        }
        return first;
    }


    private static Map<String, List<Tile> > genCandidate(char ch, String challenge) {
        Map<String, List<Tile> > pieceMap = new HashMap<>();
        for (int i = 0; i < Columns; ++i) {
            for (int j = 0; j < Rows; ++j) {
                int maxDirection = 4;
                if(ch == 'f' || ch == 'g') {
                    maxDirection = 2;
                }
                for (int direction = 0; direction < maxDirection; ++direction) {
                    String pieceString = "" + ch + i + j + direction;
                    if(impossible.contains(pieceString)) {
                        continue;
                    }
                    Placement piecePlacement = new Placement(pieceString);
                    List<Tile> tiles = isPiecePlacementStringValid(piecePlacement, challenge);
                    if (tiles != null) {
                        pieceMap.put(pieceString, tiles);
                    }
                }
            }
        }
        return pieceMap;
    }


    private static boolean isMaginValid(List<Tile> tileList) {
        Set<Integer> testSet = new TreeSet<>();
        for(Tile tile : tileList) {
            testSet.add(tile.getY()*Columns + tile.getX());
        }

        for(int i= 0; i < Columns * Rows-1; ++i) {
            if(!testSet.contains(i) && i != 36) {
                //Check the blank in front, back, left and right.
                int blank = 0;

                if (i % 9 != 0) {
                    if (i - 1 >= 0 && i - 1 != 36 && !testSet.contains(i - 1)) {
                        ++blank;
                    }
                }

                if (i % 9 != 8) {
                    if (i + 1 <= Columns * Rows - 2 && i + 1 != 36 && !testSet.contains(i + 1)) {
                        ++blank;
                    }
                }

                if(i-Columns >= 0 && !testSet.contains(i-Columns)) {
                    ++blank;
                }
                if(i+Columns <= Columns * Rows-2 && i+Columns != 36 && !testSet.contains(i+Columns)) {
                    ++blank;
                }
                if(blank == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    //challenge == "" or "GGGGGGGGG"
    public static List<Tile> isPiecePlacementStringValid(Placement piecePlacement, String challenge) {
        List<Tile> tileList = piecePlacement.getTiles();
        for (Tile tile : tileList) {
            if (tile.getX() < 0 || tile.getX() > 8 || tile.getY() < 0 || tile.getY() > 4) {
                return null;
            }

            int position = tile.getY() * Columns + tile.getX();
            if(position == 36 || position == 44) {
                return null;
            }
            if (challenge.length() > 0) {
                if (challengeMap.containsKey(position)) {
                    int index = challengeMap.get(position);
                    if (tile.getColor().toString().charAt(0) != challenge.charAt(index)) {
                        return null;
                    }
                }
            }
        }

        if(isMaginValid(tileList)) {
            return tileList;
        }
        return null;

    }


}
