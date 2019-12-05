package window;

import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static final boolean DEBUG = true;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private static Stage stage;
    private BorderPane root;

    @Override
    public void init() throws Exception {
        if(DEBUG)
            System.out.println("Application inits");
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        SetScene s = new SetScene();
        s.setScene("EnterWindow.fxml");
        primaryStage.setScene(s.getScene());
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if(DEBUG)
            System.out.println("Application stops");
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage(){return stage;}
}

