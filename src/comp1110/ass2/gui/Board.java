package comp1110.ass2.gui;

import java.awt.List;
import java.util.ArrayList;
import java.util.Set;

import comp1110.ass2.FocusGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.paint.Color;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Board extends Application {

    public static final int SQUARE_SIZE = 60;
    public static final int Columns = 9;
    public static final int Rows = 5;
    public static final int GAME_Y = 190;
    public static final int GAME_X = 190;
    public static final int BOARD_WIDTH = 970;
    public static final int BOARD_HEIGHT = 700;
    public static final int MARGIN_X = 20;
    public static final int MARGIN_Y = 20;
    public static final int GAME_WIDTH = SQUARE_SIZE * Columns;//540;
    public static final int GAME_HEIGHT = SQUARE_SIZE * Rows;//300;
    private static final int ROTATION_THRESHOLD = 50;

    private final Group root = new Group();
    private final Group board = new Group();
    private final Group control = new Group();
    private final Group pieces = new Group();
    private final Group hintSnap = new Group();
    private static final String URI_BASE = "assets/";
    private String challenge = "";private int challenge_i = 0;
    private int challenge_j = 0;
    private int challenge_k = 0;
    private String placement = "";
    private static PieceView[] PS = new PieceView[10];

    private static DropShadow dropShadow;

    private final Text completionText = new Text("Well done!");

    //private static final String BOARD_URI = Board.class.getResource(URI_BASE + "board.png").toString();
    private static final String BOARD_URI = getUriPath("boardM.png");

    public String getChallenge() {
        return this.challenge;
    }
    public String getPlacement() {
        return this.placement;
    }
    public void setPlacement(String place) {
        this.placement = place;
    }

    static String getUriPath(String picture) {
        return Board.class.getResource(URI_BASE + picture).toString();
    }
    public boolean checkCompletion() {
        if(placement.length() == 40) {
            System.out.println("test check .............");
            showCompletion();
            return true;
        }
        return false;
    }
    /*
    private void makeTest() {
    	ImageView apiece = new ImageView();
    	apiece.setImage(new Image("assets/a.png"));
    	apiece.setFitWidth(3*SQUARE_SIZE);
    	apiece.setFitHeight(2*SQUARE_SIZE);
    	apiece.setLayoutX(GAME_X);
    	apiece.setLayoutY(GAME_Y);

    	System.out.println("before rotate apiece X Y: " + apiece.getLayoutX() +  " " + apiece.getLayoutY());
    	System.out.println("before rotate apiece fit W H: " + apiece.getFitWidth() +  " " + apiece.getFitHeight());
    	System.out.println("orien:" + apiece.getNodeOrientation());
    	apiece.setRotate(90);
    	System.out.println("after rotate apiece X Y: " + apiece.getLayoutX() +  " " + apiece.getLayoutY());
    	System.out.println("before rotate apiece fit W H: " + apiece.getFitWidth() +  " " + apiece.getFitHeight());

    	System.out.println("orien:" + apiece.getNodeOrientation());
    	apiece.setLayoutX(160);
    	apiece.setLayoutY(220);
    	board.getChildren().add(apiece);
    }*/
    private void makeBoard() {
        board.getChildren().clear();

        ImageView baseboard = new ImageView();
        baseboard.setImage(new Image(BOARD_URI));
        baseboard.setFitWidth(GAME_WIDTH);
        baseboard.setFitHeight(GAME_HEIGHT);
        baseboard.setLayoutX(GAME_X);
        baseboard.setLayoutY(GAME_Y);
        baseboard.setViewOrder(100.0);
        board.getChildren().add(baseboard);

        board.toBack();
        //makeChallenge();
        makeInterestChallenge();
    }
    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places

    private void makePieces() {
        pieces.getChildren().clear();
        int i =0;
        for (char m = 'a'; m < 'k'; m++) {
            PieceView d = new PieceView(this, m,true);
            pieces.getChildren().add(d);
            PS[i] = d;
            i = i + 1;
        }
    }

    /**
     * Create the controls that allow the game to be restarted
     * @author Zhongxuan Liu u6730788@anu.edu.au & Li Anbang u6744849@anu.edu.au
     */
    private void makeControls() {
        Button button = new Button("Restart");
        button.setLayoutX(650);
        button.setLayoutY(620);
        button.setStyle( "-fx-background-radius: 5em; " +
                "-fx-min-width: 50px; " +
                "-fx-min-height: 50px; " +
                "-fx-max-width: 50px; " +
                "-fx-max-height: 50px; " +
                "-fx-background-color: -fx-body-color;" +
                "-fx-background-insets: 0px; " +
                "-fx-padding: 0px;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {resetPieces();}
        });
        control.getChildren().add(button);

        Button button2 = new Button("Hint");
        button2.setLayoutX(760);
        button2.setLayoutY(630);
        button2.setOnMouseEntered(event-> {getHint();});
        button2.setOnMouseExited(event->{clearHint();});
        control.getChildren().add(button2);

        Button button3 = new Button("ChangeChallenge");
        button3.setLayoutX(820);
        button3.setLayoutY(630);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {resetPieces();makeBoard();}
        });
        control.getChildren().add(button3);

    }

    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)
    /**
     * Show the challenge in the middle of the board
     * @author Anbang Li u6744849@anu.edu.au
     */
    private void makeChallenge() {
        challenge = FocusGame.genStartChallenge();
        System.out.println("challenge:" + challenge);
        int[] positions = new int[]{12,13,14,21,22,23,30,31,32};
        for(int i = 0; i < challenge.length(); ++i) {
            ImageView onelattice = new ImageView();
            onelattice.setImage(new Image(getUriPath("sq-"+Character.toLowerCase(challenge.charAt(i))+".png")));
            onelattice.setFitWidth(SQUARE_SIZE);
            onelattice.setFitHeight(SQUARE_SIZE);
            onelattice.setLayoutX(GAME_X + (positions[i]%Columns) * SQUARE_SIZE);
            onelattice.setLayoutY(GAME_Y + (positions[i]/Columns) * SQUARE_SIZE);
            onelattice.setViewOrder(99.0);
            board.getChildren().add(onelattice);
        }
    }
    // FIXME Task 10: Implement hints
    /**
     * When the mouse is over the hint key, small pieces of the current status that can be placed on the board are displayed in the board
     * @author Anbang Li u6744849@anu.edu.au
     */
    private void getHint() {
        hintSnap.getChildren().clear();
        Set<String> hints = FocusGame.genHint(this.placement,
                this.challenge);
        if(hints == null) {
            return;
        }
        Object[] hintArray = hints.toArray();
        System.out.println("hints: "+ hints);
        String hint = (String)hintArray[(int)(Math.random()*hintArray.length)];
        System.out.println("one hint: "+ hint);
        int orientation = hint.charAt(3) - '0';
        Image image = new Image(getUriPath(hint.charAt(0)+".png"));
        ImageView imageView = new ImageView(image);
        double W = SQUARE_SIZE * (int) (image.getWidth() / 100);
        double H = SQUARE_SIZE * (int) (image.getHeight() / 100);
        imageView.setFitHeight(H);
        imageView.setFitWidth(W);
        switch(orientation) {
            case 0:
            case 2:
                imageView.setLayoutX(GAME_X + (hint.charAt(1)-'0')*SQUARE_SIZE);
                imageView.setLayoutY(GAME_Y + (hint.charAt(2)-'0')*SQUARE_SIZE);
                break;
            case 1:
            case 3:
                imageView.setLayoutX(GAME_X + (hint.charAt(1)-'0') * SQUARE_SIZE + H / 2 - W / 2);
                imageView.setLayoutY(GAME_Y + (hint.charAt(2)-'0') * SQUARE_SIZE + W / 2 - H / 2);
                break;
        }
        imageView.setRotate(orientation*90);
        hintSnap.getChildren().add(imageView);
        hintSnap.toFront();
    }

    private void clearHint() {
        hintSnap.getChildren().clear();
    }
    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)
    /**
     * Click challenges, search and create a new challenge
     * @author Anbang Li u6744849@anu.edu.au
     */
    private void makeInterestChallenge() {
        //challenge = FocusGame.genStartChallenge();
        ArrayList<String> result = (ArrayList<String>) FocusGame.getNewChallenge(this.challenge_i, this.challenge_j, this.challenge_k);
        challenge = result.get(0);

        this.challenge_i = Integer.parseInt(result.get(1));
        this.challenge_j = Integer.parseInt(result.get(2));
        this.challenge_k = Integer.parseInt(result.get(3)) + 1;
        if(this.challenge_k == 64) {
            challenge_k = 0;
            ++challenge_j;
        }
        if(challenge_j == 64) {
            challenge_j = 0;
            ++challenge_i;
        }

        System.out.println("challenge:" + challenge);
        int[] positions = new int[]{12,13,14,21,22,23,30,31,32};
        for(int i = 0; i < challenge.length(); ++i) {
            ImageView onelattice = new ImageView();
            onelattice.setImage(new Image(getUriPath("sq-"+Character.toLowerCase(challenge.charAt(i))+".png")));

            onelattice.setFitWidth(SQUARE_SIZE);
            onelattice.setFitHeight(SQUARE_SIZE);
            onelattice.setLayoutX(GAME_X + (positions[i]%Columns) * SQUARE_SIZE);
            onelattice.setLayoutY(GAME_Y + (positions[i]/Columns) * SQUARE_SIZE);
            onelattice.setViewOrder(99.0);
            board.getChildren().add(onelattice);
        }
    }
    /**
     * Create the message when the player finish the game.
     * @author Anbang Li u6744849@anu.edu.au
     */
    private void makeCompletion() {
        completionText.setFill(javafx.scene.paint.Color.RED);
        completionText.setEffect(dropShadow);
        completionText.setCache(true);
        completionText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 80));
        completionText.setLayoutX(250);
        completionText.setLayoutY(375);
        completionText.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(completionText);
        hideCompletion();
    }
    /**
     * Show the completion message after finish the game
     * @author Anbang Li u6744849@anu.edu.au
     */
    private void showCompletion() {
        System.out.print("complete");
        completionText.toFront();
        completionText.setOpacity(1);
    }
    /**
     * Hide the completion message
     * @author Anbang Li u6744849@anu.edu.au
     */
    private void hideCompletion() {
        completionText.toBack();
        completionText.setOpacity(0);
    }

    /**
     * Put all of the tiles back in their home position
     * @author Zhongxuan Liu u6730788@anu.edu.au
     * Modified by Li Anbang u6744849@anu.edu.au
     */
    private void resetPieces() {
        pieces.toFront();
        placement = "";
        hideCompletion();
        for (Node n : pieces.getChildren()) {
            ((PieceView) n).snapToHome();
        }
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("IQ-focus");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT, Color. SLATEGRAY);

        root.getChildren().add(board);
        root.getChildren().add(pieces);
        root.getChildren().add(control);
        root.getChildren().add(hintSnap);


        makeBoard();
        makePieces();
        makeControls();
        makeCompletion();


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
