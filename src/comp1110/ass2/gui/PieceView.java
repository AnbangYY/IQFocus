package comp1110.ass2.gui;

import comp1110.ass2.FocusGame;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Creates a new view that represents an IMG element.
 *
 * @author Zhongxuan Liu u6730788@anu.edu.au
 * Modified by Li Anbang u6744849@anu.edu.au
 */
public class PieceView extends ImageView {
    private static final String URI_BASE = "assets/";
    char piece;
    boolean draggable;
    double H;
    double W;
    int topPosition; // valid only on board
    int leftPosition;
    String piecePlacement;
    private static DropShadow dropShadow;
    int homeX, homeY;
    double mouseX, mouseY; // the last known mouse positions (used when dragging)
    private Board board;

    private static final int[] homex = { Board.MARGIN_X, Board.MARGIN_X, Board.MARGIN_X,
            Board.GAME_X + Board.SQUARE_SIZE, Board.GAME_X + Board.SQUARE_SIZE * 5,
            763, 763, 763,
            Board.GAME_X + Board.SQUARE_SIZE*2, Board.GAME_X + Board.SQUARE_SIZE * 5 };
    private static final int[] homey = { 50, 250, 500, 50, 50, 500, 285, 50, 500, 500 };

    public PieceView(Board board, char p, boolean draggable) {
        this.board = board;
        piecePlacement = "";
        this.draggable = draggable;
        if (p > 'k') {
            throw new IllegalArgumentException("Bad tile: \"" + p + "\"");
        }
        Image image = new Image(Board.getUriPath(p + ".png"));
        setImage(image);
        this.piece = p;

        H = Board.SQUARE_SIZE * (int) (image.getHeight() / 100);
        W = Board.SQUARE_SIZE * (int) (image.getWidth() / 100);
        setFitHeight(Board.SQUARE_SIZE * (int) (image.getHeight() / 100));
        setFitWidth(Board.SQUARE_SIZE * (int) (image.getWidth() / 100));

        setEffect(dropShadow);

        homeX = homex[p - 'a'];
        homeY = homey[p - 'a'];

        setLayoutX(homeX);
        setLayoutY(homeY);
        setOnScroll(event -> { // scroll to change orientation

            if (getLayoutX() == homeX) {
                rotate();
            }
            event.consume();
        });

        setOnMousePressed(event -> { // mouse press indicates begin of drag
            if (this.draggable == true) {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                if (piecePlacement != "") {
                    System.out.println(" start move " + piecePlacement);
                    String placement = board.getPlacement();
                    int index = placement.indexOf(piecePlacement.charAt(0));
                    board.setPlacement(
                            placement.substring(0, index) + placement.substring(index + piecePlacement.length()));
                    piecePlacement = "";
                    System.out.println("after delete a picec:" + board.getPlacement());
                }
            }
        });

        setOnMouseDragged(event -> { // mouse is being dragged
            // hideCompletion();
            toFront();
            if (this.draggable == true) {
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;

                setLayoutX(getLayoutX() + movementX);
                setLayoutY(getLayoutY() + movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();

                event.consume();
            }
        });
        setOnMouseReleased(event -> { // drag is complete
            System.out.println("" + !checkOnBoard());
            if (!checkOnBoard()
                    || !FocusGame.isPlacementStringValid(board.getPlacement() + piecePlacement, board.getChallenge())) {
                snapToHome();
            } else {
                testSnap();
                board.setPlacement(board.getPlacement() + piecePlacement);
                System.out.println("after add a picec:" + board.getPlacement());
                if(board.checkCompletion()) {
                    this.draggable = false;
                }
            }
        });
    }

    private void testSnap() {
        System.out.println("position       -----0 0");
        switch ((int) (this.getRotate() / 90)) {
            case 0:
            case 2:
                this.setLayoutX(Board.GAME_X + leftPosition * Board.SQUARE_SIZE);
                this.setLayoutY(Board.GAME_Y + topPosition * Board.SQUARE_SIZE);
                break;
            case 1:
            case 3:
                this.setLayoutX(Board.GAME_X + leftPosition * Board.SQUARE_SIZE + H / 2 - W / 2);
                this.setLayoutY(Board.GAME_Y + topPosition * Board.SQUARE_SIZE + W / 2 - H / 2);
                break;
        }
    }

    /**
     * check all the shape board is in board, return ture
     * @author Zhongxuan Liu u6730788@anu.edu.au
     */
    private boolean checkOnBoard() {
        double curLeft = this.getLayoutX();
        double curRight = curLeft + W;
        double curTop = this.getLayoutY();
        double curBottom = curTop + H;
        double newTop = curTop + H / 2 - W / 2;
        double newBottom = curTop + H / 2 + W / 2;
        double newLeft = curLeft + W / 2 - H / 2;
        double newRight = curLeft + W / 2 + H / 2;
        System.out.println("rotate:" + (int) this.getRotate());
        if ((int) (this.getRotate() / 90) == 0 || (int) (this.getRotate() / 90) == 2) {
            if (curTop >= Board.GAME_Y && curTop <= Board.GAME_Y + Board.GAME_HEIGHT
                    && curBottom >= Board.GAME_Y && curBottom <= Board.GAME_Y + Board.GAME_HEIGHT
                    && curLeft >= Board.GAME_X && curLeft <= Board.GAME_X + Board.GAME_WIDTH
                    && curRight >= Board.GAME_X && curRight <= Board.GAME_X + Board.GAME_WIDTH) {
                leftPosition = (int) ((curLeft - Board.GAME_X) / Board.SQUARE_SIZE + 0.5);
                topPosition = (int) ((curTop - Board.GAME_Y) / Board.SQUARE_SIZE + 0.5);
                piecePlacement = "" + piece + leftPosition + topPosition + (int) (this.getRotate() / 90);
                return true;
            }
            return false;
        }
        if (newTop >= Board.GAME_Y && newTop <= Board.GAME_Y + Board.GAME_HEIGHT
                && newBottom >= Board.GAME_Y && newBottom <= Board.GAME_Y + Board.GAME_HEIGHT
                && newLeft >= Board.GAME_X && newLeft <= Board.GAME_X + Board.GAME_WIDTH
                && newRight >= Board.GAME_X && newRight <= Board.GAME_X + Board.GAME_WIDTH) {
            leftPosition = (int) ((newLeft - Board.GAME_X) / Board.SQUARE_SIZE + 0.5);
            topPosition = (int) ((newTop - Board.GAME_Y) / Board.SQUARE_SIZE + 0.5);
            piecePlacement = "" + piece + leftPosition + topPosition + (int) (this.getRotate() / 90);
            return true;
        }
        return false;
    }

    /**
     * If tiles are not on the grid, snap the mask to its home position
     * @author Zhongxuan Liu u6730788@anu.edu.au
     */
    public void snapToHome() {
        piecePlacement = "";
        setLayoutX(homeX);
        setLayoutY(homeY);
        setRotate(0);
    }

    private void rotate() {
        setRotate((getRotate() + 90) % 360);
        System.out.println("after rotate, the rotate is: " + getRotate());
    }
}
