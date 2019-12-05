package window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class SetScene {
    private Scene scene;
    private int width,hight;

    public void setScene(String fxml){
        try {
            if(fxml.equals("EnterWindow.fxml")){
                width=650;hight=500;
            }
            else {
                width=Main.WIDTH;
                hight=Main.HEIGHT;
            }
            scene = new Scene(FXMLLoader.load(getClass().getResource(fxml)),width,hight);
            //scene.getStylesheets().add("test.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getStage().setScene(scene);
        Main.getStage().centerOnScreen();
    }

    public Scene getScene(){return scene;}
}
