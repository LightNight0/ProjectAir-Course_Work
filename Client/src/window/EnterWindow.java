package window;

import essences.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import webConnection.Connector;

public class EnterWindow {

    private User user;

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label errorLabelBlocked;


    @FXML
    public void initialize() {
        Main.getStage().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                Enter();
            }
        });
    }

    @FXML
    public void Enter() {
        user = new User();
        user.setDataBaseCommand("Select");
        user.setLogin(loginField.getText());
        user.setPassword(passwordField.getText());
        Connector.getInstance().send(user);
        Object o = Connector.getInstance().get();
        if (o instanceof String && ((String) o).equals("empty")) {
            errorLabel.setVisible(true);
            passwordField.setText("");
        } else {
            loginField.setText("");
            passwordField.setText("");
            errorLabel.setVisible(false);
            errorLabelBlocked.setVisible(false);
            user = (User) o;
                switch (user.getRole()) {
                    case "manager":
                        new SetScene().setScene("managerWindows/ManagerWindow.fxml");
                        break;
                    case "admin":
                        new SetScene().setScene("adminWindow/AdminWindow.fxml");
                        break;
                    case "blocked":
                        errorLabelBlocked.setVisible(true);
                        break;
                    default :
                        errorLabel.setVisible(true);
                }
        }
    }
}
