package window;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class Message {

    private Alert alert;


    public Message(String content, Alert.AlertType alertType){
        alert = new Alert(alertType);
        alert.setContentText(content);
        alert.initStyle(StageStyle.UNDECORATED);
        switch (alertType){
            case ERROR:
                alert.setHeaderText("Ошибка!");
                alert.getDialogPane().setStyle("-fx-background-color: #ff7777; -fx-border-width: 2; -fx-border-color: #771111" );
                break;
            case WARNING:
                alert.setHeaderText("Внимание!");
                alert.getDialogPane().setStyle("-fx-background-color: #ffffdd; -fx-border-width: 2; -fx-border-color: #555555");
                break;
            case INFORMATION:
                alert.setHeaderText("");
                alert.getDialogPane().setStyle("-fx-background-color: #ffffff; -fx-border-width: 2; -fx-border-color: #999999");
                break;
        }
    }


    public void show(){
        alert.show();
    }
}
