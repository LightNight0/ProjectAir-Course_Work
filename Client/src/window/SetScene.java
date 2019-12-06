package window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import localization.*;
import java.io.IOException;
import java.util.ResourceBundle;

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
            ResourceBundle resources = ResourceBundle.getBundle("bundle", LangController.getLocale());
            scene = new Scene(FXMLLoader.load(getClass().getResource(fxml), resources),width,hight);
            scene.getStylesheets().add(ThemeController.getTheme());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getStage().setScene(scene);
        Main.getStage().centerOnScreen();
    }

    public Scene getScene(){return scene;}
}
