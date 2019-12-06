package window.adminWindow;

import essences.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.FileWriter;
import java.io.Writer;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import webConnection.Connector;
import window.MenuBarFunctions;
import window.Message;


public class AdminWindow {


    @FXML
    private TableView<User> userTableView;

    private ObservableList<User> list = null;
    private ObservableList<String> roles = null;

    private TableView.TableViewSelectionModel<User> selectionModel;

    @FXML
    private TextField tf1;
    @FXML
    private TextField tf2;
    @FXML
    private TextField tf3;

    @FXML
    private Button button;

    @FXML
    private Button saveFile;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private VBox vBox;

    private MenuBarFunctions menuBarFunctions;

    private static TableColumn name;
    private static TableColumn role;
    private static TableColumn login;
    private static TableColumn password;

    public AdminWindow() {
    }

    public ObservableList<User> getUsers() throws Exception {
        ObservableList<User> list = FXCollections.observableArrayList();
        User user = new User();
        user.setDataBaseCommand("Select");
        user.setRole("admin");
        Connector.getInstance().send(user);
        Object o = Connector.getInstance().get();
        if (o instanceof String) {
            switch ((String) o) {
                case "empty":
                    throw new Exception("empty");
                case "Wrong request":
                    throw new Exception("wrong request");
            }
        } else {
            User[] users = (User[]) o;
            list.addAll(users);
        }
        return list;
    }

    @FXML
    public void initialize() {
        roles = FXCollections.observableArrayList();
        roles.addAll("manager", "admin", "blocked");
        choiceBox.setItems(roles);

        name = new TableColumn("Имя");
        role = new TableColumn("Должность");
        login = new TableColumn("Логин");
        password = new TableColumn("Пароль");

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));

        name.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        role.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        login.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        password.setCellFactory(TextFieldTableCell.<User>forTableColumn());

        try {
            list = getUsers();
        } catch (Exception e) {
            new Message(e.getMessage(), Alert.AlertType.INFORMATION).show();
        }
        menuBarFunctions = new MenuBarFunctions();
        userTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userTableView.setItems(list);
        userTableView.getColumns().addAll(name, role, login, password);

        selectionModel = userTableView.getSelectionModel();
    }

    @FXML
    private BorderPane borderPane;

    @FXML
    public void registrate() {
        if(button.getText().equals("Изменить"))sendRequest("Update");
        else sendRequest("Add");
    }

    private void clearFields() {
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        choiceBox.getSelectionModel().select(0);
    }

    public void sendRequest(String requestType){
        if (tf1.getText().isEmpty() || tf2.getText().isEmpty() || tf3.getText().isEmpty()) {
            new Message("Заполните все окна!", Alert.AlertType.ERROR).show();
            return;
        }
        User user = new User();
        if(requestType.equals("Add"))user.setDataBaseCommand("Add");
        else {
            user.setDataBaseCommand("Update");
            user.setId(userTableView.getSelectionModel().getSelectedItem().getId());
        }
        user.setName(tf1.getText());
        user.setLogin(tf2.getText());
        user.setPassword(tf3.getText());
        user.setRole(choiceBox.getValue());

        User user1 = new User();
        user1.setLogin(user.getLogin());
        user1.setDataBaseCommand("Select");
        if(requestType.equals("Add"))user1.setRole("admin");
        Connector.getInstance().send(user1);
        if(Connector.getInstance().get() instanceof User[]){
            new Message("Пользователь с таким логином уже существует!", Alert.AlertType.ERROR).show();
            return;
        }

        Connector.getInstance().send(user);
        Object o = Connector.getInstance().get();
        if (!((String) o).equals("ok"))
            new Message("Ошибка при занесении данных в базу данных!", Alert.AlertType.ERROR).show();
        else {
            clearFields();
            back();
            try {
                list = getUsers();
            } catch (Exception e) {
                new Message(e.getMessage(), Alert.AlertType.INFORMATION).show();
            }
            userTableView.setItems(list);
        }
        button.setText("Зарегистрировать");
    }

    @FXML
    public void back() {
        clearFields();
    }

    @FXML
    public void add() {
        choiceBox.getSelectionModel().select(0);
        //vBox.setVisible(true);
        //borderPane.getBottom().setVisible(false);

    }

    @FXML
    public void edit() {
        User user= userTableView.getSelectionModel().getSelectedItem();
        if(user == null){
            new Message("Вы не выбрали учётную запись!", Alert.AlertType.ERROR).show();
            return;
        }
        //vBox.setVisible(true);
        //borderPane.getBottom().setVisible(false);
        tf1.setText(user.getName());
        tf2.setText(user.getLogin());
        tf3.setText(user.getPassword());
        choiceBox.getSelectionModel().select(user.getRole());
        button.setText("Изменить");
    }

    @FXML
    public void changeLanguage(){
        menuBarFunctions.changeLanguage();
    }

    @FXML
    public void changeTheme(){
        menuBarFunctions.changeTheme();
    }

    @FXML
    public void delete() {
        User user= userTableView.getSelectionModel().getSelectedItem();
        if(user == null){
            new Message("Вы не выбрали удаляемую учётную запись!", Alert.AlertType.ERROR).show();
            return;
        }
        user.setDataBaseCommand("Delete");
        Connector.getInstance().send(user);
        Object o = Connector.getInstance().get();
        if (!((String) o).equals("ok"))
            new Message("Ошибка при занесении данных в базу данных!", Alert.AlertType.ERROR).show();
        try {
            list = getUsers();
        } catch (Exception e) {
            new Message(e.getMessage(), Alert.AlertType.INFORMATION).show();
        }
        userTableView.setItems(list);
        userTableView.refresh();
    }

    @FXML
    private void saveInFile(){
        try{
            FileWriter writer = new FileWriter("blockedAccounts.txt");
            for (User u : list){
                if(u.getRole().equals("blocked")){
                    writer.write(u.getName() + " " + u.getLogin() + " " + u.getPassword());
                    writer.flush();
                }
            }
            writer.close();
            new Message("Ok!", Alert.AlertType.INFORMATION).show();
        }catch (Exception ex){
            new Message("Ошибка при записи в файл!", Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    private MenuItem fullScreenMenuItem;

    @FXML
    public void fullscreen() {
        menuBarFunctions.setFullScreen(fullScreenMenuItem);
    }

    @FXML
    public void loginOut() {
        menuBarFunctions.loginOut();
    }

    @FXML
    public void closeProgramm() {
        menuBarFunctions.closeProgramm();
    }

    @FXML
    public void about() {
        menuBarFunctions.about();
    }
}
