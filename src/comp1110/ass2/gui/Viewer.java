package comp1110.ass2.gui;

import comp1110.ass2.FocusGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * A very simple viewer for piece placements in the IQ-Focus game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 480;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField textField;
    private int n = 0;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {

        if (FocusGame.isPlacementStringValid(placement)){
            for (int i = 0; i < n; i++){
                root.getChildren().remove(1);
            }
            n = placement.length() / 4;
            String p = "";
            for (int i = 0; i < placement.length() / 4; i++){
                for (int j = 0; j < 4; j ++){
                    p = p + placement.charAt(i * 4 + j);
                }
                viewImage(p);
                p = "";
            }
        }
        // FIXME Task 4: implement the simple placement viewer

    }

    public void viewImage(String p) {
        Image image = new Image(Viewer.class.getResource(URI_BASE + p.charAt( 0 ) + ".png").toString());
        ImageView imageView = new ImageView(image);
        int x = 0;
        int y = 0;
        int a = p.charAt(3) - '0';
        if (p.charAt(0) == 'a') {
            imageView.setFitWidth(180);
            imageView.setFitHeight(120);
            if (a == 1 || a == 3) {
                x = 30;
                y = 30;
            }
        }
        if (p.charAt(0) == 'b') {
            imageView.setFitWidth(240);
            imageView.setFitHeight(120);
            if (a == 1 || a == 3) {
                x = 60;
                y = 60;
            }
        }
        if (p.charAt(0) == 'c') {
            imageView.setFitWidth(240);
            imageView.setFitHeight(120);
            if (a == 1 || a == 3) {
                x = 60;
                y = 60;
            }
        }
        if (p.charAt(0) == 'd') {
            imageView.setFitWidth(180);
            imageView.setFitHeight(120);
            if (a == 1 || a == 3) {
                x = 30;
                y = 30;
            }
        }
        if (p.charAt(0) == 'e') {
            imageView.setFitWidth(180);
            imageView.setFitHeight(120);
            if (a == 1 || a == 3) {
                x = 30;
                y = 30;
            }
        }
        if (p.charAt(0) == 'f') {
            imageView.setFitWidth(180);
            imageView.setFitHeight(60);
            if (a == 1 || a == 3) {
                x = 60;
                y = 60;
            }
        }
        if (p.charAt(0) == 'g') {
            imageView.setFitWidth(180);
            imageView.setFitHeight(120);
            if (a == 1 || a == 3) {
                x = 30;
                y = 30;
            }
        }
        if (p.charAt(0) == 'h') {
            imageView.setFitWidth(180);
            imageView.setFitHeight(180);
        }
        if (p.charAt(0) == 'i') {
            imageView.setFitWidth(120);
            imageView.setFitHeight(120);
        }
        if (p.charAt(0) == 'j') {
            imageView.setFitWidth(240);
            imageView.setFitHeight(120);
            if (a == 1 || a == 3) {
                x=60;
                y=60;
            }
        }
        imageView.setRotate(90*(p.charAt(3)-'0'));
        imageView.setX(90+SQUARE_SIZE*(p.charAt(1)-'0')-x);
        imageView.setY(65+SQUARE_SIZE*(p.charAt(2)-'0')+y);
        root.getChildren().add(imageView);
    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
