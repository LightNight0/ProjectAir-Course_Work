package window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import webConnection.Connector;

import java.io.File;
import java.io.IOException;

public class MenuBarFunctions {

    private boolean fullScreenMode = false;

    public void setFullScreen(MenuItem item){
        if (fullScreenMode) {
            item.setText("Полноэкранный режим");
            Main.getStage().setFullScreen(false);
            fullScreenMode = false;
        } else {
            item.setText("Оконный режим");
            Main.getStage().setFullScreen(true);
            fullScreenMode = true;
        }
    }

    public void closeProgramm(){
        Connector.getInstance().disconnect();
        System.exit(0);
    }

    public void loginOut(){
        new SetScene().setScene("EnterWindow.fxml");
    }

    private boolean aboutWindow = false;

    public void about(){

    }
}
